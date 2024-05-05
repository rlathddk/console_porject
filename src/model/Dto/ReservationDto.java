package model.Dto;

public class ReservationDto {
    private int reservation_pk; // 예약번호 primary key
    private int member_pk; // (fk) 예약한 사람 회원번호
    private int reservation_people; // 예약할 인원
    private int reservation_status; // 예약 상태 0:승인대기 1:승인완료 2:취소 3:리뷰완료

    public ReservationDto(){}

    public ReservationDto(int reservation_pk, int member_pk, int reservation_people, int reservation_status) {
        this.reservation_pk = reservation_pk;
        this.member_pk = member_pk;
        this.reservation_people = reservation_people;
        this.reservation_status = reservation_status;
    }

    public int getReservation_pk() {
        return reservation_pk;
    }

    public void setReservation_pk(int reservation_pk) {
        this.reservation_pk = reservation_pk;
    }

    public int getMember_pk() {
        return member_pk;
    }

    public void setMember_pk(int member_pk) {
        this.member_pk = member_pk;
    }

    public int getReservation_people() {
        return reservation_people;
    }

    public void setReservation_people(int reservation_people) {
        this.reservation_people = reservation_people;
    }

    public int getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(int reservation_status) {
        this.reservation_status = reservation_status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_pk=" + reservation_pk +
                ", member_pk=" + member_pk +
                ", reservation_people=" + reservation_people +
                ", reservation_status=" + reservation_status +
                '}';
    }
}
