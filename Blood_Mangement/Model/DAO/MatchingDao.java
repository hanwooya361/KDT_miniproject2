package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Blood_Mangement.Controller.MemberController;
import Blood_Mangement.Model.DTO.MatchingDto;


public class MatchingDao extends BaseDao {
    private MatchingDao( ){ }
    private static final MatchingDao instance = new MatchingDao( );
    public static MatchingDao getInstance( ){ return instance; }
    private MemberController mc = MemberController.getInstance();

        // [API19] 출고 가능 여부 검사 shipmentCheck( )
        public boolean shipmentCheck( int request_id , int blood_pack_id ){
            String reqBloodType = "";
            String reqStatus = "";
            String packBloodType = "";
            String packStatus = "";
            
            try { 
                String sql1 = "SELECT blood_type, status FROM transfusion_request WHERE request_id = ?";
                PreparedStatement ps1 = conn.prepareStatement( sql1 );
                ps1.setInt( 1 , request_id );
                ResultSet rs1 = ps1.executeQuery();
                
                if ( rs1.next() ){
                    reqBloodType = rs1.getString( "blood_type" );
                    reqStatus = rs1.getString( "status" );
                } else {
                    return false;
                }

                String sql2 = "SELECT blood_type, status FROM blood_pack WHERE blood_pack_id = ?";
                PreparedStatement ps2 = conn.prepareStatement( sql2 );
                ps2.setInt( 1 , blood_pack_id );
                ResultSet rs2 = ps2.executeQuery();
                
                if ( rs2.next() ){
                    packBloodType = rs2.getString( "blood_type" );
                    packStatus = rs2.getString( "status" );
                } else {
                    return false;
                }

                if ( "대기중".equals(reqStatus) && 
                     "보관중".equals(packStatus) && 
                     reqBloodType.equals(packBloodType) ){
                    return true; 
                }

            } catch ( Exception e ){ 
                System.out.println("출고 검사 오류: " + e); 
            }
            return false;
        } // shipmentCheck end


        // [API18] 수혈 요청 매칭 및 출고 등록 shipmentCreate( )
        public boolean shipmentCreate( int request_id , int blood_pack_id ){
            try {
                // 1. 등록 전 사전 검사 (혈액형 일치 여부 및 대기중/보관중 상태 확인)
                if ( !shipmentCheck(request_id, blood_pack_id) ) {
                    return false; // 조건 불일치 시 등록 중단
                }
                String sql1 = "INSERT INTO matching(blood_pack_id, request_id) VALUES(?, ?)";
                PreparedStatement ps1 = conn.prepareStatement(sql1);
                ps1.setInt(1, blood_pack_id);
                ps1.setInt(2, request_id);
                ps1.executeUpdate();
                
                String sql2 = "UPDATE blood_pack SET status = '출고완료', shipment_date = NOW() WHERE blood_pack_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, blood_pack_id);
                ps2.executeUpdate();

                String sql3 = "UPDATE transfusion_request SET status = '완료' WHERE request_id = ?";
                PreparedStatement ps3 = conn.prepareStatement(sql3);
                ps3.setInt(1, request_id);
                ps3.executeUpdate();

                return true; 
            } catch ( Exception e ){ 
                System.out.println("출고 등록 오류: " + e); 
            }  

            return false; 
        } // shipmentCreate end

            // [API20] 병원 출고 내역 조회
            public ArrayList<MatchingDto> shipmentView(String shipment_date) {
            ArrayList<MatchingDto> list = new ArrayList<>();
            try {
                String sql = "select m.matching_detail_id, tr.requester_id as member_id, m.blood_pack_id, "
                        + "tr.hospital_name, tr.patient_name, "
                        + "bp.blood_type, bp.shipment_date, bp.status "
                        + "from matching m "
                        + "join blood_pack bp on m.blood_pack_id = bp.blood_pack_id "
                        + "join transfusion_request tr on m.request_id = tr.request_id "
                        + "where bp.shipment_date like concat(?, '%') and bp.status = '출고완료' "
                        + "order by bp.shipment_date desc";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, shipment_date);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    MatchingDto matchingDto = new MatchingDto();
                    matchingDto.setMatching_detail_id(rs.getInt("matching_detail_id"));
                    matchingDto.setMember_id(rs.getInt("member_id"));
                    matchingDto.setBlood_pack_id(rs.getInt("blood_pack_id"));
                    matchingDto.setHospital_name(rs.getString("hospital_name"));
                    matchingDto.setPatient_name(rs.getString("patient_name"));
                    matchingDto.setBlood_type(rs.getString("blood_type"));
                    matchingDto.setShipment_date(rs.getString("shipment_date"));
                    matchingDto.setStatus(rs.getString("status"));
                    list.add(matchingDto);
                }
                } catch( Exception e ) { 
                    System.out.println("출고 내역 조회 오류: " + e); 
                }
                return list;
            } // shipmentView end

            
            // [API21] 매칭 성공 이력 전체 조회
            public ArrayList<MatchingDto> matchingView() {
                ArrayList<MatchingDto> list = new ArrayList<>();
                try {
                    String sql = "select m.matching_detail_id, tr.requester_id as member_id, m.blood_pack_id, "
                            + "tr.hospital_name, tr.patient_name, "
                            + "bp.blood_type, bp.shipment_date, bp.status "
                            + "from matching m "
                            + "join blood_pack bp on m.blood_pack_id = bp.blood_pack_id "
                            + "join transfusion_request tr on m.request_id = tr.request_id "
                            + "where bp.status = '출고완료' "
                            + "order by m.matching_detail_id desc";

                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        MatchingDto matchingDto = new MatchingDto();
                        matchingDto.setMatching_detail_id(rs.getInt("matching_detail_id"));
                        matchingDto.setMember_id(rs.getInt("member_id"));
                        matchingDto.setBlood_pack_id(rs.getInt("blood_pack_id"));
                        matchingDto.setHospital_name(rs.getString("hospital_name"));
                        matchingDto.setPatient_name(rs.getString("patient_name"));
                        matchingDto.setBlood_type(rs.getString("blood_type"));
                        matchingDto.setShipment_date(rs.getString("shipment_date"));
                        matchingDto.setStatus(rs.getString("status"));
                        list.add(matchingDto);
                    }
                } catch ( Exception e ) { 
                    System.out.println( e ); 
                }
                return list;
            } // matchingView end

            // [API22] 매칭 혈액팩 변경 수정 (기존팩 보관 복구 + 새 혈액팩 출고완료 처리)
            public boolean shipmentUpdate( MatchingDto matchingDto ){ 
                try{ 
                    String Sql = "SELECT blood_pack_id FROM matching WHERE matching_detail_id = ?";
                    PreparedStatement ps1 = conn.prepareStatement(Sql);
                    ps1.setInt(1, matchingDto.getMatching_detail_id());
                    ResultSet rs = ps1.executeQuery();
                    
                    int oldBloodPackId = 0;
                    if (rs.next()) {
                        oldBloodPackId = rs.getInt("blood_pack_id");
                    } else {
                        return false;
                    }

                    String sql1 = "UPDATE matching SET blood_pack_id = ? WHERE matching_detail_id = ?";
                    PreparedStatement ps2 = conn.prepareStatement(sql1);
                    ps2.setInt( 1, matchingDto.getBlood_pack_id() );
                    ps2.setInt( 2, matchingDto.getMatching_detail_id() );
                    int count = ps2.executeUpdate();

                    if( count >= 1 ){
                        String sql2 = "UPDATE blood_pack SET status = '보관중', shipment_date = NULL WHERE blood_pack_id = ?";
                        PreparedStatement ps3 = conn.prepareStatement(sql2);
                        ps3.setInt(1, oldBloodPackId);
                        ps3.executeUpdate();

                        String sql3 = "UPDATE blood_pack SET status = '출고완료', shipment_date = NOW() WHERE blood_pack_id = ?";
                        PreparedStatement ps4 = conn.prepareStatement(sql3);
                        ps4.setInt(1, matchingDto.getBlood_pack_id());
                        ps4.executeUpdate();

                        return true; 
                    }
                } catch( Exception e ){ 
                    System.out.println( e ); 
                }
                return false;
            } // shipmentUpdate end

            // [API23] 출고 취소 (매칭 행 물리 삭제 + 혈액팩 보관 복구 + 요청 대기 복구)
            public boolean shipmentDelete( MatchingDto matchingDto ){
                try {

                    String Sql = "SELECT request_id FROM matching WHERE blood_pack_id = ?";
                    PreparedStatement ps1 = conn.prepareStatement(Sql);
                    ps1.setInt(1, matchingDto.getBlood_pack_id());
                    ResultSet rs = ps1.executeQuery();

                    int requestId = 0;
                    if (rs.next()) {
                        requestId = rs.getInt("request_id");
                    } else {
                        return false; 
                    }

                    String sql2 = "UPDATE blood_pack SET status = '보관중', shipment_date = NULL "
                                    + "WHERE blood_pack_id = ? AND status = '출고완료'";
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ps2.setInt(1, matchingDto.getBlood_pack_id());
                    int bpCount = ps2.executeUpdate();

                    if (bpCount >= 1) {
                        String sql3 = "DELETE FROM matching WHERE blood_pack_id = ?";
                        PreparedStatement ps3 = conn.prepareStatement(sql3);
                        ps3.setInt(1, matchingDto.getBlood_pack_id());
                        ps3.executeUpdate();

                        if (requestId != 0) {
                            String sql4 = "UPDATE transfusion_request SET status = '대기중' WHERE request_id = ?";
                            PreparedStatement ps4 = conn.prepareStatement(sql4);
                            ps4.setInt(1, requestId);
                            ps4.executeUpdate();
                        }
                        return true;
                    }
                } catch( Exception e ){ 
                    System.out.println( e ); 
                }
                return false;
            }

            public boolean checkAdmin() {
                try{ String sql = "select member_type from member where member_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(sql);
                ps1.setInt(1, mc.getLoginMember().getMember_id()) ;

                ResultSet rs = ps1.executeQuery();
                if(rs.next()){
                    if( rs.getString("member_type").equals("관리자") ) {
                    return true;
                }
                }
                
                }catch( Exception e ){ System.out.println( e ); 
             } return false;
            } // shipmentDelete end
} // class end




