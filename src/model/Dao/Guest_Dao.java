package model.Dao;

import controller.Control_member;
import model.Dto.Guest_ReviewDto;
import model.Dto.HouseDto;
import model.Dto.ReservationDto;

import java.lang.reflect.Array;
import java.security.cert.Extension;
import java.security.spec.ECField;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Guest_Dao extends Dao{
    // 싱글톤 ==============================================
    private Guest_Dao(){}
    private static Guest_Dao guset_dao = new Guest_Dao();
    public static Guest_Dao getInstance() {return guset_dao;}
    // ======================================================
    //로그인된 아이디의 회원번호 저장
    public int findMemberPk(){
        //현재 로그인한 아이디값 받아오기
        String currentId= Control_member.getInstance().getLogin_id();
        try {
            //아이디로 회원번호 찾는 sql
            String sql = "select member_pk from member where mid=?";
            //sql 기재
            ps = conn.prepareStatement(sql);
            //매개변수 대입(현재 로그인된 아이디)
            ps.setString(1,currentId);
            //sql 실행
            rs=ps.executeQuery();
            //찾은 회원번호 저장
            rs.next();
            int memberNo=rs.getInt("member_pk");
            return memberNo;
        }//try end
        catch (Exception e){
            System.out.println("오류 : "+e);
            return 0;
        }//catch end
    }//m end

    //예약현황 불러오기_예약번호, 숙소이름, 예약날짜, 예약인원, 예약상태
    public ArrayList<HashMap<String, String>> reservationList(){
        //회원번호 찾기 함수 호출
        int memberNo=findMemberPk();

        //불러온 데이터 저장할 배열
        ArrayList<HashMap<String, String>> reservationDtos=new ArrayList<>();

        try{
            //예약내역 추출
            String sql="select reservation_pk, reservation_date, houseName, reservation_people, reservation_status from house join\n" +
                    "(select * from reservation_date join\n" +
                    "(select * from reservation_detail join\n" +
                    "(select * from reservation where member_pk=?) as a\n" +
                    "using(reservation_pk))as b\n" +
                    "using(reservation_date_pk))as c\n" +
                    "using(house_pk) group by reservation_pk;";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //매개변수 대입(현재 로그인된 아이디)
            ps.setInt(1, memberNo);
            //sql 실행
            rs=ps.executeQuery();

            //불러온 데이터 저장
            while(rs.next()){
                //hashMap 객체 생성
                HashMap<String, String> reservationList=new HashMap<>();
                //hashMap에 저장
                reservationList.put("reservation_pk",String.valueOf(  rs.getInt("reservation_pk") ));
                reservationList.put("houseName",String.valueOf( rs.getString( "houseName") ));
                reservationList.put("reservation_date",String.valueOf( rs.getString( "reservation_date") ));
                reservationList.put("reservation_people",String.valueOf( rs.getInt( "reservation_people") ));
                reservationList.put("reservation_status",String.valueOf( rs.getInt("reservation_status") ));

                //ArrayList에 저장
                reservationDtos.add(reservationList);
            }
            //배열에 아무것도 저장되지 않을경우 안내문구 출력
            if (reservationDtos.size()==0){
                return null;
            }
            //저장된 배열 반환
            return reservationDtos;
        }//try end
        catch (Exception e){
            System.out.println("[오류]데이터를 불러오는데 실패했습니다 : "+e);
        }//catch end
        return null;
    }//m end

    //예약취소 메소드
    public int deleteReservation(int reservation_pk){
        try{
            String sql="update reservation set reservation_status=2 where reservation_pk=?;";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //매개변수 대입
            ps.setInt(1,reservation_pk);
            //sql 실행
            int count= ps.executeUpdate();

            if(count==0){
                return 0;
            }

            //예약 취소 성공
            return 1;

        }
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }

        return 0;
    }

    //예약 상태 출력 함수(매개변수 : 예약번호)
    public int findReservationStatus(int reservation_pk){
        try {
            String sql = "select reservation_status from reservation where reservation_pk=?";
            //sql 기재
            ps = conn.prepareStatement(sql);
            //매개변수 대입
            ps.setInt(1,reservation_pk);
            //sql실행
            rs=ps.executeQuery();
            //예약번호에 해당하는 정보는 하나의 레코드
            rs.next();
            int result=rs.getInt("reservation_status");

            //반환
            return result;
        }
        catch(Exception e){
            System.out.println("[오류] : "+e);
        }
        return 0;
    }//m end

    //존재하는 예약번호인지 유효성검사 메소드
    public boolean checkReservationPk(int reservation_pk){
        try {
            //sql 작성
            String sql = "select reservation_pk from reservation_detail where reservation_pk=?;";
            //sql 기재
            ps = conn.prepareStatement(sql);
            //매개변수 대입
            ps.setInt(1,reservation_pk);
            //sql 실행
            rs=ps.executeQuery();

            //만약 조회결과가 존재 할 경우 true 반환
            if(rs.next()){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return false;
    }//m end


//=========================================== 리뷰관리 =================================================
    //내 평균평점 출력 메소드
    public float scoreAvg(){
        //회원번호 찾기 함수 호출
        int memberNo=findMemberPk();

        try{
            //sql 작성
            String sql="select score from host_review where target=?;";
            //sql  기재
            ps=conn.prepareStatement(sql);
            //sql 매개변수 대입
            ps.setInt(1,memberNo);
            //sql 실행
            rs=ps.executeQuery();

            //결과 반환
            float scoreAvg=0; //평균 저장 변수
            int i=0;        //리뷰개수 저장 변수
            while(rs.next()){
                scoreAvg+=rs.getInt(1);
                i++;
            }//w end
            //리뷰 없으면 0 출력
            if(i==0){
                return 0;
            }
            return scoreAvg/i;
        }
        catch(Exception e){
            System.out.println("[오류] : "+e);
        }
        return 0;
    }

    //내게 등록된 리뷰 출력
    public ArrayList<HashMap<String, String>> myReview(){
        int member_pk=findMemberPk();//회원번호 호출
        ArrayList<HashMap<String, String>> myReviews=new ArrayList<>();//데이터 저장 배열 생성

        try{
            //sql작성
            String sql="select houseName, content, score from house join\n" +
                    "(select * from host_review where target=?) as a on house_pk=writer;";
            //sql기재
            ps=conn.prepareStatement(sql);
            //sql매개변수 대입
            ps.setInt(1,member_pk);
            //sql실행
            rs=ps.executeQuery();

            //출력데이터 저장
            while(rs.next()){
                //숙소명 앞 세글자만 추출
                String houseName= rs.getString("houseName").substring(0,3);
                //HashMap에 추출한 데이터 저장
                HashMap<String, String> myReviewMap=new HashMap<>();
                myReviewMap.put("houseName", houseName);
                myReviewMap.put("content", rs.getString("content"));
                myReviewMap.put("score",String.valueOf(rs.getInt("score")));

                //배열에 데이터 저장
                myReviews.add(myReviewMap);
            }//w end

            //배열 반환
            return myReviews;
        }//t end
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return null;
    }//m end

    //리뷰 가능 내역 출력 메소드 (조건 : 예약상태1(승인완료))
    public ArrayList<HashMap<String, String>> finishReservationList(){
        //출력값 저장 배열 생성
        ArrayList<HashMap<String, String>> finishReservations=new ArrayList<>();

        //회원번호 저장 변수
        int member_pk=findMemberPk();
        //sql 실행
        try{
            String sql = "select reservation_pk, reservation_date, houseName from house join\n" +
                    "(select * from reservation_date join\n" +
                    "(select reservation_pk, reservation_date_pk from (select * from reservation where reservation_status=1 and member_pk=?) as a \n" +
                    "join (select * from reservation_detail group by reservation_pk) as b\n" +
                    "using(reservation_pk)) as c\n" +
                    "using(reservation_date_pk)) as d\n" +
                    "using(house_pk);";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //매개변수 대입
            ps.setInt(1,member_pk);
            //실행
            rs=ps.executeQuery();

            //출력값 저장
            while(rs.next()){

                //hashMap 객체 생성
                HashMap<String, String> finishRecords=new HashMap<>();

                //hashMap에 저장(예약번호, 날짜, 숙소이름)
                finishRecords.put("reservation_pk",String.valueOf(rs.getInt("reservation_pk")));
                finishRecords.put("reservation_date",rs.getString("reservation_date"));
                finishRecords.put("houseName",rs.getString("houseName"));
                //배열에 hashMap 저장
                finishReservations.add(finishRecords);
            }
            //배열 반환
            return finishReservations;
        }
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return null;
    }//m end

    //리뷰 가능한 예약번호인지 유효성검사 메소드
    public boolean checkFinishReservationList(int reservation_pk){
        ArrayList<HashMap<String, String>> rsList= finishReservationList();
        for(int i=0; i<rsList.size(); i++){
            if(String.valueOf(reservation_pk).equals(rsList.get(i).get("reservation_pk"))){
                return true;
            }
        }
        return false;
    }//m end

    //예약번호 -> 숙소번호 찾기 메소드
    public int findHousePk(int reservation_pk){
        try{
            //sql 작성
            String sql="select house_pk from ( \n" +
                    "select reservation_date_pk from reservation_detail where reservation_pk=?) as a\n" +
                    "join reservation_date using(reservation_date_pk);";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //sql 매개변수 대입
            ps.setInt(1,reservation_pk);
            //sql 실행
            rs=ps.executeQuery();

            //추출한 데이터 반환
            rs.next();
            return rs.getInt(1);
        }
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return 0;
    }//m end

    //리뷰등록 메소드
    public boolean inputReview(int reservation_pk, Guest_ReviewDto guestReviewDto){
        try{
            int member_pk=findMemberPk();   //로그인한 회원번호
            int house_pk=findHousePk(reservation_pk);   //예약된 숙소번호
            //sql작성
            String sql="insert into guest_review(target,writer,content,score) value(?,?,?,?);";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //sql 매개변수 대입
            ps.setInt(1,house_pk);
            ps.setInt(2,member_pk);
            ps.setString(3,guestReviewDto.getContent());
            ps.setInt(4,guestReviewDto.getScore());
            //sql 실행
            int count=ps.executeUpdate();

            if(count==1) {//실행됐으면 true 반환
                return true;
            }
        }
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return false;
    }//m end

    //예약 상태 변경 메소드[매개변수 : reservation_pk(상태가 바뀌는 곳의 예약번호), newStatus(바뀔 상태)]
    public boolean changeStatus(int reservation_pk, int newStatus){
        try{
            //sql 작성
            String sql="update reservation set reservation_status=? where reservation_pk=?;";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //sql 매개변수 대입
            ps.setInt(1, newStatus);
            ps.setInt(2, reservation_pk);
            //sql 실행
            int count=ps.executeUpdate();
            if(count==1){
                return true;
            }
        }

        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return false;
    }//m end

//--리뷰수정--
    //내가 쓴 리뷰 출력 메소드
    public ArrayList<HashMap<String, String>> printWriteReview(){
        int member_pk=findMemberPk();   //로그인된 회원번호 호출

        //출력값 저장 배열 생성
        ArrayList<HashMap<String, String>> writeReviews=new ArrayList<>();
        try{
            //sql 작성
            String sql="select review_pk, houseName, content, score from house join\n" +
                    "(select * from guest_review where writer=?) as a on house_pk=target;";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //sql 매개변수 대입
            ps.setInt(1, member_pk);
            //sql 실행
            rs=ps.executeQuery();
            while(rs.next()){
                HashMap<String, String> ReviewRecord=new HashMap<>();//저장 객체 생성
                ReviewRecord.put("review_pk",rs.getString("review_pk"));
                ReviewRecord.put("houseName",rs.getString("houseName"));
                ReviewRecord.put("content",rs.getString("content"));
                ReviewRecord.put("score",String.valueOf(rs.getInt("score")));

                //객체 배열에 저장
                writeReviews.add(ReviewRecord);
            }//while end
            //데이터 배열 반환
            return writeReviews;
        }//t end
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return null;
    }//m end

    //수정할 리뷰번호 존재 여부 판별 메소드
    public boolean checkReviewPk(Guest_ReviewDto guestReviewDto){
        try {
            //전달받은 리뷰번호 저장 변수
            int review_pk=guestReviewDto.getReview_pk();
            int member_pk=findMemberPk();   //로그인된 회원번호 호출


            //sql 작성
            String sql = "select review_pk from guest_review where writer=?;";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //sql 매개변수 대입
            ps.setInt(1, member_pk);
            //sql 실행
            rs=ps.executeQuery();
            //결과 반환 : 존재하는 리뷰번호이면 true 반환
            while(rs.next()){
                if(review_pk==rs.getInt(1)){
                    return true;
                }
            }//w end
        }//t end
        catch (Exception e){
            System.out.printf("[오류] : "+e);
        }
        return false;
    }//m end

    //리뷰수정
    public boolean updateReview(Guest_ReviewDto guestReviewDto){
        try{
            //sql작성
            String sql="update guest_review set content=?, score=? where review_pk=?;";
            //sql기재
            ps=conn.prepareStatement(sql);
            //sql매개변수 대입
            ps.setString(1,guestReviewDto.getContent());
            ps.setInt(2,guestReviewDto.getScore());
            ps.setInt(3,guestReviewDto.getReview_pk());
            //sql실행
            int count=ps.executeUpdate();
            //결과반환
            if(count==1){
                return true;
            }
        }
        catch(Exception e){
            System.out.println("[오류] : "+e);
        }
        return false;
    }//m end

    //리뷰삭제 메소드
    public boolean deleteReview(Guest_ReviewDto guestReviewDto){
        try{
            //sql 작성
            String sql="delete from guest_review where review_pk=?;";
            //sql 기재
            ps=conn.prepareStatement(sql);
            //sql 매개변수 대입
            ps.setInt(1, guestReviewDto.getReview_pk());
            //sql 실행
            int count=ps.executeUpdate();
            //결과 반환
            if(count==1){
                return true;
            }//if end
        }//t end
        catch (Exception e){
            System.out.println("[오류] : "+e);
        }
        return false;
    }//m end




    // 승택 ============================================================
    public ArrayList<HashMap<String,String>> searchHouse(String region){
        ArrayList<HashMap<String, String>> searchHouse = new ArrayList<>();

        try {
            String sql = "select house.house_pk, house.houseName, house.region, house.maxPeople, reservation_date.reservation_date,\n" +
                         "reservation_date.day_price from house inner join reservation_date on\n" +
                         "house.house_pk = reservation_date.house_pk where region = ? order by reservation_date.reservation_date asc";

            ps=conn.prepareStatement(sql);
            ps.setString(1, region);
            rs=ps.executeQuery();
            while(rs.next()){
                HashMap<String, String> houseList=new HashMap<>();

                houseList.put("house_pk",String.valueOf( rs.getInt("house_pk")));
                houseList.put("houseName",String.valueOf( rs.getString("houseName")));
                houseList.put("region",String.valueOf( rs.getString("region")));
                houseList.put("maxPeople",String.valueOf( rs.getInt("maxPeople")));
                houseList.put("reservation_date",String.valueOf( rs.getString("reservation_date")));
                houseList.put("day_price",String.valueOf( rs.getInt("day_price")));

                searchHouse.add(houseList);
            }
            if (searchHouse.size()==0){
                System.out.println("등록된 숙소가 없습니다.");
            }
            return searchHouse;

        }catch (Exception e){
            System.out.println(e + "오류");
        }

        return null;
    }

    public boolean insertReservation(ReservationDto reservationDto){
        // reservation 테이블에 레코드 추가
        // guest가 숙소를 예약하였을 때 발생
        try {
            int member_pk = 0;
            String sql = "";
            String id = Control_member.getInstance().getLogin_id();

            // member 테이블에서 member_pk를 먼저 받아오기
            sql = "select member_pk from member where mid = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                member_pk = rs.getInt("member_pk");
            }

            // member_pk = 1;

            sql = "insert into reservation(member_pk, reservation_people, reservation_status) values(?, ?, ?)";
            ps = conn.prepareStatement( sql , Statement.RETURN_GENERATED_KEYS );
            ps.setInt(1, member_pk);
            ps.setInt(2, reservationDto.getReservation_people());
            ps.setInt(3, 0);
            if(ps.executeUpdate() == 1){

                rs = ps.getGeneratedKeys();
                if( rs.next() ){
                    //System.out.println( rs.getInt(1 ) );
                }

                return true;
            }

        }catch (Exception e){
            System.out.println(e);
        }

        return false;
    }

    public boolean insertReservation_detail(int house_pk, String date, int day){
        // reservation_detail 테이블에 레코드 추가
        // guest가 숙소를 예약하였을 때 발생
        try{
            int reservation_pk = 0;
            int reservation_date_pk = 0; // 최대 2주 예약한다고 가정
            int[] house_pk_arr = new int[14];
            String sql = "";
            int count = 0;

            // 방금 등록된 예약에서 reservation_pk를 먼저 가져오기
            sql = "select reservation_pk from reservation";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                reservation_pk = rs.getInt("reservation_pk");
            }
            //System.out.println("reservation_pk :"+reservation_pk);

            sql = "select reservation_date_pk from reservation_date where house_pk = ? and reservation_date = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, house_pk);
            ps.setString(2, date);
            rs = ps.executeQuery();
            if (rs.next()){
                //System.out.println("여기 탄다");
                reservation_date_pk = rs.getInt("reservation_date_pk");
            }
            //System.out.println("house_pk :"+house_pk);
            //System.out.println("reservation_date : "+date);
            //System.out.println("reservation_date_pk : "+reservation_date_pk);

            for(int i=0; i<day; i++) {
                sql = "select house_pk from reservation_date where reservation_date_pk = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, (reservation_date_pk+i));
                rs = ps.executeQuery();
                if (rs.next()){
                    house_pk_arr[i] = rs.getInt("house_pk");
                }
            }
            for(int i=0; i<day; i++) {
                if (house_pk_arr[i] == house_pk_arr[i+1]){
                    // 유효성검사 추가하기???? controller에서 해야하나?
                }
            }

            for(int i=0; i<day; i++) {
                sql = "insert into reservation_detail(reservation_pk, reservation_date_pk) values(?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, reservation_pk);
                ps.setInt(2, (reservation_date_pk+i));
                if (ps.executeUpdate() == 1) {
                    count++;
                }
                if(count == day){
                    return true;
                }
            }


        } catch (Exception e){
            System.out.println("잘못 입력하셨습니다.");
        }

        return false;
    }
    // 승택 end ========================================================


}//c end
