package model.Dao;

import model.Dto.Guest_ReviewDto;
import model.Dto.Host_ReviewDto;
import model.Dto.ReviewWrite_View_Dto;


import java.util.ArrayList;

public class Review_Dao extends Dao{//class start
    // 싱글톤 ==============================================
    private Review_Dao(){}
    private static Review_Dao review_dao = new Review_Dao();
    public static Review_Dao getInstance() {return review_dao;}
    // ======================================================

    // 전승호 start--------------------------------------
        // 내 숙소에 등록된 리뷰 가져오기  받은값(숙소번호) -> 반환(숙소번호로된 게스트가 쓴 리뷰배열)
    public ArrayList<Guest_ReviewDto> my_house_Review(int ch){
        ArrayList<Guest_ReviewDto> house_review_list = new ArrayList<>();// house_review_list 데이터의집
        try {
            // 1. sql 작성한다
            String sql = "select * from guest_review where target = "+ch+";";
            // 2. sql 기재한다
            ps = conn.prepareStatement(sql);
            // 3. sql 실행한다.
            rs = ps.executeQuery();
            // 4. sql 결과처리


            while (rs.next()){//DB house 데이터 다 가져오기
                Guest_ReviewDto guest_reviewDto = new Guest_ReviewDto();
                guest_reviewDto.setReview_pk(rs.getInt(1));
                guest_reviewDto.setTarget(rs.getInt(2));
                guest_reviewDto.setWriter(rs.getInt(3));
                guest_reviewDto.setContent(rs.getString(4));
                guest_reviewDto.setScore(rs.getInt(5));

                house_review_list.add(guest_reviewDto);
            }

            return house_review_list;
        }catch (Exception e){
            System.out.println("my_house_list 오류"+e);

        }
        return house_review_list;
    }
    // 하우스 식벽번호 -> 하우스 이름반환
    public String house_name (int ch){
        try {
            String sql = "select * from house where house_pk = " + ch + ";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 처리
            String name = "";
            if(rs.next()){
                name=rs.getString("houseName");
            }
            return name;
        }catch (Exception e){
            System.out.println("house_name 오류"+e);
        }

        return "";
    }

    // 작성자 식별번호(회원번호) -> 이름으로 반환
    public String member_name(int writer){
        try {
            String sql = "select * from member where member_pk = "+writer+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 처리
            String name = "";
            if(rs.next()){
                name=rs.getString("mname");
            }
            return name;
        }catch (Exception e){
            System.out.println("house_name 오류"+e);
        }
        return "";
    }

    // 리뷰 작성할수 있는 거 찾아오기
    public ArrayList<ReviewWrite_View_Dto>review_write_view(int ch){
        ArrayList<ReviewWrite_View_Dto> review_write_view = new ArrayList<>();

        try {
            String sql = "select * from reservation inner join reservation_detail on reservation.reservation_pk = reservation_detail.reservation_pk inner join reservation_date on reservation_date.reservation_date_pk = reservation_detail.reservation_date_pk where reservation_status = 1 && house_pk = "+ch+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 처리
            while (rs.next()){
                ReviewWrite_View_Dto reviewWrite_view_dto = new ReviewWrite_View_Dto();
                reviewWrite_view_dto.setReservation_pk(rs.getInt(1));
                reviewWrite_view_dto.setHouse_pk(rs.getInt("house_pk"));
                reviewWrite_view_dto.setMember_pk(rs.getInt("member_pk"));
                reviewWrite_view_dto.setReservation_date(rs.getString("reservation_date"));

                review_write_view.add(reviewWrite_view_dto);
            }
            return review_write_view;

        }catch (Exception e){
            System.out.println("review_write_view 오류"+e);
        }

        return review_write_view;
    }

    // 리뷰등록하기
        // 리뷰저장
    public boolean review_write(Host_ReviewDto host_reviewDto){
        try {
            // 1. SQL 작성 [변수가 들어갈 자리에는 ? 대체한다. ]
            String sql = "insert into host_review(target,writer,content,score) value(?,?,?,?);";
            // 2. SQL 기재
            ps = conn.prepareStatement(sql);
            // ? 매개변수 대입
            ps.setInt(1, host_reviewDto.getTarget());     // 기재된 SQL내 두번째 ? 에 값 대입
            ps.setInt(2, host_reviewDto.getWriter());  // 기재된 SQL내 세번째 ? 에 값 대입
            ps.setString(3, host_reviewDto.getContent());  // 기재된 SQL내 세번째 ? 에 값 대입
            ps.setInt(4, host_reviewDto.getScore());  // 기재된 SQL내 세번째 ? 에 값 대입


            // 3. SQL 실행
            int count = ps.executeUpdate(); // executeUpdate() 기재된 sql 실행하고 insert된 레코드 개수 반환.
            if (count == 1) {
                return true;
            }// 만약에 insert처리된 레코드가 1개이면 등록 성공
            // 4. SQL 결과
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }// method end
        //리뷰(예약)상태수정
    public void review_status(int no){
        try {
            // 1. SQL 작성 [변수가 들어갈 자리에는 ? 대체한다. ]
            String sql = "update reservation set reservation_status = 3 where reservation_pk = "+no+";";
            // 2. SQL 기재
            ps = conn.prepareStatement(sql);
            // ? 매개변수 대입

            // 3. SQL 실행
            ps.executeUpdate(); // executeUpdate() 기재된 sql 실행하고 insert된 레코드 개수 반환.

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // 리뷰수정/////////////
        // 1. 내가 쓴리뷰출력
    public ArrayList<Host_ReviewDto> Host_my_review(int ch){
        ArrayList<Host_ReviewDto> house_review_list = new ArrayList<>();// house_review_list 데이터의집
        try {
            // 1. sql 작성한다
            String sql = "select * from host_review where writer = "+ch+";";
            // 2. sql 기재한다
            ps = conn.prepareStatement(sql);
            // 3. sql 실행한다.
            rs = ps.executeQuery();
            // 4. sql 결과처리

            while (rs.next()){//DB house 데이터 다 가져오기
                Host_ReviewDto host_reviewDto = new Host_ReviewDto();
                host_reviewDto.setReview_pk(rs.getInt(1));
                host_reviewDto.setTarget(rs.getInt(2));
                host_reviewDto.setWriter(rs.getInt(3));
                host_reviewDto.setContent(rs.getString(4));
                host_reviewDto.setScore(rs.getInt(5));

                house_review_list.add(host_reviewDto);
            }

            return house_review_list;
        }catch (Exception e){
            System.out.println("my_house_list 오류"+e);

        }
        return house_review_list;
    }//리뷰수정 - 1. 내가쓴리뷰출력 method end
        // 2. 새로 작성한 리뷰받아와서 update 하고 결과 반환(true / false )
    public boolean house_Review_update(Host_ReviewDto host_reviewDto){
        try {
            // 1. SQL 작성 [변수가 들어갈 자리에는 ? 대체한다. ]
            String sql = "update host_review set content = ?,score=? where writer=? && target = ?;";
            // 2. SQL 기재
            ps = conn.prepareStatement(sql);
            // ? 매개변수 대입
            ps.setString(1, host_reviewDto.getContent());     // 기재된 SQL내 두번째 ? 에 값 대입
            ps.setInt(2, host_reviewDto.getScore());  // 기재된 SQL내 세번째 ? 에 값 대입
            ps.setInt(3, host_reviewDto.getWriter());  // 기재된 SQL내 세번째 ? 에 값 대입
            ps.setInt(4, host_reviewDto.getTarget());  // 기재된 SQL내 세번째 ? 에 값 대입

            // 3. SQL 실행
            ps.executeUpdate(); // executeUpdate() 기재된 sql 실행하고 insert된 레코드 개수 반환.
            return true;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
        // 3.
    public boolean house_Review_delete(int 리뷰번호){
        try {
            // 1. SQL 작성
            String sql = "delete from host_review where review_pk = "+리뷰번호+";";
            // 2. SQL 기재
            ps = conn.prepareStatement(sql);

            // 3. SQL 실행
            int count = ps.executeUpdate();
            // 5. sql 실행결과 + 처리
            if(count == 1){return true;}

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }



    // 전승호 end------------------------------------


}//class end
