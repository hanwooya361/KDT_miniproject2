package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Blood_Mangement.Model.DTO.MatchingDto;


public class MatchingDao extends BaseDao {
    private MatchingDao( ){ }
    private static final MatchingDao instance = new MatchingDao( );
    public static MatchingDao getInstance( ){ return instance; }

    // [API19] 출고 가능 여부 검사 shipmentCheck( )
        public boolean shipmentCheck( int request_id , int blood_pack_id ){
            String reqBloodType = "";
            String reqStatus = "";
            String packBloodType = "";
            String packStatus = "";
            // 1. 수혈 요청 정보 조회
            try{ 
                String sql1 = "select blood_type, status from transfusion_request where request_id = ?";
                    PreparedStatement ps = conn.prepareStatement( sql1 );
                    ps.setInt( 1 , request_id );
                    ResultSet rs = ps.executeQuery( );
                    if ( rs.next( ) ){
                        reqBloodType = rs.getString( "blood_type" );
                        reqStatus = rs.getString( "status" );
                    } 
                // 2. 혈액팩 조회
                String sql2 = "select blood_type, status from blood_pack where blood_pack_id = ?";
                    PreparedStatement ps2 = conn.prepareStatement( sql2 );
                    ps2.setInt( 1 , blood_pack_id );
                    ResultSet rs2 = ps2.executeQuery( );
                    if( rs2.next( ) ){
                        packBloodType = rs2.getString( "blood_type" );
                        packStatus = rs2. getString( "status" );
                    }
                // 3. 수혈 요청 상태 검사
                if( reqStatus.equals( "대기중" ) && packStatus.equals("보관중") 
                    && reqBloodType.equals(packBloodType) ){ return true; }

                }catch( Exception e ){ System.out.println( e ); 
                }return false;
            } // shipmentCheck end

            // [API18] 수혈 요청 매칭 및 출고 등록 shipmentCreate( )
            public boolean shipmentCreate( int request_id , int blood_pack_id ){
                int member_id = 0;
                try{
                    // 1. 수혈 요청 정보에서 requester_id 조회
                    String sql1 = "select requester_id from transfusion_request where request_id = ?";
                    PreparedStatement ps1 = conn.prepareStatement(sql1);
                    ps1.setInt(1, request_id);
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next()){
                    member_id = rs1.getInt("requester_id");
                    }else{
                        return false;
                    }

                    // 2. matching 테이블에 등록 (insert)
                    String sql2 = "insert into matching(member_id, blood_pack_id) values(?, ?)";
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ps2.setInt(1, member_id);
                    ps2.setInt(2, blood_pack_id);
                    ps2.executeUpdate();

                    // 3. blood_pack 상태 출고완료 및 출고일 변경 (update)
                    String sql3 = " update blood_pack set status = '출고완료' , shipment_date = now() where blood_pack_id = ? ";
                    PreparedStatement ps3 = conn.prepareStatement(sql3);
                    ps3.setInt(1, blood_pack_id );
                    ps3.executeUpdate();

                    // 4. transfusion_request 상태 '완료' 변경 (update)
                    String sql4 = " update transfusion_request set status = '완료' where request_id = ? ";
                    PreparedStatement ps4 = conn.prepareStatement(sql4);
                    ps4.setInt(1, request_id);
                    ps4.executeUpdate();

                    return true; // 2/3/4 문제 없으면 성공 반환
                } catch ( Exception e ){ System.out.println( e ); }  

                return false; // 조회 실패 or 예외 발생(SQL) 실패 반환

            } // shipmentCreate end

            // [API20] 일별/월별 병원 출고 내역 조회
            public ArrayList<MatchingDto> shipmentView(String shipment_date) {
                ArrayList<MatchingDto> list = new ArrayList<>();
                try {
                    // SQL 작성 (DML - select)
                    String sql = "select distinct m.matching_detail_id, m.member_id, m.blood_pack_id, " +
                                "tr.hospital_name, tr.patient_name, bp.blood_type, bp.shipment_date, bp.status " +
                                "from matching m " +
                                "join blood_pack bp on m.blood_pack_id = bp.blood_pack_id " +
                                "join transfusion_request tr on bp.blood_type = tr.blood_type " +
                                "where bp.shipment_date like concat(?, '%') " + // "2026-08-19" 로 시작하는 모든 데이터를 조회
                                "  and bp.status = '출고완료' " +
                                "  and tr.status = '완료' " +
                                "order by bp.shipment_date desc";
                    
                    // PreparedStatement : SQL 실행 객체
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, shipment_date);  // ? 자리에 shipment_date 대입
                    // ResultSet : SQL 실행 결과(조회된 데이터들)
                    ResultSet rs = ps.executeQuery();
                    // while문 : 조회된 데이터가 있을 때까지 반복
                    while (rs.next()) {
                        // DTO 객체 생성 (데이터 담을 그릇)
                        MatchingDto matchingDto = new MatchingDto();
                        matchingDto.setMatching_detail_id(rs.getInt("matching_detail_id"));
                        matchingDto.setMember_id(rs.getInt("member_id"));
                        matchingDto.setBlood_pack_id(rs.getInt("blood_pack_id"));
                        matchingDto.setHospital_name(rs.getString("hospital_name"));
                        matchingDto.setPatient_name(rs.getString("patient_name"));
                        matchingDto.setBlood_type(rs.getString("blood_type"));
                        matchingDto.setShipment_date(rs.getString("shipment_date"));
                        matchingDto.setStatus(rs.getString("status"));
                        
                        // 리스트에 추가
                        list.add(matchingDto);
                    }
                } catch(Exception e) { 
                    System.out.println(e);
                }
                return list;
            } // shipmentView end

            // [API21] 매칭 성공이력 전체 조회
            public ArrayList<MatchingDto> matchingView() {
                ArrayList<MatchingDto> list = new ArrayList<>();
                try {
                    String sql = "select distinct m.matching_detail_id, m.member_id, m.blood_pack_id, " +
                                "tr.hospital_name, tr.patient_name, bp.blood_type, bp.shipment_date, bp.status " +
                                "from matching m " +
                                "join blood_pack bp on m.blood_pack_id = bp.blood_pack_id " +
                                "join transfusion_request tr on bp.blood_type = tr.blood_type " +
                                "where tr.status = '완료' " + 
                                "order by m.matching_detail_id desc";
                    
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
                } catch(Exception e) { 
                    System.out.println(e);
                }
                return list;
            } // matchingView end

            // [API22] 혈액팩 출고상태 수동 수정 구현 shipmentUpdate();
            public boolean shipmentUpdate( MatchingDto matchingDto ){ 
                try{ String sql = "update matching set blood_pack_id = ? where matching_detail_id = ?";

                PreparedStatement ps1 = conn.prepareStatement(sql);
                ps1.setInt( 1, matchingDto.getBlood_pack_id( ) );
                ps1.setInt( 2, matchingDto.getMatching_detail_id( ) );
                int count = ps1.executeUpdate();
                if( count >= 1 ){
                    return true; }
            }catch( Exception e ){ System.out.println( e ); 
            }return false;
            
            } // shipmentUpdate end

            // [API23] 출고 기록 삭제 (보통 이력 관리를 위해 삭제보다는 취소 상태로 업데이트 처리) shipmentDelete(); 
            public boolean shipmentDelete( MatchingDto matchingDto ){
                try{ String sql = "update blood_pack set status = '보관중' , shipment_date = null where blood_pack_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(sql);
                ps1.setInt(1, matchingDto.getBlood_pack_id( ) );
                int count = ps1.executeUpdate();
                if( count >= 1 ){
                    return true; }
                }catch( Exception e ){ System.out.println( e );         
                }return false;

            } // shipmentDelete end

} // class end


