package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import Blood_Mangement.Controller.MemberController;
import Blood_Mangement.Model.DTO.BloodPackDto;

public class BloodPackDao extends BaseDao{
    private BloodPackDao(){}
    private static final BloodPackDao instance = new BloodPackDao();
    public static BloodPackDao getInstance(){return instance;}
    // 싱글톤
    private BloodPackDao bpd = BloodPackDao.getInstance();
    private MemberController mec = MemberController.getInstance();

    // [1] 혈액팩 등록
    public int bloodCreate(BloodPackDto dto) {
      try {
        int memberId = mec.getLoginMember().getMember_id();
          conn.setAutoCommit(false);
          

          // 1. 최근 2개월 이내 등록 이력 확인
          String checkSql =
              "SELECT donation_id " +
              "FROM donation_history " +
              "WHERE member_id = ? " +
              "AND donation_date >= DATE_SUB(CURDATE(), INTERVAL 2 MONTH) " +
              "LIMIT 1";

          try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
              ps.setInt(1, memberId);

              try (ResultSet rs = ps.executeQuery()) {
                  if (rs.next()) {
                      conn.rollback();
                      return 0;
                  }
              }
          }

          // 2. 기존 헌혈 이력 중 가장 최근 donation_id 조회
          int donationId = 0;

          String findSql =
              "SELECT donation_id " +
              "FROM donation_history " +
              "WHERE member_id = ? " +
              "ORDER BY donation_date DESC, donation_id DESC " +
              "LIMIT 1";

          try (PreparedStatement ps = conn.prepareStatement(findSql)) {
              ps.setInt(1, memberId);

              try (ResultSet rs = ps.executeQuery()) {
                  if (rs.next()) {
                      donationId = rs.getInt("donation_id");
                  }
              }
          }

          // 3. 기존 이력이 있으면 donation_date를 오늘로 변경
          if (donationId != 0) {
              String updateSql =
                  "UPDATE donation_history " +
                  "SET donation_date = CURDATE() " +
                  "WHERE donation_id = ? AND member_id = ?";

              try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                  ps.setInt(1, donationId);
                  ps.setInt(2, memberId);
                  ps.executeUpdate();
              }
          }

          // 4. 이력이 없으면 새 헌혈 이력 생성
          else {
              String insertDonationSql =
                  "INSERT INTO donation_history " +
                  "(member_id, donation_date, created_at) " +
                  "VALUES (?, CURDATE(), CURDATE())";

              try (PreparedStatement ps = conn.prepareStatement(
                      insertDonationSql,
                      Statement.RETURN_GENERATED_KEYS)) {

                  ps.setInt(1, memberId);
                  ps.executeUpdate();

                  try (ResultSet rs = ps.getGeneratedKeys()) {
                      if (rs.next()) {
                          donationId = rs.getInt(1);
                      } else {
                          conn.rollback();
                          return -1;
                      }
                  }
              }
          }

          // 5. 로그인 회원의 donation_id로 혈액팩 등록
          String bloodPackSql =
              "INSERT INTO blood_pack " +
              "(blood_type, donation_id, received_date, expiration_date) " +
              "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 36 DAY))";

          try (PreparedStatement ps = conn.prepareStatement(bloodPackSql)) {
              ps.setString(1, dto.getBlood_type());
              ps.setInt(2, donationId);

              if (ps.executeUpdate() != 1) {
                  conn.rollback();
                  return -1;
              }
          }

          conn.commit();
          return 1;

      } catch (SQLException e) {
          try {
              conn.rollback();
          } catch (SQLException rollbackException) {
              rollbackException.printStackTrace();
          }

          System.out.println("혈액팩 등록 실패 : " + e);
          return -1;

      } finally {
          try {
              conn.setAutoCommit(true);
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
  }

    // [2] 전체 혈액팩 조회
    public ArrayList<BloodPackDto> bloodAllPrint(){
        ArrayList<BloodPackDto> bloodlist = new ArrayList<>();
        try{
            String sql = "select * from blood_pack order by blood_pack_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BloodPackDto bloodpackdto = new BloodPackDto();
                bloodpackdto.setBlood_pack_id(rs.getInt("blood_pack_id"));
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

    // 개별 혈액팩 조회 
    public ArrayList<BloodPackDto> myBloodPrint() {
        int memberId = mec.getLoginMember().getMember_id();
      ArrayList<BloodPackDto> bloodList = new ArrayList<>();

      String sql =
          "SELECT bp.* " +
          "FROM blood_pack bp " +
          "JOIN donation_history dh " +
          "ON bp.donation_id = dh.donation_id " +
          "WHERE dh.member_id = ? " +
          "ORDER BY bp.blood_pack_id";

      try (PreparedStatement ps = conn.prepareStatement(sql)) {
          ps.setInt(1, memberId);

          try (ResultSet rs = ps.executeQuery()) {
              while (rs.next()) {
                  BloodPackDto dto = new BloodPackDto();

                  dto.setBlood_pack_id(rs.getInt("blood_pack_id"));
                  dto.setBlood_type(rs.getString("blood_type"));
                  dto.setDonation_id(rs.getInt("donation_id"));
                  dto.setReceived_date(rs.getString("received_date"));
                  dto.setExpiration_date(rs.getString("expiration_date"));
                  dto.setShipment_date(rs.getString("shipment_date"));
                  dto.setStatus(rs.getString("status"));

                  bloodList.add(dto);
              }
          }
      } catch (SQLException e) {
          System.out.println("내 혈액팩 조회 실패 : " + e);
      }

      return bloodList;
  }
  
    // [3] 잔여 혈액팩 조회
    public ArrayList<BloodPackDto> bloodPrint(String blood_type){
        ArrayList<BloodPackDto> bloodlist = new ArrayList<>();
        try{
            String sql = "select * from blood_pack where blood_type = ? and status = '보관중' order by expiration_date";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, blood_type);
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
        }catch(SQLException e){System.out.println("잔여 혈액팩 조회 실패:"+e);}
        return bloodlist;
    }
    // [4] 유통기한 임박 혈액팩 조회(기준 3일 이내)
    public ArrayList<BloodPackDto> ebloodPrint(){
        ArrayList<BloodPackDto> bloodlist = new ArrayList<>();
        try{
            String sql = "select * from blood_pack where datediff(expiration_date, curdate()) between 0 and 3 and status = '보관중' order by expiration_date";
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
        }catch(SQLException e){System.out.println("잔여 혈액팩 조회 실패:"+e);}
        return bloodlist;
    }
    // [5] 유통기한에 따른 상태 변경(유통기한이 넘을 경우 폐기)
    public int bloodUpdate(){
        try{
            String sql = "update blood_pack set status='폐기' where expiration_date < curdate() and status = '보관중'";
            PreparedStatement ps = conn.prepareStatement(sql);
            int result  = ps.executeUpdate();
            return result;
        }catch(SQLException e){System.out.println("혈액팩 상태 변경 실패"+e);}
        return 0;
    }
    // [6] 혈액팩 정보 삭제
    public boolean bloodDelete(int bloodPackId) {
        int memberId = mec.getLoginMember().getMember_id();
      try {
          String sql =
              "DELETE bp " +
              "FROM blood_pack bp " +
              "JOIN donation_history dh " +
              "ON bp.donation_id = dh.donation_id " +
              "WHERE bp.blood_pack_id = ? " +
              "AND dh.member_id = ? " +
              "AND bp.status = '보관중'";

          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, bloodPackId);
          ps.setInt(2, memberId);

          return ps.executeUpdate() == 1;

      } catch (SQLException e) {
          System.out.println("혈액팩 삭제 실패 : " + e);
          return false;
      }
  }
}
