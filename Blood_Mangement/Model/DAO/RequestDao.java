package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import Blood_Mangement.Model.DTO.RequestDto;

public class RequestDao extends BaseDao {
    private RequestDao() {}
    private static final RequestDao instance = new RequestDao();
    public static RequestDao getInstance() { return instance; }

    private ArrayList<RequestDto> rList = new ArrayList<>();

    // 멤버 아이디 찾기
    public int findMemberId(String name) {
        try {
            String sql = "SELECT member_id FROM member WHERE name = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("member_id");
            }

        } catch (SQLException e) {
            System.out.println("회원 조회 실패 : " + e);
        }

        return -1;
    }
    // 헌혈 요청 목록 추가
    public boolean rListAdd(RequestDto requestDto){
        try{
            String sql = "insert into transfusion_request( request_type, requester_id, patient_name, hospital_name, blood_type, requested_quantity, deadline, created_at) values(?, ?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, requestDto.getRequest_type());
            int memberId = findMemberId(requestDto.getMember_name());
            ps.setInt(2, memberId);
            ps.setString(3, requestDto.getPatient_name());
            ps.setString(4, requestDto.getHospital_name());
            ps.setString(5, requestDto.getBlood_type());
            ps.setInt(6, requestDto.getRequested_quantity());
            ps.setDate(7, java.sql.Date.valueOf(requestDto.getDeadline()));
            ps.setDate(8, java.sql.Date.valueOf(requestDto.getCreated_at()));

            int result = ps.executeUpdate();
            if(result==1) return true;
        }
        catch(SQLException e){
            System.out.println("실패"+e);
        }
        return false;
    }

    // 전체 목록 조회
    public ArrayList<RequestDto> rListcheck() {
        try {
            // DB에서 가져오기
            String sql =
            "SELECT r.request_id, " +
            "r.request_type, " +
            "m.name AS member_name, " +
            "r.patient_name, " +
            "r.hospital_name, " +
            "r.blood_type, " +
            "r.requested_quantity, " +
            "r.deadline, " +
            "r.created_at " +
            "FROM transfusion_request r " +
            "JOIN member m ON r.requester_id = m.member_id";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // 가져온거 list에 넣기
            while(rs.next()){
                rList.add(new RequestDto(
                rs.getInt("request_id"),
                rs.getString("request_type"),
                rs.getString("member_name"),
                rs.getString("patient_name"),
                rs.getString("hospital_name"),
                rs.getString("blood_type"),
                rs.getInt("requested_quantity"),
                rs.getDate("deadline").toLocalDate(),
                rs.getDate("created_at").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("회원 조회 실패 : " + e);
        }

        return rList;
    }

    // 요청 대기 목록 조회
    public ArrayList<RequestDto> rWaitListcheck() {
        ArrayList<RequestDto> rList = new ArrayList<>();
        try {
            // DB에서 가져오기
            String sql =
            "SELECT r.request_id, " +
            "r.request_type, " +
            "m.name AS member_name, " +
            "r.patient_name, " +
            "r.hospital_name, " +
            "r.blood_type, " +
            "r.requested_quantity, " +
            "r.deadline, " +
            "r.created_at " +
            "FROM transfusion_request r " +
            "JOIN member m ON r.requester_id = m.member_id " + 
            "WHERE r.status = '대기중' " ;

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // 가져온거 list에 넣기
            while(rs.next()){
                rList.add(new RequestDto(
                rs.getInt("request_id"),
                rs.getString("request_type"),
                rs.getString("member_name"),
                rs.getString("patient_name"),
                rs.getString("hospital_name"),
                rs.getString("blood_type"),
                rs.getInt("requested_quantity"),
                rs.getDate("deadline").toLocalDate(),
                rs.getDate("created_at").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("회원 조회 실패 : " + e);
        }

        return rList;
    }

    // 요청 상태 변경
    public boolean rListUpdate(int request_id, int ch, String value) {
        if( ch == 1 ){
            try{
                String sql = "UPDATE transfusion_request SET blood_type = ? WHERE request_id = ?";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setString(1, value);
                ps.setInt(2, request_id);

                int result = ps.executeUpdate();
                if(result==1) return true;
            }
            catch(SQLException e){
                System.out.println("실패"+e);
            }
            return false;
        }

        else if( ch == 2 ){
            try{
                String sql = "UPDATE transfusion_request SET patient_name = ? WHERE request_id = ?";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setString(1, value);
                ps.setInt(2, request_id);

                int result = ps.executeUpdate();
                if(result==1) return true;
            }
            catch(SQLException e){
                System.out.println("실패"+e);
            }
            return false;
        }

        else if( ch == 3 ){
            try{
                String sql = "UPDATE transfusion_request SET hostpital_name = ? WHERE request_id = ?";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setString(1, value);
                ps.setInt(2, request_id);

                int result = ps.executeUpdate();
                if(result==1) return true;
            }
            catch(SQLException e){
                System.out.println("실패"+e);
            }
            return false;
        }

        else if( ch == 4 ){
            try{
                String sql = "UPDATE transfusion_request SET deadline = ? WHERE request_id = ?";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setDate(1, java.sql.Date.valueOf(value));
                ps.setInt(2, request_id);

                int result = ps.executeUpdate();
                if(result==1) return true;
            }
            catch(SQLException e){
                System.out.println("실패"+e);
            }
            return false;
        }

        else if( ch == 5 ){
            try{
                String sql = "UPDATE transfusion_request SET requested_quantity = ? WHERE request_id = ?";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setInt(1, Integer.parseInt(value));
                ps.setInt(2, request_id);

                int result = ps.executeUpdate();
                if(result==1) return true;
            }
            catch(SQLException e){
                System.out.println("실패"+e);
            }
            return false;
        }
        
        return false;
    }

    // 요청 목록 삭제
    public boolean rListDelete(int request_id){
        try{
                String sql = "delete from transfusion_request WHERE request_id = ?";
                PreparedStatement ps = conn.prepareStatement( sql );
                ps.setInt(1, request_id);

                int result = ps.executeUpdate();
                if(result==1) return true;
            }
            catch(SQLException e){
                System.out.println("실패"+e);
            }
            return false;
    }


} // dao end
