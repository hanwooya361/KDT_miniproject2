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
    public boolean mAdd(MemberDto memberDto) {
    boolean check = med.logCheck(memberDto.getLogin_id());
    // 가입실패
    if (!check) { 
        System.out.println("아이디가 이미 존재합니다.");
        return false;
    }
    return med.mAdd(memberDto);
}
    // 현재 로그인한 회원<<<<<<<현재 로그인한 회원 가져다 쓰기
    private MemberDto loginMember = null;

    // 로그인
    public boolean mLogin(String login_id, String password){
        MemberDto memberDto = med.mLogin(login_id, password);
        loginMember = memberDto;
        return memberDto != null;
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
    // 회원정보삭제 (탈퇴)
    public boolean mdelete(String login_id){
        return med.mdelete(login_id);
    }

    // 헌혈이력정보삭제
    public boolean ddelete(String login_id){
        return med.ddelete(login_id);
    }



    
}
