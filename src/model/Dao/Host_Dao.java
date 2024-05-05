package model.Dao;

import controller.Control_member;
import model.Dto.*;

import java.util.ArrayList;

public class Host_Dao extends Dao{// class start

    // 싱글톤 ==============================================
    private Host_Dao(){}
    private static Host_Dao host_dao = new Host_Dao();
    public static Host_Dao getInstance() {return host_dao;}
    // ======================================================


    // 전승호 =================================================================
    // id에 해당하는 houst 리스트 가져오기
    public ArrayList<HouseDto> my_house_list (String id) {
        ArrayList<HouseDto> my_house_list = new ArrayList<>();// house 데이터의집
        try {
            int id_no = login_number(id);
            // 1. sql 작성한다
            String sql = "select * from member inner join house on member.member_pk = house.member_pk && member.member_pk ="+id_no+";";
            // 2. sql 기재한다
            ps = conn.prepareStatement(sql);
            // 3. sql 실행한다.
            rs = ps.executeQuery();
            // 4. sql 결과처리


            while (rs.next()){//DB house 데이터 다 가져오기
                HouseDto houseDto = new HouseDto();
                houseDto.setHouse_pk(rs.getInt(7));
                houseDto.setHouseName(rs.getString(8));
                houseDto.setMember_pk(rs.getInt(1));
                houseDto.setRegion(rs.getString(10));
                houseDto.setMaxPeople(rs.getInt(11));

                my_house_list.add(houseDto);
            }

            return my_house_list;
        }catch (Exception e){
            System.out.println("my_house_list 오류"+e);
        }
        return my_house_list;// 없음
    }
    // ID 받아서 회원번호로 반납해주는 메소드
    public int login_number (String id){
        try {
            // 1. sql 작성한다
            String sql = "select member_pk from member where mid = '"+id+"';";
            // 2. sql 기재한다
            ps = conn.prepareStatement(sql);
            // 3. sql 실행한다.
            rs = ps.executeQuery();
            // 4. sql 결과처리
            if(rs.next())
                return rs.getInt("member_pk");
        }catch (Exception e) {
            System.out.println("login_number 오류"+e);
        }
        return 0;
    }

    //
    public ArrayList<HouseFixDto> HouseFix_View (int 하우스번호){
        ArrayList<HouseFixDto> houseFixDtos = new ArrayList<>();
        try {
            // 1. sql 작성한다
            String sql = "select * from reservation_date inner join house on house.house_pk = reservation_date.house_pk where house.house_pk = "+하우스번호+";";
            // 2. sql 기재한다
            ps = conn.prepareStatement(sql);
            // 3. sql 실행한다.
            rs = ps.executeQuery();
            // 4. sql 결과처리


            while (rs.next()){//DB house 데이터 다 가져오기
                HouseFixDto houseFixDto = new HouseFixDto();
                houseFixDto.setReservation_date_pk(rs.getInt(1));
                houseFixDto.setReservation_date(rs.getString(2));
                houseFixDto.setHouse_pk(rs.getInt(3));
                houseFixDto.setDay_price(rs.getInt(4));
                houseFixDto.setHouseName(rs.getString(6));
                houseFixDto.setMember_pk(rs.getInt(7));
                houseFixDto.setRegion(rs.getString(8));
                houseFixDto.setMaxPeople(rs.getInt(9));

                houseFixDtos.add(houseFixDto);
            }

            return houseFixDtos;
        }catch (Exception e){
            System.out.println("my_house_list 오류"+e);
        }
        return null;
    }
// 수정하기 ~~~~
    // 수정 인트ver
    public boolean intHouseFix(ArrayList<HouseFixDto> houseFixDtos,int 항목선택, int 수정선택번호, int 수정내용) {
        try{
            if (항목선택 ==2) {
                String sql = "update reservation_date set day_price = " + 수정내용 + " where reservation_date_pk = " + houseFixDtos.get(수정선택번호).getReservation_date_pk()+ ";";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            } else if (항목선택 ==4) {
                String sql = "update house set maxpeople = "+수정내용+" where house_pk = "+houseFixDtos.get(수정선택번호).getHouse_pk()+";";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            }
        }catch (Exception e){
            System.out.println(e+"intHouseFix");
        }
        return false;
    }
    // 수정 String ver
    public boolean strHouseFix(ArrayList<HouseFixDto> houseFixDtos,int 항목선택, int 수정선택번호, String 수정내용){
        try{
            if (항목선택 ==1) {//날짜
                String sql = "update reservation_date set reservation_date = '"+수정내용+"' where reservation_date_pk = "+houseFixDtos.get(수정선택번호).getReservation_date_pk()+";";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            } else if (항목선택 ==3) {// 지역
                String sql = "update house set region = '"+수정내용+"' where house_pk = "+houseFixDtos.get(수정선택번호).getHouse_pk()+";";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            } else if (항목선택==5) {// 이름
                String sql = "update house set houseName = '"+수정내용+"' where house_pk = "+houseFixDtos.get(수정선택번호).getHouse_pk()+";";
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            }
        }catch (Exception e){
            System.out.println(e+"strHouseFix");
        }
        return false;
    }
/// 삭제 하기
    public boolean deleteHouse(int house_pk){
        try{
            String sql = "delete from house where house_pk = "+house_pk+";";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println(e+"deleteHouse");
        }
        return false;
    }
/// 수락하기
    // 수락하기 - 출력
    public ArrayList<ReservationVIewDto>reservationAcceptView(){
        ArrayList<ReservationVIewDto> my_reservationList = new ArrayList<>();

        // 로그인 ID 로 회원번호 반환
        int member_pk = login_number(Control_member.getInstance().getLogin_id());

        try {
            // 1. sql 작성한다
            String sql = "select reservation_pk, house_pk, reservation_date, member_pk from reservation join\n" +
                    "(select * from reservation_detail join\n" +
                    "(select * from reservation_date join\n" +
                    "(select house_pk, houseName from house where member_pk="+member_pk+")as a\n" +
                    "using(house_pk))as b\n" +
                    "using(reservation_date_pk)) as c\n" +
                    "using(reservation_pk) where reservation_status=0;";
            // 2. sql 기재한다
            ps = conn.prepareStatement(sql);
            // 3. sql 실행한다.
            rs = ps.executeQuery();
            // 4. sql 결과처리

            while (rs.next()){//DB house 데이터 다 가져오기
                ReservationVIewDto reservationVIewDto = new ReservationVIewDto();
                reservationVIewDto.setReservation_pk(rs.getInt("reservation_pk"));
                reservationVIewDto.set집이름(Review_Dao.getInstance().house_name(rs.getInt("house_pk")));
                reservationVIewDto.set날짜(rs.getString("reservation_date"));
                reservationVIewDto.set신청자이름(Review_Dao.getInstance().member_name(rs.getInt("member_pk")));

                my_reservationList.add(reservationVIewDto);
            }

            return my_reservationList;
        }catch (Exception e){
            System.out.println("reservationAcceptView 오류"+e);
        }


        return my_reservationList;
    }
    // 예약선택번호 상태수정
    public boolean updateStatus(int reservation_pk){
        try{
            String sql = "update reservation set reservation_status = 1 where reservation_pk ="+reservation_pk+";";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println(e+"updateStatus");
        }
        return false;
    }

    // 전승호END ================================================================

    // 오승택 =================================================================

    public boolean insertHouse(HouseDto houseDto){

        try{
            int member_pk = 0;
            String sql = "";
            String id = Control_member.getInstance().getLogin_id();

            // member 테이블에서 member_pk를 먼저 받아오기
            sql = "select member_pk from member where mid = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                member_pk = rs.getInt("member_pk");
            }

            //member_pk = 1;

            // member_pk를 먼저 받아와서 오류검사 > house 테이블에 넣기
            sql = "insert into house(houseName, member_pk, region, maxPeople) values(?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, houseDto.getHouseName());
            ps.setInt(2, member_pk);
            ps.setString(3, houseDto.getRegion());
            ps.setInt(4, houseDto.getMaxPeople());

            if(ps.executeUpdate() == 1){
                return true;
            }

        }catch (Exception e){
            System.out.println(e +"DB오류");
        }
        return false;
    }

    public boolean insertReservation_date(HouseDto houseDto, Reservation_dateDto reservation_dateDto, int day){
        try{
            int house_pk = 0;
            String sql = "";
            int result = 0;

            // house_pk를 먼저 받아오기
            sql = "select house_pk from house where houseName = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, houseDto.getHouseName());
            rs = ps.executeQuery();
            if(rs.next()){
                house_pk = rs.getInt("house_pk");
            }

            // 날짜 테이블에 데이터 넣기
            for(int i=1; i<=day; i++) {
                sql = "insert into reservation_date(reservation_date, house_pk, day_price) values(?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, reservation_dateDto.getReservation_date());
                ps.setInt(2, house_pk);
                ps.setInt(3, reservation_dateDto.getDay_price());
                if(ps.executeUpdate() == 1){
                    result++;
                }
            }
            if(day >= 2) { // 2박 이상일때 날짜 변경 함수를 탄다
                changeReservation_date(house_pk, reservation_dateDto);
            }
           if (result == day) {
                return true;
            }
        }catch(Exception e){
            System.out.println(e +"DB오류");
        }
        return false;
    }

    public void changeReservation_date(int house_pk, Reservation_dateDto reservation_dateDto){

        String sql = "";
        int[] reservation_date_pk = new int[14]; // 최대 2주 예약한다고 가정
        int index = 0;

        try{
            // reservation_date_pk를 먼저 가져오고
            sql = "select reservation_date_pk from reservation_date where reservation_date = ? and house_pk = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reservation_dateDto.getReservation_date());
            ps.setInt(2, house_pk);
            rs = ps.executeQuery();
            while(rs.next()){
                reservation_date_pk[index] = rs.getInt("reservation_date_pk");
                index++;
            }

            // reservation_date 테이블의 reservation_date 칼럼을 바꿔준다
            for(int i=1; i<index; i++) {
                sql = "update reservation_date set reservation_date = date_add(reservation_date, interval ? day) where reservation_date_pk = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, i);
                ps.setInt(2, reservation_date_pk[i]);
                ps.executeUpdate();
            }
        }catch (Exception e){
            System.out.println(e + "오류");
        }
    }
    // 오승택END ================================================================


}//class end
