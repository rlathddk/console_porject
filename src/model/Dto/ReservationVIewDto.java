package model.Dto;

public class ReservationVIewDto {
    private int reservation_pk;
    private String 집이름;
    private String 날짜;
    private String 신청자이름;

    public ReservationVIewDto(){}
    public ReservationVIewDto(int reservation_pk, String 집이름, String 날짜, String 신청자이름) {
        this.reservation_pk = reservation_pk;
        this.집이름 = 집이름;
        this.날짜 = 날짜;
        this.신청자이름 = 신청자이름;
    }

    public int getReservation_pk() {
        return reservation_pk;
    }

    public void setReservation_pk(int reservation_pk) {
        this.reservation_pk = reservation_pk;
    }

    public String get집이름() {
        return 집이름;
    }

    public void set집이름(String 집이름) {
        this.집이름 = 집이름;
    }

    public String get날짜() {
        return 날짜;
    }

    public void set날짜(String 날짜) {
        this.날짜 = 날짜;
    }

    public String get신청자이름() {
        return 신청자이름;
    }

    public void set신청자이름(String 신청자이름) {
        this.신청자이름 = 신청자이름;
    }
}
