package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import Blood_Mangement.Model.DTO.MemberDto;

public class MemberDao extends BaseDao {
    private MemberDao() {}
    private static final MemberDao instance = new MemberDao();
    public static MemberDao getInstance() { return instance; }

    // 가입(저장)함수 
    public boolean mAdd(MemberDto memberDto){
        try {
            String sql = "insert into board(login_id,) values( ? , ? )";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, memberDto.getLogin_id() );
            ps.setString(2, memberDto.getName() );
            ps.setString(3, memberDto.getPhone() );
            ps.setString(4, memberDto.getMember_type() );
            ps.setString(5, memberDto.getDonation_date() );

            int result = ps.executeUpdate();
            if(result == 1) return true;
        } catch (Exception e) {
            System.out.println(e);
        } return false;
    }
    // 전체조회함수


    // 개인조회함수

    // 회원정보수정

    // 헌혈이력정보수정

    // 회원정보삭제

    // 헌혈이력정보삭제

    
}//ce
