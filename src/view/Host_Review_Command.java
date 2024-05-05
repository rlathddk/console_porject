package view;


import controller.Control_Host;
import controller.Control_Review;
import controller.Control_member;
import model.Dao.Host_Dao;
import model.Dto.Guest_ReviewDto;
import model.Dto.Host_ReviewDto;
import model.Dto.HouseDto;
import model.Dto.ReviewWrite_View_Dto;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Host_Review_Command {//class start
    // 싱글톤
    private Host_Review_Command(){}
    private static Host_Review_Command review_command = new Host_Review_Command();
    public static Host_Review_Command getInstance(){return review_command;}


    public void run(){
        Scanner scanner = new Scanner(System.in);
        String id = Control_member.getInstance().getLogin_id();//"noname1";//
        System.out.println("======================= 내가 등록한 숙소 =======================");
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

                                break;
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

        // 내가 등록한 house 에 대한 리뷰 들고오기
        ArrayList<Guest_ReviewDto> my_house_Review = Control_Review.getInstance().my_house_Review(ch);//리뷰출력할 객체받아오기
        double score = 0; // 평균점수용 변수
        for (int i = 0; i < my_house_Review.size(); i++) {
            score += my_house_Review.get(i).getScore();
        }
            // 평균구하기
        score = Math.round(score/my_house_Review.size()); //Math.round = 소숫점 첫째자리까지 반올림 시킴
            // 하우스식별번호로 이름 받아오기
        String house_name = Control_Review.getInstance().house_name(ch);


        System.out.println("\n\n=========================== 우리집에 등록된 리뷰"+"=============================");
        System.out.println("평균 평점 ="+score);
        System.out.printf(" %2s\t %-15s    %-29s \t %2s\n","번호","작성자","내용","평점");
        for (int i = 0; i < my_house_Review.size(); i++) {
            // 작성자 번호로 작성자 이름 가져오기
            String writer_name = Control_Review.getInstance().member_name(my_house_Review.get(i).getWriter()).charAt(0)+"***";
            String content = my_house_Review.get(i).getContent(); // 내용
            int point = my_house_Review.get(i).getScore(); // 점수
            System.out.printf(" %2d\t\t %-15s %-30s \t %2d\n",i+1,writer_name,content,point);
        }

        System.out.println("===========================================================================");
        // 매개변수 = 하우스 식별번호(ch)


        while (true) { // while start
            System.out.println("\n\n============== 리뷰기능을 선택해주세요 ==============");
            System.out.println("1.리뷰등록 | 2.리뷰수정 | 3.리뷰삭제 | 4.돌아가기");
            System.out.print("선택(1~4) > ");
            try {
                int choice = scanner.nextInt();

                if (choice == 1) {//리뷰등록
                    // 이용한 멤버 가져오기
                    ArrayList<ReviewWrite_View_Dto> review_write_view = Control_Review.getInstance().review_write_view(ch);
                    while (true) { // 한글 입력시 예외처리 하기위한 while
                        try {
                            int 선택한번호 = 0;
                            System.out.println("\n\n=========================== 이용해준 사람들 =============================");
                            System.out.printf(" %2s\t %-10s   %-5s %10s\n", "번호", "집이름", "회원명", "사용날짜");
                            for (int i = 0; i < review_write_view.size(); i++) {
                                String 집이름 = Control_Review.getInstance().house_name(review_write_view.get(i).getHouse_pk());
                                String 회원명 = Control_Review.getInstance().member_name(review_write_view.get(i).getMember_pk());
                                String 날짜 = review_write_view.get(i).getReservation_date();
                                System.out.printf(" %2d\t\t %-10s  %-5s \t%10s\n", i + 1, 집이름, 회원명, 날짜);
                            }// for end
                            System.out.println("\n\n=======================================================================");

                            // System.out.println(review_write_view);
                            if (!review_write_view.isEmpty()) {
                                System.out.print("안내] 번호를 선택해주세요 > ");
                                선택한번호 = scanner.nextInt();
                                scanner.nextLine();
                                if (선택한번호 < review_write_view.size() + 1) {
                                    System.out.print("안내] 내용을 입력해주세요 : ");
                                    String content = scanner.nextLine();
                                    int 점수 =0;
                                    while (true) {// 점수 유효성검사 1~5 점만 가능
                                        System.out.print("안내] 평점을 입력해주세요 (1~5) : ");점수 = scanner.nextInt();
                                        if (점수<6&&점수>0){break;}
                                        else {
                                            System.out.println("안내] 0~5 사이로 입력해주세요");
                                        }
                                    }
                                    scanner.nextLine();
                                    Host_ReviewDto host_reviewDto = new Host_ReviewDto(0, review_write_view.get(선택한번호 - 1).getMember_pk(), ch, content, 점수);
                                    if (Control_Review.getInstance().review_write(host_reviewDto)) {
                                        System.out.println("안내]리뷰등록에 성공했습니다.");
                                        // 예약상태 수정 하기   선택한 예약식별번호 -> 반환없음.
                                        Control_Review.getInstance().review_status(review_write_view.get(선택한번호 - 1).getReservation_pk());
                                        break;
                                    } else {
                                        System.out.println("안내]리뷰등록에 실패했습니다.");
                                        break;
                                    }
                                } else {
                                    System.out.println("안내] 해당 순번은 없습니다.");
                                    break;
                                }
                            }else {
                                System.out.println("안내] 작성할수 있는 리뷰가 없습니다. ");
                                break;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("안내] 숫자입력해주세요");
                            scanner.nextLine();
                        }
                    }// 한글 입력시 예외처리 하기위한 while end


                    //////////////////////////리뷰등록 끝 - 전승호

                } else if (choice == 2) {//리뷰수정
                    // 전승호 리뷰수정 start ===============================================================
                        // 1. 출력 id(텍스트) 주고 내가 작성한 리뷰배열 찾아오기(호스트버전)
                        // ch = 선택한 하우스 식별번호
                    ArrayList<Host_ReviewDto> my_review = Control_Review.getInstance().Host_my_review(ch);
                    if(!my_review.isEmpty()) {
                        // my_review 가 값이 있다면 실행 => 없으면 안내] 등록하신 리뷰가 없습니다. 출력
                        System.out.println("\n\n======================== 내가 작성했던 리뷰 리스트 ==========================");
                        System.out.printf(" %2s\t %-10s  %-5s %30s\n", "번호", "대상", "점수", "내용");
                        for (int i = 0; i < my_review.size(); i++) {
                            String 대상 = Control_Review.getInstance().member_name(my_review.get(i).getTarget());
                            int 점수 = my_review.get(i).getScore();
                            String 내용 = my_review.get(i).getContent();
                            System.out.printf(" %2d\t\t %-10s  %-5s \t%30s\n", i + 1, 대상, 점수, 내용);
                        }//for end

                        try {
                            while (true) {
                                System.out.print("수정하실 번호를 입력해 주세요 : ");int 수정선택 = scanner.nextInt();
                                if (수정선택 < my_review.size() + 1) {
                                    scanner.nextLine();
                                    System.out.print("수정하실 내용을 입력해주세요 : ");String 내용a = scanner.nextLine();
                                    int 점수a=0;
                                    while (true) { // 번호입력 유효성검사 0~5점만 가능
                                        System.out.print("수정하실 점수를 입력해주세요(1~5) : "); 점수a = scanner.nextInt();
                                        if (점수a<6&&점수a>0){
                                            break;
                                        }else {
                                            System.out.println("안내] 0~5 사이로 입력해주세요");
                                        }
                                    }
                                    // 수정한 내용으로 객체 생성
                                    Host_ReviewDto host_reviewDto = new Host_ReviewDto(
                                            my_review.get(수정선택-1).getReview_pk(),
                                            my_review.get(수정선택-1).getTarget(),
                                            my_review.get(수정선택-1).getWriter(),
                                            내용a,점수a);
                                    if(Control_Review.getInstance().house_Review_update(host_reviewDto)){
                                        System.out.println("안내] 수정 완료되었습니다.");
                                    }else {
                                        System.out.println("안내] 수정 실패했습니다.");
                                    }
                                    break;

                                } else {
                                    System.out.println("안내] 선택하신 번호에 해당하는 리뷰가 없습니다. ");
                                    System.out.println("\n   다시 선택해주세요. ");
                                    break;
                                }
                            }//while end
                        }catch (InputMismatchException e){
                            System.out.println("안내 ] 숫자를 입력해주세요");
                            scanner.nextLine();
                        }

                    }else {
                        System.out.println("안내] 등록하신 리뷰가 없습니다.");
                    }
                    // 전승호 =============================================================================
                } else if (choice == 3) {//리뷰삭제
                    // 전승호 리뷰삭제 start ===============================================================
                    ArrayList<Host_ReviewDto> my_review = Control_Review.getInstance().Host_my_review(ch);
                    if(!my_review.isEmpty()) {
                        // my_review 가 값이 있다면 실행 => 없으면 안내] 등록하신 리뷰가 없습니다. 출력
                        System.out.println("\n\n======================== 내가 작성했던 리뷰 리스트 ==========================");
                        System.out.printf(" %2s\t %-10s  %-5s %30s\n", "번호", "대상", "점수", "내용");
                        for (int i = 0; i < my_review.size(); i++) {
                            String 대상 = Control_Review.getInstance().member_name(my_review.get(i).getTarget());
                            int 점수 = my_review.get(i).getScore();
                            String 내용 = my_review.get(i).getContent();
                            System.out.printf(" %2d\t\t %-10s  %-5s \t%30s\n", i + 1, 대상, 점수, 내용);
                        }//for end

                        try {
                            while (true) {
                                System.out.print(" 삭제하실 번호를 입력해 주세요 : ");int 삭제선택 = scanner.nextInt();
                                if (삭제선택 < my_review.size() + 1) {
                                    int 삭제할리뷰번호 = my_review.get(삭제선택-1).getReview_pk();
                                    if(Control_Review.getInstance().house_Review_delete(삭제할리뷰번호)){
                                        System.out.println("안내] 수정 완료되었습니다.");
                                    }else {
                                        System.out.println("안내] 수정 실패했습니다.");
                                    }
                                    break;

                                } else {
                                    System.out.println("안내] 선택하신 번호에 해당하는 리뷰가 없습니다. ");
                                    System.out.println("\n   다시 선택해주세요. ");
                                    scanner.nextLine();
                                    break;
                                }
                            }//while end
                        }catch (InputMismatchException e){
                            System.out.println("안내 ] 숫자를 입력해주세요");
                            scanner.nextLine();
                        }

                    }else {
                        System.out.println("안내] 등록하신 리뷰가 없습니다.");
                    }


                    // 전승호 =============================================================================
                } else if (choice == 4) {//돌아가기
                    return;
                }else {
                    System.out.println("안내] 요청하신 번호는 없는 기능입니다.");
                    scanner.nextLine();
                }//IF END
            }catch (InputMismatchException e) {System.out.println(e+"\n숫자로 입력해주세요");scanner.nextLine();}
        }//while end
    }// run method end

}//class end
