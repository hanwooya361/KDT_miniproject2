package Blood_Mangement.Controller;

import java.util.ArrayList;
import Blood_Mangement.Model.DAO.MatchingDao;
import Blood_Mangement.Model.DTO.MatchingDto;

    public class MatchingController {
        private MatchingController( ){ }
        private static final MatchingController instance = new MatchingController( );
        public static MatchingController getInstance( ){ return instance; }
        private MatchingDao matchingDao = MatchingDao.getInstance( );
        
        // [API18 , 19] 출고 가능 여부 확인 후 등록 Controller
        public boolean shipmentCreate( int request_id , int blood_pack_id) {
            // [API 19] 출고 가능 여부 검사
            boolean result = matchingDao.shipmentCheck( request_id , blood_pack_id );   
            if ( !result ){
                return false; 
            }
                // [API 18] 검증 통과 시 실제 매칭 등록 실행 
                return matchingDao.shipmentCreate( request_id , blood_pack_id );
                } // shipmentCreate end

            // [API20] 병원 출고 내역 조회 Controller
            public ArrayList< MatchingDto > shipmentView( String shipment_date ){
                return matchingDao.shipmentView( shipment_date );
            } // shipmentView end

            // [API21] 매칭 성공 이력 전체 조회 Controller
            public ArrayList< MatchingDto > matchingView( ){
                return matchingDao.matchingView( );
            } // matchingView end

            // [API22] 매칭 혈액팩 변경 수정 Controller
            public boolean shipmentUpdate( MatchingDto matchingDto ) {
                return matchingDao.shipmentUpdate( matchingDto );
            } // shipmentUpdate end

            // [API23] 매칭, 출고 취소 Controller
            public boolean shipmentDelete( MatchingDto matchingDto ) {
                return matchingDao.shipmentDelete( matchingDto );
            } // shipmentDelete end

            public boolean checkAdmin() {
                return matchingDao.checkAdmin();
            }

} // class end
