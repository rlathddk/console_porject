package view;

import java.util.Scanner;

public class MainView {
    Scanner scanner = new Scanner(System.in);
    // 싱글톤
    private MainView(){}
    private static MainView MainView = new MainView();
    public static MainView getInstance(){return MainView;}

    public void run(){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 기능 선택하기
            System.out.println("===============================================");
            System.out.println("1.회원가입 | 2.로그인 | 3.아이디찾기 | 4.비밀번호찾기 ");
            System.out.println("===============================================");
            System.out.print("기능을 선택해 주세요(1~4) => ");

            try {
            int ch = scanner.nextInt();

            if(ch==1){
                JoinView.getInstance().join();
            }
            else if (ch==2) {
                LoginView.getInstance().login();
            }
            else if (ch==3) {
                SearchId.getInstance().SearchId();
            }
            else if (ch==4) {
                SearchPw.getInstance().SearchPw();
            }
            else {
                System.out.println("올바른 숫자를 입력해주세요.");
            }
            } catch(Exception e) {
                System.out.println("올바르지 않은 입력입니다.");
                System.out.println(e);
                scanner.nextLine();
            }
        }
    }
}// c e
