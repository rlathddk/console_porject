package model.Dao;

import jdk.jfr.Category;
import model.Dto.MemberDto;

public class Member_Dao extends Dao {//class start
    //
    // 싱글톤 ==============================================
    private Member_Dao(){}
    private static Member_Dao member_Dao = new Member_Dao();
    public static Member_Dao getInstance() {return member_Dao;}
    // ======================================================


    // ================================ 회원가입 ================================ //
    public int join(MemberDto memberDto){
        try {
            // SQL 작성
            String sql = "insert into member(mid , mpw , memail, mphone, mname ) values(?, ?, ?, ?, ? ) ";
            // SQL 기재
            ps = conn.prepareStatement(sql);
            // SQL 대입(? 매개변수 대입)
            ps.setString(1, memberDto.getMid());
            ps.setString(2, memberDto.getMpw());
            ps.setString(3, memberDto.getMemail());
            ps.setString(4, memberDto.getMphone());
            ps.setString(5, memberDto.getMname());
            // SQL 실행 [ SQL문이 select 이면 rs = ps.executeQuery(); , insert/update/delete 이면 int count = rs.executeUpdate() ]
            int count = ps.executeUpdate();
            if (count == 1) {   return 0; }
            System.out.println("count = " + count);
        }catch ( Exception e ){  System.out.println(e);  } // SQL 문제 발생.
        // 종료
        return 2;
    } // login e


    // ================================ 아이디 중복검사 ================================ //
    public boolean idCheck( String mid ){
        try {
            // SQL 작성
            String sql = "select mid from member where mid = ? ";
            // SQL 기재
            ps = conn.prepareStatement(sql);
            // SQL 대입(? 매개변수 대입)
            ps.setString(1, mid);
            // SQL 실행
            rs = ps.executeQuery(); // 질의/검색 (select) 결과를 rs 인터페이스 대입한다.
            // SQL 결과
            if (rs.next()) {        // rs.next() : 검색 결과 테이블에서 다음레코드 이동. [ 다음레코드 이동후 존재하면 true , 존재하지 않으면 false ]
                return true; // 중복 있음.
            }
        }catch ( Exception e ){  System.out.println(e);   }
        // 5. 함수종료
        return false; // 중복 없음
    } // e end


    // ================================ 로그인 ================================ //
    public boolean login(MemberDto memberDto){
        try {
            // SQL 작성
            String sql = "select * from member where mid = ? and mpw = ? ";
            // SQL 기재
            ps = conn.prepareStatement(sql); // db 연결
            // SQL 대입(? 매개변수 대입)
            ps.setString(1, memberDto.getMid());
            ps.setString(2, memberDto.getMpw());
            // SQL 실행
            rs = ps.executeQuery();
            while(rs.next()){
                return true;
            }
            // SQL 결과
        }catch ( Exception e ){  System.out.println(e);   }
        // 6. 함수종료
        return false;
    } // login e


    // ================================ 아이디 찾기 ================================ //
    public String SearchId (MemberDto memberDto){
        try {
            // SQL 작성
            String sql = "select mid from member where mname = ? and mphone = ?";
            // SQL 기재
            ps = conn.prepareStatement(sql);
            // SQL 대입
            ps.setString(1, memberDto.getMname());
            ps.setString(2, memberDto.getMphone());
            // SQL 실행
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("mid");
            }
            // SQL 결과
        }catch ( Exception e ){  System.out.println(e);  }
        // 6. 함수종료
        return null;
    }


    // ================================ 비밀번호 찾기 ================================ //
    public String SearchPw (MemberDto memberDto){
        try {
            // SQL 작성
            String sql = "select mpw from member where mid = ? and memail = ?";
            // SQL 기재
            ps = conn.prepareStatement(sql);
            // SQL 대입
            ps.setString(1, memberDto.getMid());
            ps.setString(2, memberDto.getMemail());
            // SQL 실행
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("mpw");
            }
            // SQL 결과
        }catch ( Exception e ){  System.out.println(e);   }
        // 6. 함수종료
        return null;
    }


    // ================================ 비밀번호 변경 ================================ //
    public boolean changePasswordView(MemberDto memberDto){
        try {
            // SQL 작성
            String sql = "update member set mpw = ? where memail = ? "; // 받은 email값이 일치하면 mpw필드 수정
            // update set 수정할필드명 = 새로운값 , 수정할필드명 = 새로운값 where 조건식
            // SQL 기재
            ps = conn.prepareStatement(sql);
            // SQL 대입
            ps.setString(1, memberDto.getMpw());
            ps.setString(2, memberDto.getMemail());
            // SQL 실행
            int count = ps.executeUpdate();
            if(count == 1){return true;}
            // SQL 결과
        }catch ( Exception e ){  System.out.println(e);   }
        // 6. 함수종료
        return false;
    }


    // ================================ 회원탈퇴 ================================ //
    public boolean deleteMemberView(MemberDto memberDto){
        try {
            // SQL 작성
            String sql = "delete from member where mid = ? and mpw = ? and mphone = ?";
            // - delete from 테이블명;
            // SQL 기재
            ps = conn.prepareStatement(sql);
            // SQL 대입
            ps.setString(1, memberDto.getMid());
            ps.setString(2, memberDto.getMpw());
            ps.setString(3, memberDto.getMphone());
            // SQL 실행
            int count = ps.executeUpdate();
            if(count == 1){return true;}
            // SQL 결과
        }catch ( Exception e ){  System.out.println(e);  }
        // 6. 함수종료
        return false;
    }
} // c e
