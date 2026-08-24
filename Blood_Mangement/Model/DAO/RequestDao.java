package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Blood_Mangement.Model.DTO.RequestDto;

public class RequestDao extends BaseDao {
    private RequestDao() {}
    private static final RequestDao instance = new RequestDao();
    public static RequestDao getInstance() { return instance; }

    public boolean rListAdd(RequestDto requestDto){
        try{
            String sql = "insert into transfusion_request( request_type, patient_name, hospital_name, blood_type, requested_quantity, deadline, created_at) values(?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, requestDto.getRequest_type());
            ps.setString(2, requestDto.getPatient_name());
            ps.setString(3, requestDto.getHospital_name());
            ps.setString(4, requestDto.getBlood_type());
            ps.setInt(5, requestDto.getRequested_quantity());
            ps.setDate(6, java.sql.Date.valueOf(requestDto.getDeadline()));
            ps.setDate(7, java.sql.Date.valueOf(requestDto.getCreated_at()));

            int result = ps.executeUpdate();
            if(result==1) return true;
        }
        catch(SQLException e){
            System.out.println("실패"+e);
        }
        return false;
    }
}
