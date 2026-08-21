package Blood_Mangement.Model.DAO;

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

        }catch(SQLException e){System.out.println(e);}
        return false;
    }

    // [2] 전체 혈액팩 조회
    public ArrayList<BloodPackDto> bloodAllPirnt(){
        ArrayList<BloodPackDto> bloodlist = new ArrayList<>();
        try{

        }catch(SQLException e){System.out.println(e);}
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
