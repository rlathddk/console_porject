package view;

import java.util.Scanner;

//마이페이지 뷰
public class MyPageView {
    //싱글톤
    private MyPageView(){}
    private static MyPageView myPageView=new MyPageView();
    public static MyPageView getInstance(){return myPageView;}

    //테스트 실행 메소드
    public static void main(String[] args) {ChangePasswordView.getInstance().changePasswordView();}

        public Boolean run(){
            //scanner 생성
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1.비밀번호 변경 | 2.회원탈퇴 | 3.돌아가기");
                System.out.print("선택 : ");

                //입력
                try {
                    int ch = scanner.nextInt();

                    if (ch == 1) {//비밀번호 페이지
                        ChangePasswordView.getInstance().changePasswordView();
                    } else if (ch == 2) {//회원탈퇴
                        if(DeleteMemberView.getInstance().deleteMemberView()){return true;}
                        else {return false;}
                    } else if (ch == 3) {//마이페이지
                        break;
                    } else {
                        System.out.println("올바르지 않은 입력입니다.");
                    }

                } catch (Exception e) {
                    System.out.println("올바르지 않은 입력입니다.");
                    System.out.println(e);
                }
            }//w end
            return false;
        }
}
