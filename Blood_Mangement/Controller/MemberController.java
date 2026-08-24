package Blood_Mangement.Controller;

import java.util.ArrayList;

import Blood_Mangement.Model.DAO.MemberDao;
import Blood_Mangement.Model.DTO.MemberDto;

public class MemberController {

    private MemberController() {}
    private static final MemberController instance = new MemberController();
    public static MemberController getInstance(){ return instance; }
    private MemberDao med = MemberDao.getInstance();

    // 가입
    public boolean mAdd(MemberDto memberDto){
        boolean result = med.mAdd(memberDto);
        return result;
    }
    // 전체조회
    public ArrayList<MemberDto> mView(){
        ArrayList<MemberDto> result = med.mView();
        return result;
    }

    // 개별조회
    public MemberDto minView(){
        MemberDto result = med.minView();
        return result;
    }

    // 회원정보수정
    public boolean mUpdate(MemberDto memberDto, String oldLoginid){
        boolean result = med.mUpdate(memberDto, oldLoginid);
        return result;
    }

    // 헌혈이력정보수정
    public boolean dUpdate(MemberDto memberDto){
        boolean result = med.dUpdate(memberDto);
        return result;
    }
    // 회원정보삭제
    public boolean mdelete(String login_id){
        return med.mdelete(login_id);
    }

    // 헌혈이력정보삭제
    public boolean ddelete(String login_id){
        return med.ddelete(login_id);
    }



    
}
