package view;

import controller.Control_member;
import model.Dto.MemberDto;

import java.util.Scanner;

public class SearchId {
    Scanner scanner = MainView.getInstance().scanner;

    // 싱글톤
    private SearchId(){}
    private static SearchId SearchId = new SearchId();
    public static SearchId getInstance(){return SearchId;}


    public void SearchId(){
        // 입력
        System.out.print("이름 : "); String mname = scanner.next();
        System.out.print("핸드폰번호 : "); String mphone = scanner.next();

        // 객체화
        MemberDto memberDto = new MemberDto();
        memberDto.setMname(mname);
        memberDto.setMphone(mphone);

        // 저장 후 control에 전달
        String result = Control_member.getInstance().SearchId(memberDto);

        // 결과 출력
        if (result!=null){
        System.out.println("<안내>아이디 : "+result);
        }else {
            System.out.println("<안내> 오류 해당 정보에 맞는 아이디를 찾을 수 없음");
        }
    } // SearchId e
} // c e
