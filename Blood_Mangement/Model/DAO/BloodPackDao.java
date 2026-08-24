package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Blood_Mangement.Model.DTO.BloodPackDto;

public class BloodPackDao extends BaseDao{
    private BloodPackDao(){}
    private static final BloodPackDao instance = new BloodPackDao();
    public static BloodPackDao getInstance(){return instance;}
    // 싱글톤
    private BloodPackDao bpd = BloodPackDao.getInstance();

    // [1] 혈액팩 등록
    public boolean bloodCreate(BloodPackDto BloodPackDto){
        try{
            String sql = "insert into blood_pack(blood_type, received_date, expiration_date)values(?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 36 DAY))";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, BloodPackDto.getBlood_type());
            int result = ps.executeUpdate();
            if(result==1) return true;
        }catch(SQLException e){System.out.println("혈액팩 입고 실패"+e);}
        return false;
    }

    // [2] 전체 혈액팩 조회
    public ArrayList<BloodPackDto> bloodAllPrint(){
        ArrayList<BloodPackDto> bloodlist = new ArrayList<>();
        String sql = "select * from blood_pack order by blood_pack_id";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BloodPackDto bloodpackdto = new BloodPackDto();
                bloodpackdto.setBlood_type(rs.getString("blood_type"));
                bloodpackdto.setExpiration_date(rs.getString("expiration_date"));
                bloodpackdto.setReceived_date(rs.getString("received_date"));
                bloodpackdto.setShipment_date(rs.getString("shipment_date"));
                bloodpackdto.setStatus(rs.getString("status"));
                bloodlist.add(bloodpackdto);
            }
        }catch(SQLException e){System.out.println("혈액팩 조회 실패:"+e);}
        return bloodlist;
    }


    // [3] 잔여 혈액팩 조회
    public BloodPackDto bloodPrint(String blood_type){

    }
    // [4] 유통기한 임박 혈액팩 조회(기준 7일 이내)
    public BloodPackDto ebloodPrint(){

    }
    // [5] 유통기한에 따른 상태 변경(유통기한이 넘을 경우 폐기)
    public int bloodUpdate(){

    }
    // [6] 혈액팩 정보 삭제
    public boolean bloodDelete(int blood_pack_id){

    }
}
