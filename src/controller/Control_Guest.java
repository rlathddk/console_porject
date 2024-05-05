package controller;

import model.Dao.Guest_Dao;
import model.Dto.Guest_ReviewDto;
import model.Dto.HouseDto;
import model.Dto.ReservationDto;

import java.util.ArrayList;
import java.util.HashMap;

public class Control_Guest {//class start


    // 싱글톤
    private Control_Guest(){}
    private static Control_Guest control_member = new Control_Guest();
    public static Control_Guest getInstance(){return control_member;}

    // 맨위에 주석 달구 메소드 시작해주세요!!
    // ex ->
    // // 회원가입
    // public String 메소드명(){return "String";}

    //예약내역 출력 메소드
    public ArrayList<HashMap<String, String>> reservationList(){
        ArrayList<HashMap<String, String>> result=Guest_Dao.getInstance().reservationList();
        return result;
    }

    //예약 취소 메소드
    public int deleteReservation(int reservation_pk){
        //예약상태 반환메소드 호출(예약상태가 1:완료 / 3:완료+리뷰완료 상태이면 삭제하지 않는다.
        int reservationStatus=Guest_Dao.getInstance().findReservationStatus(reservation_pk);

        if(reservationStatus==1 || reservationStatus==3){
            return 2;
        }
        else if(reservationStatus==0){//취소 진행
            int result=Guest_Dao.getInstance().deleteReservation(reservation_pk);

            return result;
        } //if end
        else if(reservationStatus==2){//이미 취소됨
            return 3;
        }

        return 0;
    }//m end

    //=====================리뷰관리===========================
    //내 평균평점 출력 메소드
    public float scoreAvg(){
        float result=Guest_Dao.getInstance().scoreAvg();
        return result;
    }

    //내게 등록된 리뷰 출력
    public ArrayList<HashMap<String, String>> myReview(){
        ArrayList<HashMap<String, String>> result=Guest_Dao.getInstance().myReview();
        return result;
    }

    //존재하는 예약번호인지 유효성검사 메소드
    public boolean checkReservationPk(int reservation_pk){
        boolean result=Guest_Dao.getInstance().checkReservationPk(reservation_pk);
        return result;
    }//m end

    //리뷰 가능 내역 출력 메소드 (조건 : 예약일자지남 && 예약상태1(승인완료))
    public ArrayList<HashMap<String, String>> finishReservationList() {
        //dao결과 호출
        ArrayList<HashMap<String, String>> result=Guest_Dao.getInstance().finishReservationList();

        //GuestReviewView로 반환
        return result;
    }//m end

    //리뷰 가능한 예약번호인지 유효성검사 메소드
    public boolean checkFinishReservationList(int reservation_pk){
        boolean result=Guest_Dao.getInstance().checkFinishReservationList(reservation_pk);
        return result;
    }

    //리뷰등록 메소드
    public boolean inputReview(int reservation_pk, Guest_ReviewDto guestReviewDto){
        //예약번호에 해당하는 숙소번호가 있는지 확인
        int housePk=Guest_Dao.getInstance().findHousePk(reservation_pk);
        if(housePk==0){
            return false;
        }

        //dao에 있는 DB입력(review) 메소드 호출
        boolean result=Guest_Dao.getInstance().inputReview(reservation_pk, guestReviewDto);

        if(result){
            //예약상태 3(리뷰완료)로 변경
            result=Guest_Dao.getInstance().changeStatus(reservation_pk,3);
        }
        return result;
    }//m end

    //내가 쓴 리뷰 출력 메소드
    public ArrayList<HashMap<String, String>> printWriteReview(){
        ArrayList<HashMap<String, String>> result=Guest_Dao.getInstance().printWriteReview();
        return result;
    }//m end

    //수정할 리뷰 수정 메소드
    public boolean updateReview(Guest_ReviewDto guestReviewDto){
        //리뷰번호 존재여부 유효성검사
        boolean check=Guest_Dao.getInstance().checkReviewPk(guestReviewDto);
        if(!check){
            return false;
        }

        //리뷰수정 메소드 호출
        boolean result=Guest_Dao.getInstance().updateReview(guestReviewDto);
        return result;
    }//m end

    //리뷰삭제 메소드
    public boolean deleteReview(Guest_ReviewDto guestReviewDto){
        //리뷰번호 존재여부 유효성검사
        boolean check=Guest_Dao.getInstance().checkReviewPk(guestReviewDto);
        if(!check){
            return false;
        }

        //리뷰삭제 메소드 호출
        boolean result=Guest_Dao.getInstance().deleteReview(guestReviewDto);
        return result;
    }//m end




    // 승택 ===========================================================
    public ArrayList<HashMap<String, String>> searchHouse(String region){
        ArrayList<HashMap<String, String>> searchHouse = Guest_Dao.getInstance().searchHouse(region);
        return searchHouse;
    }
    // 승택 end =======================================================

    public boolean insertReservation(ReservationDto reservationDto, int house_pk, String date, int day){

        boolean result = Guest_Dao.getInstance().insertReservation(reservationDto);
        if(result){
            if(Guest_Dao.getInstance().insertReservation_detail(house_pk, date, day)){
                return true;
            }
        }

        return false;
    }
}//class end
