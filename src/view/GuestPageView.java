package view;

import controller.Control_Guest;
import controller.Control_member;
import model.Dto.HouseDto;
import model.Dto.ReservationDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



//게스트 뷰
public class GuestPageView {
    //싱글톤
    private GuestPageView(){}
    private static GuestPageView guestPageView=new GuestPageView();
    public static GuestPageView getInstance(){return guestPageView;}

    //scanner 객체 생성
    Scanner scanner=new Scanner(System.in);

    //실행 메소드
    public void run(){
        while (true) {
            System.out.println("================= guest 페이지 =================");
            System.out.println("1.숙소검색 | 2.예약관리 | 3.리뷰관리 | 4.돌아가기");
            System.out.print("선택 : ");
            int ch = scanner.nextInt();

            if (ch == 1) {//숙소검색
                searchHouse();

            } else if (ch == 2) {
                reservationManagement();
            } else if (ch == 3) {
                GuestReviewView.getInstance().run();
            } else if(ch==4){
                System.out.println("================= guest 페이지를 나갑니다. =================");
                return;
            }
        }//while end
    }//m end

    //예약관리 메소드
    public void reservationManagement() {
        while(true) {
            ArrayList<HashMap<String, String>> result = Control_Guest.getInstance().reservationList();

            System.out.println("--------------------------------- 예약내역 ---------------------------------");

            //배열에 아무것도 저장되지 않을경우 안내문구 출력
            if(result==null){
                System.out.println("예약내역이 없습니다.");
                System.out.println("----------------------------------------------------------------------");
                return;
            }//if end

            //예약내역 출력
            System.out.println("예약번호\t\t\t예약날짜\t\t\t숙소이름\t\t\t예약인원\t\t\t예약상태");
            for (int i = 0; i < result.size(); i++) {

                //예약상태 데이터 String으로 표기
                String reservationStatus=null;//상태 저장 문자열 변수
                if(result.get(i).get("reservation_status").equals("0")){
                    reservationStatus="승인대기";
                }
                else if(result.get(i).get("reservation_status").equals("1")){
                    reservationStatus="승인완료";
                }
                else if(result.get(i).get("reservation_status").equals("2")){
                    reservationStatus="취소";
                }
                else if(result.get(i).get("reservation_status").equals("3")){
                    reservationStatus="리뷰작성 완료";
                }//if end

                System.out.printf("%-15s %-15s %-12s %-15s %-10s\n",
                        result.get(i).get("reservation_pk"),
                        result.get(i).get("reservation_date"),
                        result.get(i).get("houseName"),
                        result.get(i).get("reservation_people"),
                        reservationStatus);

            }//for end
            System.out.println("---------------------------------------------------------------------------");

            try {
                //예약 관리
                System.out.println("1.예약취소 | 2.돌아가기");
                System.out.print("선택 : ");
                //콘솔 입력
                int ch = scanner.nextInt();

                //예약결과 저장변수
                int deleteResult = 0;
                if (ch == 1) {//예약 취소
                    System.out.print("취소하실 예약번호를 입력 해 주십시오. : ");
                    int reservation_pk = scanner.nextInt();

                    //-----------존재하는 예약번호인지 유효성 검사--------------
                    boolean rspkCheck=Control_Guest.getInstance().checkReservationPk(reservation_pk);
                    if(!rspkCheck){

                        System.out.println("존재하는 예약번호가 없습니다. 예약관리 화면으로 돌아갑니다..");
                        continue;
                    }//if end--------------------------------------------

                    System.out.println("정말 취소하시겠습니까? 1.예 | 2.아니오");
                    System.out.print("선택 : ");

                    int check = scanner.nextInt();

                    //취소 진행(예약상태=2(취소)로 변경)
                    if (check == 1) {
                        //db내역 변경 함수 호출
                        deleteResult = Control_Guest.getInstance().deleteReservation(reservation_pk);

                        //출력
                        if (deleteResult == 1) {//취소완
                            System.out.println("예약취소가 완료되었습니다.");
                        }
                        else if (deleteResult == 2) {//취소안됨1
                            System.out.println("[예약취소 실패]입실 기간이 지났거나 승인완료된 내역은 취소가 불가합니다.");
                        }
                        else if(deleteResult==3){//취소안됨2
                            System.out.println("이미 취소된 내역입니다.");
                        }//if3 end
                    }
                    else if (check == 2) {//아니오 선택
                        System.out.println("예약관리 페이지로 돌아갑니다.");
                    }
                    else {
                        System.out.println("올바르지 않은 입력입니다.");
                    }//if2 end
                }
                else if (ch == 2) {//돌아가기
                    System.out.println("선택화면으로 돌아갑니다.");
                    return;
                }
                else {
                    System.out.println("올바르지 않은 입력입니다.");
                }//if1 end
            }//t end
            catch (Exception e) {
                System.out.println("올바르지 않은 입력입니다. : " + e);
                scanner.nextLine();
            }
        }//while end
    }//m end


    // 승택 ================================================================
    public void searchHouse(){ // 숙소를 검색하는 매소드
        System.out.println("1.지역만 선택하기 | 2.상세 조건 선택하기(지역, 날짜, 인원, 가격) : ");
        int ch = scanner.nextInt();

        if(ch == 1){
            System.out.println("지역 선택 : ");     String region = scanner.next();
            ArrayList<HashMap<String, String>> searchHouse = Control_Guest.getInstance().searchHouse(region);
            if(searchHouse.size()!=0) {
                outputHouse(region, searchHouse);
            }
        }
        else if(ch == 2){
            System.out.println("입실 날짜 : ");
            System.out.println("몇박 하시겠습니까?");
        }
        else{
            System.out.println("잘못 입력하셨습니다.");
        }
    }

    public void outputHouse(String rigion, ArrayList<HashMap<String, String>> searchHouse){ // 등록된 숙소를 출력해주는 매소드
        System.out.println("=============================== 숙소내역 =================================");
        System.out.println("no\t\t숙소이름\t\t지역\t\t\t최대인원\t\t\t날짜\t\t\t\t1박당가격");
        for (int i = 0; i < searchHouse.size(); i++) {
            System.out.printf("%-7s %-10s %-10s %-10s %10s %15s\n",
                    (i+1),
                    searchHouse.get(i).get("houseName"),
                    searchHouse.get(i).get("region"),
                    searchHouse.get(i).get("maxPeople"),
                    searchHouse.get(i).get("reservation_date"),
                    searchHouse.get(i).get("day_price"));
        }//for end
        System.out.println("=========================================================================");
        System.out.println("어떤 숙소를 예약하시겠습니까?(no) : ");  int houseNo = scanner.nextInt();
        int house_pk = Integer.parseInt(searchHouse.get(houseNo-1).get("house_pk"));
        reserveHouse(house_pk);
    }

    public void reserveHouse(int house_pk){
        boolean result = false;

        try {
            System.out.println("입실 날짜(0000-00-00) : ");        String date = scanner.next();
            System.out.println("몇박 하시겠습니까? : ");            int day = scanner.nextInt();
            System.out.println("인원 : ");                        int people = scanner.nextInt();
            System.out.println("정말 예약하시겠습니까? (1:예 2:아니오) : "); int real = scanner.nextInt();

            ReservationDto reservationDto = new ReservationDto(0, 0, people, 0);

            result = Control_Guest.getInstance().insertReservation(reservationDto, house_pk, date, day);

            if (result) {
                System.out.println("예약이 완료되었습니다.");
            } else {
                System.out.println("예약에 실패하셨습니다.");
            }
        }catch (Exception e){
            System.out.println(e);
            scanner.nextLine();
        }
    }
    // 승택 end ============================================================
}//c end
