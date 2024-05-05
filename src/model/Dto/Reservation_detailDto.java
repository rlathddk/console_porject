package model.Dto;

public class Reservation_detailDto {
    private int reservation_detail_pk; // 예약상세 primary key
    private int reservation_pk; // (fk) 예약번호
    private int reservation_date_pk; // (fk) 예약날짜

    public Reservation_detailDto(){}

    public Reservation_detailDto(int reservation_detail_pk, int reservation_pk, int reservation_date_pk) {
        this.reservation_detail_pk = reservation_detail_pk;
        this.reservation_pk = reservation_pk;
        this.reservation_date_pk = reservation_date_pk;
    }

    public int getReservation_detail_pk() {
        return reservation_detail_pk;
    }

    public void setReservation_detail_pk(int reservation_detail_pk) {
        this.reservation_detail_pk = reservation_detail_pk;
    }

    public int getReservation_pk() {
        return reservation_pk;
    }

    public void setReservation_pk(int reservation_pk) {
        this.reservation_pk = reservation_pk;
    }

    public int getReservation_date_pk() {
        return reservation_date_pk;
    }

    public void setReservation_date_pk(int reservation_date_pk) {
        this.reservation_date_pk = reservation_date_pk;
    }

    @Override
    public String toString() {
        return "Reservation_detail{" +
                "reservation_detail_pk=" + reservation_detail_pk +
                ", reservation_pk=" + reservation_pk +
                ", reservation_date_pk=" + reservation_date_pk +
                '}';
    }
}
