package Blood_Mangement.Controller;

import java.util.Scanner;

import Blood_Mangement.Model.DAO.MatchingDao;

public class MatchingController {
    private MatchingController( ){ }
    private static final MatchingController instance = new MatchingController( );
    public static MatchingController getInstance( ){ return instance; }
    private Scanner scan = new Scanner( System.in );
    private MatchingDao matchingDao = MatchingDao.getInstance( );

        public void ShipmentCreate( ){

            // request_id
            System.out.print( "수혈번호 요청: " ); 
            int request_Id = scan.nextInt( );

            // blood_pack_id
            System.out.println( "혈액팩 번호: " );
            int blood_pack_id = scan.nextInt( );

            boolean result = matchingDao.ShipmentCheck(request_Id, blood_pack_id);
            if( result ){ 
                System.out.println( "출고 가능");
            }else{
                System.out.println("출고 불가능");
            }



        } // main end
    
} // class end
