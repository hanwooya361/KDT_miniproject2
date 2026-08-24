package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Blood_Mangement.Model.DTO.RequestDto;

public class RequestDao extends BaseDao {
    private RequestDao() {}
    private static final RequestDao instance = new RequestDao();
    public static RequestDao getInstance() { return instance; }

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
}
