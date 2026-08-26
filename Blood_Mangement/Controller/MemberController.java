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


    // 현재 로그인한 회원 정보 반환
    public MemberDto getLoginMember() {
        return loginMember;
    }
 
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
    public boolean mUpdate(String oldLoginid, int ch, String value) {
        // 1. 수정할 정보를 담을 DTO 객체 생성
        MemberDto memberDto = new MemberDto();

        if (ch == 1) {
            memberDto.setLogin_id(value);
        } else if (ch == 2) {
            memberDto.setPassword(value);
        } else if (ch == 3) {
            memberDto.setName(value);
        } else if (ch == 4) {
            memberDto.setPhone(value);
        } else if (ch == 5) {
            memberDto.setMember_type(value);
        } else {
            return false; 
        }

        // 3. DTO와 선택번호, 기존아이디를 DAO로 전달
        return med.mUpdate(memberDto, ch, oldLoginid);
    }

    // 헌혈이력정보수정/삭제
    
    // [1] 헌혈 이력 수정
    public boolean dUpdate(MemberDto memberDto) {
        return med.dUpdate(memberDto);
    }
    // [2] 헌혈 이력 삭제
    public boolean ddelete(String login_id) {
        return med.ddelete(login_id);
    }


    // 회원정보삭제 (탈퇴)
    public boolean mdelete(String login_id, String password){
        return med.mdelete(login_id, password);
    }

    



    
}
