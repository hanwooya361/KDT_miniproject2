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
        // 수혈 요청과 혈액팩이 실제로 매칭되어 출고 가능한 상태인지 사전에 검증
        public boolean shipmentCheck( int request_id , int blood_pack_id ){
            String reqBloodType = "";
            String reqStatus = "";
            String packBloodType = "";
            String packStatus = "";
            // 수혈 요청 상태가 대기중인가?
            // 혈액팩 상태가 보관중인가?
            // 요청 혈액형과 팩 혈액형이 일치하는가?
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
                System.out.println( "출고 검사 오류: " + e ); 
            }
            return false;
        } // shipmentCheck end


        // [API18] 수혈 요청 매칭 및 출고 등록 shipmentCreate( )
        // 검증을 거친 후 실제로 매칭 데이터를 생성 
        // 관련 테이블들의 상태를 출고 상태로 동기화
        public boolean shipmentCreate( int request_id , int blood_pack_id ){
            try {
                // 먼저 shipmentCheck()를 호출해 조건이 맞는지 검사
                if ( !shipmentCheck(request_id, blood_pack_id) ) {
                    return false; // 조건 불일치 시 등록 중단
                }
                // (INSERT) matching 테이블에 (혈액팩id, 요청id) 매칭 이력을 등록
                String sql1 = "INSERT INTO matching(blood_pack_id, request_id) VALUES(?, ?)";
                PreparedStatement ps1 = conn.prepareStatement(sql1);
                ps1.setInt(1, blood_pack_id);
                ps1.setInt(2, request_id);
                ps1.executeUpdate();
                
                // (UPDATE) blood_pack의 상태를 출고완료로 바꾸고, 
                // 출고일시(shipment_date)를 현재 시간NOW()으로 기록
                String sql2 = "UPDATE blood_pack SET status = '출고완료', shipment_date = NOW() WHERE blood_pack_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, blood_pack_id);
                ps2.executeUpdate();
                // (UPDATE) transfusion_request의 상태를 완료로 변경
                String sql3 = "UPDATE transfusion_request SET status = '완료' WHERE request_id = ?";
                PreparedStatement ps3 = conn.prepareStatement(sql3);
                ps3.setInt(1, request_id);
                ps3.executeUpdate();

                return true; 
            } catch ( Exception e ){ 
                System.out.println( "출고 등록 오류: " + e ); 
            }  

            return false; 
        } // shipmentCreate end

            // [API20] 병원 출고 내역 조회
            // 특정 일자별 출고 내역 조회
            // 지정한 날짜에 출고 완료된 혈액팩과 병원 수혈 요청 정보를 묶어서 조회
            public ArrayList<MatchingDto> shipmentView(String shipment_date) {
            ArrayList<MatchingDto> list = new ArrayList<>();
            try {
                // matching, blood_pack, transfusion_request 3개 테이블을 JOIN
                // bp.shipment_date like concat(?, '%')와 bp.status = '출고완료' 조건을 걸어 사용자가 전달한 날짜에 출고된 데이터만 필터링
                // 최신 출고 순으로 정렬하여 MatchingDto 리스트로 반환
                String sql = "SELECT m.matching_detail_id, m.blood_pack_id, "
                        + "tr.hospital_name, tr.patient_name, "
                        + "bp.blood_type, bp.shipment_date, bp.status "
                        + "FROM matching m "
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
                    
                    matchingDto.setBlood_pack_id(rs.getInt("blood_pack_id"));
                    matchingDto.setHospital_name(rs.getString("hospital_name"));
                    matchingDto.setPatient_name(rs.getString("patient_name"));
                    matchingDto.setBlood_type(rs.getString("blood_type"));
                    matchingDto.setShipment_date(rs.getString("shipment_date"));
                    matchingDto.setStatus(rs.getString("status"));
                    list.add(matchingDto);
                }
                } catch( Exception e ) { 
                    System.out.println( "출고 내역 조회 오류: " + e ); 
                }
                return list;
            } // shipmentView end

            
            // [API21] 매칭 성공 이력 전체 조회
            public ArrayList<MatchingDto> matchingView() {
                ArrayList<MatchingDto> list = new ArrayList<>();
                try {
                    String sql = "select m.matching_detail_id, m.blood_pack_id, "
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
                        
                        matchingDto.setBlood_pack_id(rs.getInt("blood_pack_id"));
                        matchingDto.setHospital_name(rs.getString("hospital_name"));
                        matchingDto.setPatient_name(rs.getString("patient_name"));
                        matchingDto.setBlood_type(rs.getString("blood_type"));
                        matchingDto.setShipment_date(rs.getString("shipment_date"));
                        matchingDto.setStatus(rs.getString("status"));
                        list.add(matchingDto);
                    }
                } catch ( Exception e ) { 
                    System.out.println( "전체 조회 오류: " + e ); 
                }
                return list;
            } // matchingView end

            // [API22] 매칭 혈액팩 변경 수정 (기존팩 보관 복구 + 새 혈액팩 출고완료 처리)
            public boolean shipmentUpdate( MatchingDto matchingDto ){ 
                try{ 
                    // 해당 매칭 번호에 묶여있던 기존 혈액팩 ID 조회
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
                    // blood_pack_id를 새로 전달받은 혈액팩 ID로 수정
                    String sql1 = "UPDATE matching SET blood_pack_id = ? WHERE matching_detail_id = ?";
                    PreparedStatement ps2 = conn.prepareStatement(sql1);
                    ps2.setInt( 1, matchingDto.getBlood_pack_id() );
                    ps2.setInt( 2, matchingDto.getMatching_detail_id() );
                    int count = ps2.executeUpdate();

                    if( count >= 1 ){
                        // 기존 혈액팩의 상태를 다시 보관중, shipment_date를 NULL로 초기화
                        String sql2 = "UPDATE blood_pack SET status = '보관중', shipment_date = NULL WHERE blood_pack_id = ?";
                        PreparedStatement ps3 = conn.prepareStatement(sql2);
                        ps3.setInt(1, oldBloodPackId);
                        ps3.executeUpdate();
                        // 새로 매칭된 혈액팩의 상태를 출고완료, shipment_date를 NOW()로 갱신
                        String sql3 = "UPDATE blood_pack SET status = '출고완료', shipment_date = NOW() WHERE blood_pack_id = ?";
                        PreparedStatement ps4 = conn.prepareStatement(sql3);
                        ps4.setInt(1, matchingDto.getBlood_pack_id());
                        ps4.executeUpdate();

                        return true; 
                    }
                } catch( Exception e ){ 
                    System.out.println( "매칭 수정 오류: " + e ); 
                }
                return false;
            } // shipmentUpdate end

            // [API23] 출고 취소 (매칭 행 물리 삭제 + 혈액팩 보관 복구 + 요청 대기 복구)
            // 출고 완료된 건을 취소, 혈액팩과 수혈 요청을 출고 전 상태로 원상복구
            public boolean shipmentDelete( MatchingDto matchingDto ){
                try {
                    // 취소할 혈액팩에 연결된 수혈 요청 번호 사전 조회
                    String Sql = "SELECT request_id FROM matching WHERE blood_pack_id = ?";
                    PreparedStatement ps1 = conn.prepareStatement(Sql);
                    ps1.setInt(1, matchingDto.getBlood_pack_id());
                    ResultSet rs = ps1.executeQuery();

                    int requestId = 0;
                    if (rs.next()) {
                        // 요청 복구 위해 ID 저장
                        requestId = rs.getInt("request_id"); 
                    } else {
                        // 해당 혈액팩으로 등록된 매칭 데이터가 없으면 취소 불가
                        return false; 
                    }
                    // 혈액팩 재고 원상복구 (출고완료 -> 보관중, 출고일시 -> NULL)
                    String sql2 = "UPDATE blood_pack SET status = '보관중', shipment_date = NULL "
                                    + "WHERE blood_pack_id = ? AND status = '출고완료'";
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ps2.setInt(1, matchingDto.getBlood_pack_id());
                    // executeUpdate()의 결과로 수정된 행 개수를 반환받아 변수에 저장
                    int bpCount = ps2.executeUpdate();
                    // // 혈액팩이 정상적으로 '보관중'으로 복구된 경우에만 다음 단계(삭제/요청 복구) 진행
                    if (bpCount >= 1) { //  성공 시 1 이상
                        // (DELETE) matching 테이블에서 해당 매칭 내역 삭제 
                        String sql3 = "DELETE FROM matching WHERE blood_pack_id = ?";
                        PreparedStatement ps3 = conn.prepareStatement(sql3);
                        ps3.setInt(1, matchingDto.getBlood_pack_id());
                        ps3.executeUpdate();
                        // 수혈 요청 상태를 다시 대기중으로 변경
                        if (requestId != 0) {
                            String sql4 = "UPDATE transfusion_request SET status = '대기중' WHERE request_id = ?";
                            PreparedStatement ps4 = conn.prepareStatement(sql4);
                            ps4.setInt(1, requestId);
                            ps4.executeUpdate();
                        }
                        return true; 
                    }
                } catch( Exception e ){ 
                    System.out.println( "출고 취소 오류: " + e ); 
                }
                return false;
            } // shipmentDelete end

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
            } // checkAdmin end

} // class end




