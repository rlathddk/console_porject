package model.Dto;

public class ReviewWrite_View_Dto {//class start
    // 전승호 -----------------------------
    private int reservation_pk;     // 예약식별번호
    private int house_pk;
    private int member_pk;
    private String reservation_date;


    public ReviewWrite_View_Dto(){}

    public ReviewWrite_View_Dto(int reservation_pk, int house_pk, int member_pk, String reservation_date) {
        this.reservation_pk = reservation_pk;
        this.house_pk = house_pk;
        this.member_pk = member_pk;
        this.reservation_date = reservation_date;
    }

    public int getReservation_pk() {
        return reservation_pk;
    }

    public void setReservation_pk(int reservation_pk) {
        this.reservation_pk = reservation_pk;
    }

    public int getHouse_pk() {
        return house_pk;
    }

    public void setHouse_pk(int house_pk) {
        this.house_pk = house_pk;
    }

    public int getMember_pk() {
        return member_pk;
    }

    public void setMember_pk(int member_pk) {
        this.member_pk = member_pk;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }
    // 전승호 end -------------------------------------

}//class end
