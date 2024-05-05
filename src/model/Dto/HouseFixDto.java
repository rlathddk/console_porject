package model.Dto;

public class HouseFixDto {
    private int reservation_date_pk;
    private String reservation_date;
    private int house_pk;
    private int day_price;
    private String houseName;
    private int member_pk;
    private String region;
    private int maxPeople;

    public HouseFixDto(){}
    public HouseFixDto(int reservation_date_pk, String reservation_date, int house_pk, int day_price, String houseName, int member_pk, String region, int maxPeople) {
        this.reservation_date_pk = reservation_date_pk;
        this.reservation_date = reservation_date;
        this.house_pk = house_pk;
        this.day_price = day_price;
        this.houseName = houseName;
        this.member_pk = member_pk;
        this.region = region;
        this.maxPeople = maxPeople;
    }

    public int getReservation_date_pk() {
        return reservation_date_pk;
    }

    public void setReservation_date_pk(int reservation_date_pk) {
        this.reservation_date_pk = reservation_date_pk;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public int getHouse_pk() {
        return house_pk;
    }

    public void setHouse_pk(int house_pk) {
        this.house_pk = house_pk;
    }

    public int getDay_price() {
        return day_price;
    }

    public void setDay_price(int day_price) {
        this.day_price = day_price;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getMember_pk() {
        return member_pk;
    }

    public void setMember_pk(int member_pk) {
        this.member_pk = member_pk;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }
}
