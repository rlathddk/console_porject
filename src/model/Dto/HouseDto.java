package model.Dto;

public class HouseDto {
    private int house_pk; // 숙소 table pk  *****1000000부터 시작
    private String houseName; // 숙소 이름
    private int member_pk; // (fk) 숙소를 등록한 회원번호
    private String region; // 숙소 지역
    private int maxPeople; // 숙소 수용 최대인원

    public HouseDto(){}
    public HouseDto(int house_pk, String houseName, int member_pk, String region, int maxPeople) {
        this.house_pk = house_pk;
        this.houseName = houseName;
        this.member_pk = member_pk;
        this.region = region;
        this.maxPeople = maxPeople;
    }

    public int getHouse_pk() {
        return house_pk;
    }

    public void setHouse_pk(int house_pk) {
        this.house_pk = house_pk;
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

    @Override
    public String toString() {
        return "HouseDto{" +
                "house_pk=" + house_pk +
                ", houseName='" + houseName + '\'' +
                ", member_pk=" + member_pk +
                ", region='" + region + '\'' +
                ", maxPeople=" + maxPeople +
                '}';
    }
}
