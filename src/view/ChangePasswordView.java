package view;

import controller.Control_member;
import model.Dto.MemberDto;

import java.util.Scanner;



public class ChangePasswordView {
    Scanner scanner = MainView.getInstance().scanner;
    // 싱글톤
    private ChangePasswordView(){}
    private static ChangePasswordView ChangePasswordView = new ChangePasswordView();
    public static ChangePasswordView getInstance(){return ChangePasswordView;}

    // 비밀번호변경 메소드
    public void changePasswordView(){
        // 입력
        System.out.print("이메일 : "); String memail = scanner.next();
        System.out.print("변경할 비밀번호 : "); String mpw = scanner.next();

        // 객체화
        MemberDto memberDto = new MemberDto();
        memberDto.setMemail(memail);
        memberDto.setMpw(mpw);

        // 저장 후 control에 전달
        boolean result = Control_member.getInstance().changePasswordView(memberDto);

        // 결과 출력
        if (result){
            System.out.println("<안내> 비밀번호변경 완료");
        }else {
            System.out.println("<안내> 비밀번호변경 실패");
        }
    } // ChangePasswordView
}//c end
