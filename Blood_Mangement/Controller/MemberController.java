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

    // 헌혈이력정보수정

    // 회원정보삭제

    // 헌혈이력정보삭제



    
}
