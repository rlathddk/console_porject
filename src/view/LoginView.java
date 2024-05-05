package view;

import controller.Control_member;
import model.Dto.MemberDto;

import java.util.Scanner;

public class LoginView {
    // 싱글톤
    private LoginView(){}
    private static LoginView LoginView = new LoginView();
    public static LoginView getInstance(){return LoginView;}
    Scanner scanner = MainView.getInstance().scanner;

    // 로그인 메소드
    public void login(){
        // 입력
        System.out.print("아이디 : "); String mid = scanner.next();
        System.out.print("비밀번호 : "); String mpw = scanner.next();

        // 객체화
        MemberDto memberDto = new MemberDto();
        memberDto.setMid(mid);
        memberDto.setMpw(mpw);

        // 저장 후 control에 전달
        boolean result = Control_member.getInstance().login(memberDto);

        // 결과 출력
        if(result){
            System.out.println("<안내>로그인 성공");
            MainPageView.getInstance().run();
        } else {
            System.out.println("<안내>로그인 실패");
        }
    } // login e
} // c e
