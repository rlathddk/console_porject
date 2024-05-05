package controller;

import model.Dao.Review_Dao;
import model.Dto.Guest_ReviewDto;
import model.Dto.Host_ReviewDto;
import model.Dto.ReviewWrite_View_Dto;

import java.util.ArrayList;

public class Control_Review {//class start


    // 싱글톤
    private Control_Review(){}
    private static Control_Review control_review = new Control_Review();
    public static Control_Review getInstance(){return control_review;}

    // 맨위에 주석 달구 메소드 시작해주세요!!
    // ex ->
    // // 회원가입
    // public String 메소드명(){return "String";}

    // 전승호 start--------------------------------------

    // 로그인된 호스트 아이디 주고 유저가 등록한 리뷰리스트 받기
    public ArrayList<Guest_ReviewDto> my_house_Review (int ch){
        return Review_Dao.getInstance().my_house_Review(ch);
    }


    // 하우스 식별번호로 하우스이름 받아오기
    public String house_name (int ch){
        return Review_Dao.getInstance().house_name(ch);
    }
    // 작성자 식별번호로 작성자이름 받아오기
    public String member_name(int writer){
        return  Review_Dao.getInstance().member_name(writer);
    }

    // 리뷰 상태가 3인 리뷰 불러오기
        // 1. reservation 에서 status 가 1 인거의 reservation_pk 찾아오기
        // 2. 찾아온 reservation_pk 로 house 식별번호 찾아오기 (이용한 숙소 정보)
    public ArrayList<ReviewWrite_View_Dto> review_write_view(int ch){

        return Review_Dao.getInstance().review_write_view(ch);

    }

    // 리뷰쓰기////////////////////
        // 리뷰쓰기
    public boolean review_write(Host_ReviewDto host_reviewDto){
        if(Review_Dao.getInstance().review_write(host_reviewDto)){return true;}
        return false;
    }
        //리뷰(예약)상태수정
    public void review_status(int no){
        Review_Dao.getInstance().review_status(no);
    }
    // 리뷰수정 ///////////////////
        // 1. 출력 id(텍스트) 주고 내가 작성한 리뷰 배열로 찾아오기(호스트버전)
    public ArrayList<Host_ReviewDto> Host_my_review(int ch){
        return Review_Dao.getInstance().Host_my_review(ch);
    }
        // 2. 새로 작성한 리뷰받아와서 update 하고 결과 반환(true / false )
    public boolean house_Review_update(Host_ReviewDto host_reviewDto){
        return Review_Dao.getInstance().house_Review_update(host_reviewDto);
    }
    // 리뷰삭제
    public boolean house_Review_delete(int 리뷰번호){
        return Review_Dao.getInstance().house_Review_delete(리뷰번호);
    }


    // 전승호 end------------------------------------



}//class end
