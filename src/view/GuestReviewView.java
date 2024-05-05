package view;

import controller.Control_Guest;
import model.Dto.Guest_ReviewDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class GuestReviewView {
    //싱글톤
    private GuestReviewView(){}
    private static GuestReviewView guestReviewView=new GuestReviewView();
    public static GuestReviewView getInstance(){return guestReviewView;}

    //Scanner 객체
    Scanner scanner=new Scanner(System.in);

    //main view
    public void run(){
        System.out.println("============ 리뷰페이지 =============");
        myReview(); //내게 써진 리뷰 출력

        while(true){
            System.out.println("1.리뷰등록 | 2.리뷰수정 | 3.리뷰삭제 | 4.돌아가기");
            System.out.print("선택 : ");
            //실행
            try {
            //입력
            int ch = scanner.nextInt();
                if (ch == 1) {  //리뷰등록
                    inputReview();
                } else if (ch == 2) {   //리뷰수정
                    updateReview();
                } else if (ch == 3) {   //리뷰삭제
                    deleteReview();
                } else if (ch == 4) {   //돌아가기
                    System.out.println("========= 리뷰페이지를 나갑니다. =========");
                    return;
                } else {
                    System.out.println("올바르지 않은 입력입니다.");
                }//if1 end
            }//t end
            catch (Exception e) {
                System.out.println("올바르지 않은 입력입니다. : " + e);
            }
        }//w end
    }//m end

    //내게 쓴 리뷰 출력
    public void myReview(){
        ArrayList<HashMap<String, String>> myReview=Control_Guest.getInstance().myReview();


        if(myReview.size()==0){//내게 써진 리뷰가 없을경우
            System.out.println("받은 리뷰가 없습니다.");
            System.out.println("-------------------------------------------------------------------------");
            return;
        }
        //평균평점 출력
        float scoreAvg=Control_Guest.getInstance().scoreAvg();    //평균평점 저장변수
        System.out.println("-------------------");
        System.out.print("내 평균 평점 : ");
        System.out.printf("%.1f \n",scoreAvg);

        //리뷰 리스트 출력
        System.out.println("-------------------------------- 받은 리뷰 --------------------------------");
        System.out.println("작성자\t\t\t  내용\t\t\t\t\t   평점");
        for(int i=0; i<myReview.size(); i++){
            System.out.printf("%-15s %-20s %-5s\n",
                    myReview.get(i).get("houseName")+"***",
                    myReview.get(i).get("content"),
                    myReview.get(i).get("score"));
        }//for end
        System.out.println("-------------------------------------------------------------------------");
    }//m end

    //리뷰등록 메소드
    public void inputReview(){
    //리뷰가능 예약내역 출력
        //control 호출
        ArrayList<HashMap<String, String>> result = Control_Guest.getInstance().finishReservationList();

        try {
            //출력
            System.out.println("예약번호\t\t예약날짜\t\t숙소이름");
            if (result.size() == 0) {//데이터 없는경우 유효성 검사
                System.out.println("리뷰등록 가능한 예약이 없습니다.");
                return;
            } else {
                for (int i = 0; i < result.size(); i++) {
                    System.out.printf("%-10s %-10s %-10s\n",
                            result.get(i).get("reservation_pk"),
                            result.get(i).get("reservation_date"),
                            result.get(i).get("houseName"));
                }//for end
            }//if end

            //리뷰등록할 예약번호 선택
            System.out.println("리뷰를 등록하실 예약번호를 선택해 주십시오.");
            System.out.print("선택 : ");
            int reservatioPk=scanner.nextInt();
            //리뷰 가능한 예약번호인지 유효성검사
            if(!Control_Guest.getInstance().checkFinishReservationList(reservatioPk)){
                System.out.println("유효하지 않은 번호입니다.");
                return;
            }

            //선택한 예약번호에 리뷰내용 입력
            System.out.print("리뷰내용 작성 : ");
            scanner.nextLine();
            String rContent=scanner.nextLine();
            //선택한 예약번호에 별점 입력
            System.out.print("별점 작성(소수점은 제외됩니다.) : ");
            int score=scanner.nextByte();

            //입력한 내용 객체에 저장
            Guest_ReviewDto guestReviewDto=new Guest_ReviewDto();//리뷰 입력내용 저장 객체
            guestReviewDto.setContent(rContent);
            guestReviewDto.setScore(score);

            //DB 저장을 위한 controller에 객체 대입
            boolean inputResult= Control_Guest.getInstance().inputReview(reservatioPk, guestReviewDto);

            //결과출력
            if(inputResult){
                System.out.println("등록성공");
            }
            else{
                System.out.println("[등록실패] 해당하는 숙소가 존재하지 않습니다.");
            }//if end
        }//t end
        catch (Exception e) {
            System.out.println("[오류] : " + e);
        }
    }//m end

    //내가 등록한 리뷰 수정 메소드
    public void updateReview(){
        //내가 쓴 리뷰 출력
        printWriteReview();

        //수정할 리뷰번호 입력
        System.out.print("수정할 리뷰번호를 입력 : ");
        int reviewNo=scanner.nextInt();
        scanner.nextLine();

        //수정할 내용 입력
        System.out.print("새로운 내용 : ");
            String content=scanner.nextLine();
        System.out.print("새로운 평점(소수점은 적용되지 않습니다.) :");
            int score=scanner.nextInt();

        //입력받은 정보 저장 객체 생성
        Guest_ReviewDto guestReviewDto=new Guest_ReviewDto();
        //객체에 입력한 정보 저장
        guestReviewDto.setReview_pk(reviewNo);
        guestReviewDto.setContent(content);
        guestReviewDto.setScore(score);
        //결과 저장 변수
        boolean result=Control_Guest.getInstance().updateReview(guestReviewDto);
        if(result){
            System.out.println("리뷰수정이 완료되었습니다.");
            printWriteReview();
        }
        else{
            System.out.println("[오류] 리뷰등록이 불가능한 리뷰번호입니다.");
        }//if end
    }//m end

    //리뷰 삭제 메소드
    public void deleteReview(){
        //내가 쓴 리뷰 출력
        printWriteReview();

        //삭제할 리뷰번호 입력
        System.out.print("삭제할 리뷰번호를 입력 : ");
        int reviewNo=scanner.nextInt();

        //입력받은 정보 저장 객체 생성
        Guest_ReviewDto guestReviewDto=new Guest_ReviewDto();

        //객체에 입력한 정보 저장
        guestReviewDto.setReview_pk(reviewNo);

        //리뷰삭제 메소드 실행
        boolean result= Control_Guest.getInstance().deleteReview(guestReviewDto);

        //출력
        if(result){
            System.out.println("리뷰삭제가 완료되었습니다.");
            printWriteReview();
        }
        else{
            System.out.println("[오류] 리뷰삭제가 불가능한 리뷰번호입니다.");
        }//if end
    }//m end

    public void printWriteReview(){
        //내가 쓴 리뷰 출력
        ArrayList<HashMap<String, String>> printWriteReview=Control_Guest.getInstance().printWriteReview();

        //출력
        System.out.println("-------------------------------- 내가 쓴 리뷰 --------------------------------");
        //만약 배열이 없다면 return
        if(printWriteReview.size()==0){
            System.out.println("내가 쓴 리뷰가 없습니다.");
            System.out.println("----------------------------------------------------------------------------");
            return;
        }

        System.out.println("리뷰번호\t 숙소이름\t   내용\t\t\t\t\t평점");
        for(int i=0; i<printWriteReview.size(); i++){
            System.out.printf("%-7s  %-10s %-20s %-5s\n",
                    printWriteReview.get(i).get("review_pk"),
                    printWriteReview.get(i).get("houseName"),
                    printWriteReview.get(i).get("content"),
                    printWriteReview.get(i).get("score"));
        }//for end
        System.out.println("----------------------------------------------------------------------------");
    }


}//c end
