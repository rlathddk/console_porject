package view;

import controller.Control_member;
import model.Dto.MemberDto;

import java.util.Scanner;

public class JoinView {
    Scanner scanner = MainView.getInstance().scanner;

    // 싱글톤
    private JoinView(){}
    private static JoinView JoinView = new JoinView();
    public static JoinView getInstance(){return JoinView;}

    // 회원가입 메소드
    public void join(){
        // 입력
        System.out.print("아이디 : "); String mid = scanner.next();
        System.out.print("비밀번호 : "); String mpw = scanner.next();
        System.out.print("이메일 : "); String memail = scanner.next();
        System.out.print("핸드폰번호 : "); String mphone = scanner.next();
        System.out.print("이름 : "); String mname = scanner.next();

        // 객체화
        MemberDto memberDto = new MemberDto();
        memberDto.setMid(mid);
        memberDto.setMpw(mpw);
        memberDto.setMemail(memail);
        memberDto.setMphone(mphone);
        memberDto.setMname(mname);

        // 저장 후 control에 전달
        int result = Control_member.getInstance().join(memberDto);
        // System.out.println(" = " +memberDto );


        // 결과 출력
        if(result == 0) {
            System.out.println("<안내> 회원가입 성공");
        }
        else if (result == 1) {
            System.out.println("<안내> 중복된 아이디입니다.");
        }
        else if (result == 2) {
            System.out.println("<안내> 오류");
        }
    } // join e
} // c e
