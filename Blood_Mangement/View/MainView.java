package Blood_Mangement.View;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

import Blood_Mangement.Controller.BloodPackController;
import Blood_Mangement.Controller.MatchingController;
import Blood_Mangement.Controller.RequestController;
import Blood_Mangement.Model.DTO.BloodPackDto;
import Blood_Mangement.Model.DTO.MatchingDto;
import Blood_Mangement.Model.DTO.RequestDto;

public class MainView {
    private MainView(){} // 1.
    private static final MainView instance = new MainView(); // 2.
    public static MainView getInstance( ){ return instance; } // 3. 
    private Scanner scan = new Scanner(System.in);
    private BloodPackController bpc = BloodPackController.getInstance();
    private RequestController rc = RequestController.getInstance();
    private MatchingController mc = MatchingController.getInstance();

    public void run(){
        while(true){
            System.out.println("\n==================================================");
            System.out.println("               🩸혈액 관리 시스템🩸");
            System.out.println("==================================================");
            System.out.println("  [1] 혈액팩 재고 관리");
            System.out.println("  [2] 헌혈자 / 헌혈 이력 관리");
            System.out.println("  [3] 수혈 요청 관리");
            System.out.println("  [4] 혈액 출고 / 매칭 관리");
            System.out.println("  [5] 프로그램 종료");
            System.out.print("\n메뉴 선택 > ");
            int mmenu = scan.nextInt();
            if(mmenu==1){bloodPackMenu();}
            else if(mmenu==2){}
            else if(mmenu==3){requestMenu();}
            else if(mmenu==4){matchingMenu();}
            else if(mmenu==5){}
            else{System.out.println("없는 메뉴입니다.");}
        }
    }

    public void bloodPackMenu(){
        while (true){
            System.out.println("==========================");
            System.out.println("[🩸혈액팩 재고 관리🩸]");
            System.out.println("==========================");
            System.out.println("[1] 혈액팩 입고 ");
            System.out.println("[2] 전체 혈액팩 조회");
            System.out.println("[3] 혈액형별 혈액팩 조회");
            System.out.println("[4] 유통기한 임박 혈액팩 조회");
            System.out.println("[5] 혈액팩 정보 삭제");
            System.out.println("[6] 이전 메뉴");
            System.out.print("메뉴 선택 >");
            int bmenu = scan.nextInt();
            if(bmenu == 1){bloodCreate();}
            else if(bmenu == 2){bloodAllPirnt();}
            else if(bmenu == 3){bloodPrint();}
            else if(bmenu == 4){ebloodPrint();}
            else if(bmenu == 5){bloodDelete();}
            else if(bmenu == 6){break;}
            else{System.out.println("잘못된 번호입니다.");}
        }
    }


    // 혈액팩 등록
    public void bloodCreate(){
        System.out.println("==========================");
        System.out.println("🩸혈액팩 입고🩸");
        System.out.println("==========================");
        System.out.print("🩸혈액팩 입력 >");
        String bloodtype = scan.next();
        BloodPackDto dto = new BloodPackDto();
        dto.setBlood_type(bloodtype);
        boolean result = bpc.bloodCreate(dto);
        if(result){
            System.out.println("혈액팩이 정상적으로 입고되었습니다");
            System.out.println("상태 > 보관중");
        }else{System.out.println("혈액팩이 정상적으로 입고에 실패했습니다.");}
    }

    // 혈액팩 전체 조회
    public void bloodAllPirnt(){
        ArrayList<BloodPackDto> result = bpc.bloodAllPirnt();
        for(BloodPackDto bloodpackdto : result){
            System.out.println( "혈액형:" + bloodpackdto.getBlood_type() + "/ 입고일:" + dto.getDonation_id() + "/" + dto.getExpiration_date() + "/" + dto.getReceived_date() + "/" + dto.getShipment_date() + "/" + dto.getStatus());
        }
    }

    // 혈액형별 잔여 혈액팩 조회
    public void bloodPrint(){
        System.out.println("혈액형을 입력해주세요: ");
        String blood_type = scan.next();

    }
    
    // 유통기한 임박 혈액팩 조회
    public void ebloodPrint(){

    }
    
    // 혈액팩 유통기한에 따른 상태 변경
    public void bloodUpdate(){

    }

    // 혈액팩 정보 삭제
    public void bloodDelete(){
        System.out.println();
    }

    // ============= 수혈 요청 관리 ==============
    public void requestMenu(){
        while (true){
            System.out.println("==========================");
            System.out.println("[수혈 요청 관리]");
            System.out.println("==========================");
            System.out.println("[1] 헌혈 요청글 작성");
            System.out.println("[2] 요청 전체 목록 조회");
            System.out.println("[3] 요청 대기 목록 조회");
            System.out.println("[4] 요청 상태 변경");
            System.out.println("[5] 요청 취소");
            System.out.println("[6] 이전 메뉴");
            System.out.print("메뉴 선택 >");
            int rmenu = scan.nextInt();
            if(rmenu == 1){rListAdd();}
            else if(rmenu == 2){rListCheck();}
            else if(rmenu == 3){rWaitListCheck();}
            else if(rmenu == 4){rListUpdate();}
            else if(rmenu == 5){rListDelete();}
            else if(rmenu == 6){break;}
            else{System.out.println("잘못된 번호입니다.");}
        }
    }

    // 헌혈 요청글 작성
    public void rListAdd(){
        System.out.println("=============================");
        System.out.println("[헌혈 요청글 작성]");
        System.out.println("=============================");
        System.out.println("양식에 맞는 정보를 입력해주세요.");
        // 정보 입력 받기
        System.out.print("요청 타입(지정헌혈/혈액요청) > "); 
        String request_type = scan.next();

        System.out.print("환자 이름 >");
        String patient_name = scan.next();

        System.out.print("병원 이름 >");
        String hospital_name = scan.next();

        System.out.print("혈액형 >");
        String blood_type = scan.next();

        System.out.print("요청 수량 >");
        int requested_quantity = scan.nextInt();

        System.out.println("기한 >");
        String deadline1 = scan.next();
        LocalDate deadline = LocalDate.parse(deadline1);
        LocalDate created_at = LocalDate.now();
        
        RequestDto requestDto = new RequestDto(request_type, patient_name, hospital_name, blood_type, requested_quantity, deadline,created_at);
        if (rc.rListAdd(requestDto)) {
            System.out.println("[안내] 요청글 등록 성공");
        } 
        else {
            System.out.println("[오류] 요청글 등록 실패");
        }
    }

    // 요청 전체 목록 조회
    public void rListCheck() {

    }

    // 요청 대기 목록 조회
    public void rWaitListCheck() {

    }

    // 요청 상태 변경
    public void rListUpdate() {

    }

    // 요청 취소
    public void rListDelete() {

    }

    // ==================== 혈액 출고 및 매칭 관리 ====================
    public void matchingMenu( ){
        while( true ){
            System.out.println( "==========================" );
            System.out.println( "💉 혈액 출고 및 매칭 관리 💉" );
            System.out.println( "==========================" );
            System.out.println( "[1] 수혈 매칭 및 출고 등록" );
            System.out.println( "[2] 병원 출고 내역 조회" );
            System.out.println( "[3] 매칭 성공 이력 전체 조회" );
            System.out.println( "[4] 매칭 혈액팩 수정" );
            System.out.println( "[5] 출고 취소" );
            System.out.println( "[6] 이전 메뉴" );
            System.out.print("메뉴 선택 > ");
            int matchMenu = scan.nextInt( );
            if ( matchMenu == 1 ) { shipmentCreate( ); }
            else if ( matchMenu == 2 ){ shipmentView( ); }
            else if ( matchMenu == 3 ){ matchingView( ); }
            else if ( matchMenu == 4 ){ shipmentUpdate( ); }
            else if ( matchMenu == 5 ){ shipmentDelete( ); }
            else if ( matchMenu == 6 ){ break; }
            else { System.out.println( "잘못된 번호입니다." ); }
        } // while end
    } // matchingMenu end

        // [API18 , API19] 수혈 매칭 및 출고 등록
        public void shipmentCreate( ){
            System.out.println( "[수혈 매칭 및 출고 등록]" );
            System.out.print( "수혈 요청 번호 > "); int request_id = scan.nextInt();
            System.out.print( "혈액팩 번호 > " ); int blood_pack_id = scan.nextInt();
            boolean result = mc.shipmentCreate( request_id , blood_pack_id );
            if(result){ System.out.println( "[성공] 수혈 매칭 및 출고 처리가 완료되었습니다" );
            }else{ System.out.println( "[실패] 출고 불가능 (상태 확인 필요)" ); }
        
        } // shipmentCreate end

        // [API20] 병원 출고 내역 조회
        public void shipmentView( ){
            System.out.println("[병원 출고 내역 조회]");
            System.out.print( "조회할 날짜 입력(예시: 2026-08-19) > " ); String shipment_date = scan.next();  
            ArrayList< MatchingDto > list = mc.shipmentView( shipment_date );
            System.out.println( "---------------------------------------" );
            if( list == null ){ 
                System.out.println( "해당 날짜의 출고 내역이 존재하지 않습니다." );
            }else{
                for( MatchingDto matchingDto : list ){
                    System.out.println(
                        "병원: " + matchingDto.getHospital_name( ) +
                        " | 혈액팩번호: " + matchingDto.getBlood_pack_id( ) +
                        " | 혈액형: " + matchingDto.getBlood_type( ) +
                        " | 환자: " + matchingDto.getPatient_name( ) +
                        " | 출고일: " + matchingDto.getShipment_date( ) );
                } // for end
            } // else end
            System.out.println( "---------------------------------------" );
        } // shipmentView end

        // [API21] 매칭 성공이력 전체 조회 
        public void matchingView( ){
            System.out.println("[매칭 성공 이력 전체 조회]");
            ArrayList< MatchingDto > list = mc.matchingView(); 
            System.out.println( "---------------------------------------" );
            if( list == null ){
                System.out.println("매칭 이력이 존재하지 않습니다.");
            }else{
                for( MatchingDto matchingDto : list ){
                    System.out.println(
                        "매칭번호: " + matchingDto.getMatching_id() + 
                        " | 회원번호: " + matchingDto.getMember_id() + 
                        " | 혈액팩번호: " + matchingDto.getBlood_pack_id() + 
                        " | 병원: " + matchingDto.getHospital_name() + 
                        " | 혈액형: " + matchingDto.getBlood_type() + 
                        " | 출고일: " + matchingDto.getShipment_date() );
                } // for end
            } // else end
            System.out.println( "---------------------------------------" );
        } // matchingView end

        // [API22] 매칭 혈액팩 변경 수정
        public void shipmentUpdate( ){
            System.out.println( "[매칭 혈액팩 수정]" );
            System.out.print( "수정할 매칭 상세 번호 > " ); int matching_detail_id = scan.nextInt();
            System.out.print( "새로 연결할 혈액팩 번호 > "); int blood_pack_id = scan.nextInt();

            MatchingDto matchingDto = new MatchingDto();
            matchingDto.setMatching_id(matching_detail_id);
            matchingDto.setBlood_pack_id(blood_pack_id);

            boolean result = mc.shipmentUpdate(matchingDto);

            if( result ){
                System.out.println( "[성공] 매칭 정보 수정 완료" );
            }else{ System.out.println( "[실패] 매칭 정보 수정 실패(번호 확인 필요)" ); }
        
        } // shipmentUpdate end

        // [API23] 출고 취소 (논리적 삭제)
        public void shipmentDelete( ){
            System.out.println( "[출고 취소 및 보관 복구]" );
            System.out.print( " 출고 취소할 혈액팩 번호 > " ); int blood_pack_id = scan.nextInt();

            MatchingDto matchingDto = new MatchingDto();
            matchingDto.setBlood_pack_id(blood_pack_id);

            boolean result = mc.shipmentDelete(matchingDto);

            if( result ){
                System.out.println( "[성공] 출고가 취소되었으며, 혈액팩이 보관중으로 복구되었습니다." );
            }else{ System.out.println( "[실패] 출고 취소 실패(번호 확인 필요)");}

        } // shipmentDelete end


    



} // MainView end
