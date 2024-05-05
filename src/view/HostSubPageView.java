package view;

import controller.Control_Host;
import controller.Control_member;
import model.Dto.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HostSubPageView {

    private HostSubPageView(){}
    private static HostSubPageView hostSubPageView=new HostSubPageView();
    public static HostSubPageView getInstance(){return hostSubPageView;}

//    public static void main(String[] args) {
//        HostSubPageView.getInstance().run();
//    }

    public void run(){
        // 스캐너 선언
        Scanner scanner = new Scanner(System.in);

        while (true){
            try {
                houseView();
                System.out.println("1.숙소등록 | 2.숙소수정 | 3.숙소삭제 | 4.예약승인 | 5.돌아가기");
                System.out.print("선택 > ");
                int ch = scanner.nextInt(); // 1.숙소등록 | 2.숙소수정 | 3.숙소삭제 | 4.예약승인 | 5. 돌아가기

                if (ch == 1) { // 숙소등록
                    insertHouse();
                } else if (ch == 2) { //숙소수정
                    updateHouse();
                } else if (ch == 3) { // 숙소삭제
                    deleteHouse();
                } else if (ch == 4) {// 예약승인
                    reservationAccept();
                } else if (ch == 5) {// 돌아가기
                    break;
                } else {
                    System.out.println("잘못 입력하셨습니다.");
                }
            }catch (InputMismatchException e){
                System.out.println("\n안내] 숫자로를 입력해주세요");
            }
        } // while end


    } // run() end
    // 전승호 ===========================================================================

    public ArrayList<HouseDto> houseView(){
        // 내가 등록한 숙소 출력하기
            // 1. 로그인한 id를 받서 -> 식별번호로 변환
            // 2. 식별번호로 등록된 HouseDto 반환받아와서 출력하기
            // 3. 하우스 리스트 반환
        String id = Control_member.getInstance().getLogin_id();
        System.out.println("\n\n======================= 내가 등록한 숙소 =======================");
        System.out.printf(" %2s\t%-12s %-15s %-4s \t\t%2s\n","번호","식별번호","이름","지역","최대인원");
        // 로그인한 id 주고 그에대한 house 정보 가져오기
        ArrayList<HouseDto> my_house_list = Control_Host.getInstance().my_house_list(id);
        for (int i = 0; i < my_house_list.size(); i++) {
            int house_pk = my_house_list.get(i).getHouse_pk();// 하우스 식별번호
            String name = my_house_list.get(i).getHouseName();// 하우스 이름
            String region = my_house_list.get(i).getRegion();//  지역
            int maxPeople = my_house_list.get(i).getMaxPeople(); // 최대인원
            System.out.printf(" %2d\t\t%-10d \t%-15s %-4s  \t\t%2d\n",i+1,house_pk,name,region,maxPeople);
        }
        System.out.println("================================================================");
        return my_house_list;
    }
    public int returnHouseNo(ArrayList<HouseDto> my_house_list){
        // 집 리스트 주고 선택받은 하우스 식별번호 를 반환해주는 메소드

        Scanner scanner = new Scanner(System.in);
        int ch = 0; // 하우스 식별번호

        if (!my_house_list.isEmpty()) { // 1개의 데이터도 없으면 스킵
            while (true) { // 한글 입력시 예외처리 하기위한 while
                try {
                    System.out.print("선택 > ");
                    ch = scanner.nextInt();
                    if (ch < my_house_list.size() + 1) { //있는 번호중에서만 선택할수있게 하기
                        // 선택받은 번호를 하우스 식별번호로 변경하는 코드
                        for (int i = 0; i < my_house_list.size(); i++) {
                            if ((ch - 1) == i) {
                                ch = my_house_list.get(i).getHouse_pk();

                                return ch;
                            }
                        }//for end
                        break;
                    } else {
                        System.out.println("해당 선택번호는 없습니다.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("숫자입력해주세요");
                    scanner.next();
                }
            }// while end
        }// if end
        return ch;
    }// returnHouseNo method end
    public void updateHouse(){
        //전승호 =========================================================================//
        Scanner scanner = new Scanner(System.in);
        // 1. 내가등록한 숙소를 DB에서 꺼내오기
        // 2. 수정할 데이터 선택 및 입력받아서 수정 -> 성공여부 출력
        // houseView(); // 내가 로그인한 아이디로 등록한 숙소 출력 => 반환 = 숙소리스트

        // returnHouseNo(houseView()); // 내가 선택한 번호를 하우스번호로 반환
        int houseNo = returnHouseNo(houseView());


        // 하우스식별번호 주고 정보 받아오기
        ArrayList<HouseFixDto> houseFixDtos = Control_Host.getInstance().HouseFix_View(houseNo);

        int 수정선택번호 = 0;

        if (!houseFixDtos.isEmpty()) {
            System.out.println("======================= 숙소이름 : " + houseFixDtos.get(0).getHouseName() + " =================");
            System.out.printf(" %2s\t%-12s %-8s %-4s \t\t%2s\n", "번호", "날짜", "가격", "지역", "최대인원");
            for (int i = 0; i < houseFixDtos.size(); i++) {
                String 날짜 = houseFixDtos.get(i).getReservation_date();
                int 가격 = houseFixDtos.get(i).getDay_price();
                String 지역 = houseFixDtos.get(i).getRegion();
                int 최대인원 = houseFixDtos.get(i).getMaxPeople();

                System.out.printf(" %2s\t%-12s\t %-10d %-4s \t\t%2s\n", i + 1, 날짜, 가격, 지역, 최대인원);
            }//for end
            System.out.println("================================================================");

            while (true) {
                try {
                    System.out.print("수정 희망하는 번호를 선택해주세요 > ");수정선택번호 = scanner.nextInt();
                    if (수정선택번호<houseFixDtos.size()+1&&수정선택번호>0) {

                        System.out.println("\n수정하실 내용을 선택해주세요");
                        System.out.println("1.날짜 2.가격 3.지역 4.최대인원 5.이름");
                        System.out.print("선택 > ");
                        int 항목선택 = scanner.nextInt();
                        scanner.nextLine();

                        if (항목선택 == 2 || 항목선택 == 4) {//선택항목이 int 타입일경우
                            System.out.print("안내] 수정내용을 작성해주세요(숫자) : ");
                            int 수정내용 = scanner.nextInt();
                            if (Control_Host.getInstance().intHouseFix(houseFixDtos, 항목선택, 수정선택번호 - 1, 수정내용)) {
                                System.out.println("\n안내] 수정이 성공적으로 완료되었습니다.");
                            } else {
                                System.out.println("\n안내] 수정작업을 실패하였습니다..");
                            }
                        } else if (항목선택 == 1 || 항목선택 == 3 || 항목선택 == 5) {// 선택항목이 str 인경우
                            System.out.println("\n\n날짜의 경우 20xx-01-01 형식에 맞춰주세요");
                            System.out.print("안내] 수정내용을 작성해주세요. : ");
                            String 수정내용 = scanner.nextLine();
                            if (Control_Host.getInstance().strHouseFix(houseFixDtos, 항목선택, 수정선택번호 - 1, 수정내용)) {
                                System.out.println("\n안내] 수정이 성공적으로 완료되었습니다.");
                            } else {
                                System.out.println("\n안내] 수정작업을 실패하였습니다..");
                            }

                        } else {
                            System.out.println("\n안내] 없는번호 입니다. \n");
                        }

                        break;
                    }else {
                        System.out.println("\n안내] 없는번호 입니다. \n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("안내] 숫자로 입력해주세요");
                    scanner.nextLine();
                }
            }

        }else {
            System.out.println("안내] 등록된 예약일이 없습니다.");
        }
    }// 전승호 숙소수정 end
    public void deleteHouse(){// 선택받은 숙소 삭제시키기
        Scanner scanner = new Scanner(System.in);
        int 삭제선택번호 = 0;
        while (true) {// 숫자 유효성검사용 반복문 -문자입력시 번호받기 반복
            try {
                ArrayList<HouseDto> houseView = houseView(); // => 내가등록한 숙소 출력 및 객체배열 반환
                if (!houseView.isEmpty()) { // 만약 배열값이 존재한다면 실행
                    System.out.println("안내] 삭제하실 번호를 선택해주세요 \n\t'없는 번호선택시 종료'");
                    System.out.print("선택 > ");삭제선택번호 = scanner.nextInt();
                    if(삭제선택번호>houseView.size()+1||삭제선택번호<0){
                        // 번호선택 유효성검사 없는번호 선택시 종료
                        System.out.println("\n안내]없는번호를 입력하셧습니다.\n");
                        scanner.nextLine();
                        break;
                    }
                    삭제선택번호 = houseView.get(삭제선택번호-1).getHouse_pk();
                    // 삭제선택번호 를 선택받은 순번의 house_pk 로 바꾸기
                    if(Control_Host.getInstance().deleteHouse(삭제선택번호)){
                        System.out.println("\n안내] 등록하신 숙소가 삭제되었습니다.\n");
                        break;
                    }else {
                        System.out.println("\n안내] 삭제실패 하였습니다.\n");
                        break;
                    }

                } else {
                    System.out.println("\n안내] 등록된 숙소가 없습니다.\n");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("안내] 숫자로 입력해주세요! ");
            }
        }//while end
    }// dereteHouse method End
/// 예약승인
    // 예약승인 메인 메소드
    public boolean reservationAccept(){
        Scanner scanner = new Scanner(System.in);
        ArrayList<ReservationVIewDto> 리스트 = reservationAcceptView();
        try {
            if(리스트.isEmpty()) {
                System.out.println("승인대기인 예약이 없습니다.");
                return false;
            }
            System.out.println("\t없는번호 또는 문자입력시 이전작업으로 돌아갑니다.");
            System.out.print("수락하실 번호를 선택해주세요 > ");
            int 선택번호 = scanner.nextInt();
            if(선택번호>리스트.size()+1||선택번호<0){
                // 번호선택 유효성검사 없는번호 선택시 종료
                System.out.println("\n안내]없는번호를 입력하셧습니다.\n");
                scanner.nextLine();
                return false;
            }
            // 예약 DB 상태 1로 수정하기
            if(Control_Host.getInstance().updateStatus(리스트.get(선택번호-1).getReservation_pk())){
                System.out.println("\n안내] 예약수락이 완료되었습니다.\n");
            }else{System.out.println("\n안내] 예약수락 작업을 실패했습니다.\n");}

            return true;

        }catch (InputMismatchException e){
            System.out.println("\n안내] 문자가 입력되었습니다.\n");
            scanner.nextLine();
            return false;
        }
    }// reservationAccept method End

    // 예약 신청받은거 출력메소드
    public ArrayList<ReservationVIewDto> reservationAcceptView(){
        // reservation 에서 나의 아이디에 해당하는 예약리스트를 가져옴
        ArrayList<ReservationVIewDto> my_reservationList =Control_Host.getInstance().reservationAcceptView();
        if(!my_reservationList.isEmpty()) {
            System.out.println("\n\n==================== 예약신청 받은 숙소 ============================");
            System.out.printf(" %2s\t%-10s %-10s %-4s \n", "번호", "집이름", "날짜", "신청자이름");
            for (int i = 0; i < my_reservationList.size(); i++) {
                String 집이름 =my_reservationList.get(i).get집이름();
                String 날짜 = my_reservationList.get(i).get날짜() ;
                String 신청자이름 = my_reservationList.get(i).get신청자이름();

                System.out.printf(" %2d\t%-10s%-10s \t%-5s \n", i+1, 집이름, 날짜, 신청자이름);
            }
            System.out.println("===================================================================");
            return my_reservationList;
        }else {
            System.out.println("\n안내] 신청받은 숙소예약이 없습니다.\n");

        }
        return my_reservationList;
    }// reservationAcceptView method End


    // 전승호End ==========================================================================

    // 오승택==============================================================================
    public void insertHouse(){
        // 스캐너 선언
        Scanner scanner = new Scanner(System.in);

        System.out.println("숙소이름 :");      String name = scanner.next();
        System.out.println("지역 :");         String region = scanner.next();
        System.out.println("시작날짜 ex)0000-00-00 :");         String date = scanner.next();
        System.out.println("며칠 등록하시겠습니까? : "); int day = scanner.nextInt();
        System.out.println("최대인원 :");       int people = scanner.nextInt();
        System.out.println("1박당 가격 :");     int price = scanner.nextInt();

        // house table DB 추가용 객체
        HouseDto houseDto = new HouseDto( 0,name, 0, region, people);

        // reservation_data DB 추가용 객체
        Reservation_dateDto reservation_dateDto = new Reservation_dateDto(0, date, 0, price);

        boolean result = Control_Host.getInstance().insertHouse(houseDto, reservation_dateDto, day);

        if(result){
            System.out.println("숙소 등록이 완료되었습니다.");
        }
    }
    // 오승택 end============================================================================================

}
