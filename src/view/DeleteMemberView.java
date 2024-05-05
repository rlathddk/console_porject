package view;

import controller.Control_member;
import model.Dto.MemberDto;

import java.util.Scanner;

public class DeleteMemberView {
    Scanner scanner = MainView.getInstance().scanner;

    // 싱글톤
    private DeleteMemberView(){}
    private static DeleteMemberView DeleteMemberView = new DeleteMemberView();
    public static DeleteMemberView getInstance(){return DeleteMemberView;}

    // 회원탈퇴 메소드
    public Boolean deleteMemberView(){
        // 입력
        System.out.print("아이디 : "); String mid = scanner.next();
        System.out.print("비밀번호 : "); String mpw = scanner.next();
        System.out.print("전화번호 : "); String mphone = scanner.next();

        // 객체화
        MemberDto memberDto = new MemberDto();
        memberDto.setMid(mid);
        memberDto.setMpw(mpw);
        memberDto.setMphone(mphone);

        // 저장 후 control에 전달
        boolean result = Control_member.getInstance().deleteMemberView(memberDto);

        // 결과 출력
        if (result){
            System.out.println("<안내> 회원탈퇴 완료");

        }else {
            System.out.println("<안내> 회원탈퇴 실패");
        }
        return result;
    } // DeleteMemberView e
}// d e
