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

            // [API20] 일별/월별 병원 출고 내역 조회 shipmentView( )
            public ArrayList< MatchingDto > shipmentView( String shipment_date ){
                ArrayList< MatchingDto > list = new ArrayList<>(); // 레코드 정보 들을 담을 리스트  
                try{
                String sql1 = "select tr.hospital_name, bp.blood_pack_id, bp.blood_type, bp.shipment_date, bp.status, tr.patient_name "
                                + "from blood_pack bp " 
                                + "join matching m using(blood_pack_id) "
                                + "join transfusion_request tr on m.member_id = tr.requester_id "
                                + "where bp.shipment_date like concat(?, '%') and bp.status = '출고완료'";

                PreparedStatement ps1 = conn.prepareStatement(sql1);
                ps1.setString(1, shipment_date);
                ResultSet rs1 = ps1.executeQuery();
                
                while (rs1.next()) {
                    MatchingDto matchingDto = new MatchingDto();
                    matchingDto.setHospital_name(rs1.getString("hospital_name"));
                    matchingDto.setBlood_pack_id(rs1.getInt("blood_pack_id"));
                    matchingDto.setBlood_type( rs1.getString("blood_type") );
                    matchingDto.setShipment_date(rs1.getString("shipment_date"));
                    matchingDto.setStatus( rs1.getString("status") );
                    matchingDto.setPatient_name( rs1.getString("patient_name") );

                    list.add(matchingDto);
                    }
                }catch( Exception e ){ System.out.println( e ); }
                return list;
            } // shipmentView end

            // [API21] 매칭 성공이력 전체 조회 matchingView( )
            public ArrayList<MatchingDto> matchingView() {
                ArrayList<MatchingDto> list = new ArrayList<>(); // 레코드 정보들을 담을 리스트
                try {
                    String sql = "select m.matching_id, m.member_id, m.blood_pack_id, tr.hospital_name, bp.blood_type, bp.shipment_date, bp.status, tr.patient_name "
                            + "from matching m "
                            + "join blood_pack bp on m.blood_pack_id = bp.blood_pack_id "
                            + "join transfusion_request tr on m.member_id = tr.requester_id "
                            + "order by m.matching_id desc";
                    // 2. SQL 기재 및 실행
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    // 3. 레코드 하나씩 DTO에 담아 리스트에 추가
                    while (rs.next()) {
                        MatchingDto matchingDto = new MatchingDto();
                        matchingDto.setMatching_id(rs.getInt("matching_id"));
                        matchingDto.setMember_id(rs.getInt("member_id"));
                        matchingDto.setBlood_pack_id(rs.getInt("blood_pack_id"));
                        matchingDto.setHospital_name(rs.getString("hospital_name"));
                        matchingDto.setBlood_type(rs.getString("blood_type"));
                        matchingDto.setShipment_date(rs.getString("shipment_date"));
                        matchingDto.setStatus(rs.getString("status"));
                        matchingDto.setPatient_name(rs.getString("patient_name"));

                        list.add(matchingDto);
                        }
                }catch(Exception e) { System.out.println(e); }
                return list; // 리스트 반환
            } // matchingView end

            // [API22] 혈액팩 출고상태 수동 수정 구현 shipmentUpdate();
            public boolean shipmentUpdate( MatchingDto matchingDto ){ 
                try{ String sql = "update blood_pack set blood_pack_id = ? where matching_id = ? ";

                PreparedStatement ps1 = conn.prepareStatement(sql);
                ps1.setInt( 1, matchingDto.getBlood_pack_id( ) );
                ps1.setInt( 2, matchingDto.getMatching_id( ) );
                ps1.executeUpdate();
            } catch( Exception e ){ System.out.println( e ); }
            return false;

            } // ShipmentUpdate end

            // [API23] shipmentDelete();
            


                
                   



                    


            

                    
    
        

} // class end


