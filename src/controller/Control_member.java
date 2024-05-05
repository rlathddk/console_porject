package controller;

import model.Dao.Member_Dao;
import model.Dto.MemberDto;

public class Control_member {//class start
    private String login_id = "";

    // 싱글톤
    private Control_member(){}
    private static Control_member control_member = new Control_member();
    public static Control_member getInstance(){return control_member;}
    // 맨위에 주석 달구 메소드 시작해주세요!!
    // ex ->
    // // 회원가입
    // public String 메소드명(){return "String";}

    // ================================ 회원가입 ================================ //
    public int join(MemberDto memberDto){
        // 반환 변수.
        int result = 0;

        // 아이디 중복검사
        if( Member_Dao.getInstance().idCheck( memberDto.getMid() ) ) {
            return 1;
        }
        // 회원가입 요청
        result = Member_Dao.getInstance().join( memberDto );
        // 반환
        return result;
    }

    // ================================ 로그인 ================================ //
    //  로그인 상태 필드

    public boolean login(MemberDto memberDto){
        // 저장 후 dao에 전달
        boolean result = Member_Dao.getInstance().login(memberDto);
        if( result ){
            setLogin_id(memberDto.getMid());
        }
        // 반환
        return result;
    }

    // ================================ 아이디 찾기 ================================ //
    public String SearchId(MemberDto memberDto){
        // 저장 후 dao에 전달
        String result = Member_Dao.getInstance().SearchId(memberDto);

        return result;
    }

    // ================================ 비밀번호 찾기 ================================ //
    public String SearchPw(MemberDto memberDto){
        // 저장 후 dao에 전달
        String result = Member_Dao.getInstance().SearchPw(memberDto);

        // 반환
        return result;
    }

    // ================================ 비밀번호 변경 ================================ //
    public boolean changePasswordView(MemberDto memberDto){

        // 저장 후 dao에 전달
        boolean result = Member_Dao.getInstance().changePasswordView(memberDto);

        // 반환
        return result;

    } // ChangePasswordView e


    // ================================ 회원탈퇴 ================================ //
    public boolean deleteMemberView(MemberDto memberDto){

        // 저장 후 dao에 전달
        boolean result = Member_Dao.getInstance().deleteMemberView(memberDto);

        // 반환
        return result;

    } // ChangePasswordView e


    // =========================================================================== //
    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }


}//class end
