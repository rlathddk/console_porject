package view;

import java.util.Scanner;

//호스트 뷰
public class HostPageView {
    //싱글톤
    private HostPageView(){}
    private static HostPageView hostPageView=new HostPageView();
    public static HostPageView getInstance(){return hostPageView;}

    //실행 메소드
    public void run(){

        // 스캐너 선언
        Scanner scanner = new Scanner(System.in);



        while (true){
            System.out.println("1.숙소관리 | 2.리뷰관리 | 3.돌아가기");

            int ch = scanner.nextInt(); // 1.숙소관리 2. 리뷰관리 3. 돌아가기

            if(ch == 1){
                HostSubPageView.getInstance().run();
            }
            else if(ch == 2){
                Host_Review_Command.getInstance().run();
            }
            else if(ch == 3){
                break;
            }
            else{
                System.out.println("잘못 입력하셨습니다");
            }
        }

    }
}
