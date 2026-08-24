package Blood_Mangement.Controller;

import java.util.ArrayList;

import Blood_Mangement.Model.DAO.BloodPackDao;
import Blood_Mangement.Model.DTO.BloodPackDto;

public class BloodPackController {
    private BloodPackController(){}
    private static final BloodPackController instance = new BloodPackController();
    public static BloodPackController getInstance(){return instance;}
    // 싱글톤
    private BloodPackDao bpd = BloodPackDao.getInstance();

    // [1] 등록 컨트롤러
    public boolean bloodCreate(BloodPackDto BloodPackDto){
        boolean result = bpd.bloodCreate(BloodPackDto);
        return result;
    }

    // [2] 전체조회 컨트롤러
    public ArrayList<BloodPackDto> bloodAllPirnt(){
        ArrayList<BloodPackDto> result = bpd.bloodAllPrint();
        return result;
    }

    // [3] 잔여 혈액팩 조회 컨트롤러
    public ArrayList<BloodPackDto> bloodPrint(String blood_type){
        return bpd.bloodPrint(blood_type);
    }

    // [4] 유통기한 임박 혈액팩 조회 컨트롤러
    public ArrayList<BloodPackDto> ebloodPrint(){
        return bpd.ebloodPrint();
    }

    // [5] 혈액팩 상태 변경 컨트롤러
    public int bloodUpdate(){
        return bpd.bloodUpdate();
    }

    // [6] 혈액팩 정보 삭제 컨트롤러
    public boolean bloodDelete(int blood_pack_id){
        return bpd.bloodDelete(blood_pack_id);
    }

}
