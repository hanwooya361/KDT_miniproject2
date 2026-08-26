package Blood_Mangement.Model.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Blood_Mangement.Model.DTO.MemberDto;

public class MemberDao extends BaseDao {
    private MemberDao() {}
    private static final MemberDao instance = new MemberDao();
    public static MemberDao getInstance() { return instance; }

    // 가입(저장)함수 
    public boolean mAdd(MemberDto memberDto){
        try {
            String sql = "insert into member(login_id, password, name, phone, member_type) values( ? , ?, ? , ? , ? )";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, memberDto.getLogin_id() );
            ps.setString(2, memberDto.getPassword() );
            ps.setString(3, memberDto.getName() );
            ps.setString(4, memberDto.getPhone() );
            ps.setString(5, memberDto.getMember_type() );
            
            int result = ps.executeUpdate();
            if(result == 1) return true;
        } catch (Exception e) {
            System.out.println(e);
        } return false;
    }

    // 아이디 유효성 검사
    public boolean logCheck(String login_id){
        try{
            String sql = "select * from member where login_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login_id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return true;
    }


    // 로그인 함수
    public MemberDto mLogin(String login_id, String password){
        try {
            // SQL 작성: 입력받은 로그인,비번이 모두 일치하는 레코드 조회
            String sql = "SELECT * FROM member WHERE login_id = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login_id);
            ps.setString(2, password); // MemberDto에 getPassword()가 구현되어 있어야 합니다.
            
            ResultSet rs = ps.executeQuery();
            
            // 결과 확인 (조회된 행이 1개라도 있으면 로그인 성공)
            if (rs.next()) {
                MemberDto memberDto = new MemberDto();
                memberDto.setMember_id(rs.getInt("member_id"));
                memberDto.setLogin_id(rs.getString("login_id"));
                memberDto.setPassword(rs.getString("password"));
                memberDto.setName(rs.getString("name"));
                memberDto.setPhone(rs.getString("phone"));
                memberDto.setMember_type(rs.getString("member_type"));
                return memberDto;
            }
        } catch (Exception e) {
            System.out.println("로그인 오류: " + e);
        }
        return null;
    }
    

    // 전체조회함수
    public ArrayList<MemberDto> mView(){
        ArrayList<MemberDto> mlist = new ArrayList<>();
        try {
            String sql = "select * from member m inner join donation_history dh on m.member_id_pk = dh.member_id_fk;"; // 2.1 SQL 작성한다.
            PreparedStatement ps = conn.prepareStatement( sql );
            ResultSet rs =  ps.executeQuery();
            
            while (rs.next() ) {
                MemberDto memberDto = new MemberDto();
                memberDto.setLogin_id(rs.getString("login_id"));    //삭제
                memberDto.setName(rs.getString("name"));
                memberDto.setPhone(rs.getString("phone"));
                memberDto.setMember_type(rs.getString("member_type"));
                memberDto.setDonation_date(rs.getString("donation_date"));
                mlist.add(memberDto);
            }
        } catch (Exception e) {
            System.out.println(e);
        } return mlist;
    }

    // 개별조회함수
    public MemberDto minView(){
        MemberDto minlist = new MemberDto();
        try {
            String sql = "select login_id, name, phone, Member_type, donation_date from member m inner join donation_history dh on m.member_id_pk = dh.member_id_fk where login_id = ?;";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, minlist.getLogin_id());
            ResultSet rs =  ps.executeQuery();
            
            while (rs.next() ) {
                MemberDto memberDto = new MemberDto();
                memberDto.setLogin_id(rs.getString("login_id"));
                memberDto.setName(rs.getString("name"));
                memberDto.setPhone(rs.getString("phone"));
                memberDto.setMember_type(rs.getString("member_type"));
                memberDto.setDonation_date(rs.getString("donation_date"));
                minlist = memberDto;    ///////////????????
            }
        } catch (Exception e) {
            System.out.println(e);
        } return minlist;
    }

    // 회원정보수정
    public boolean mUpdate(MemberDto memberDto, int ch, String oldLoginid) {
    String sql = "";
    if (ch == 1) sql = "UPDATE member SET login_id = ? WHERE login_id = ?";
    else if (ch == 2) sql = "UPDATE member SET password = ? WHERE login_id = ?";
    else if (ch == 3) sql = "UPDATE member SET name = ? WHERE login_id = ?";
    else if (ch == 4) sql = "UPDATE member SET phone = ? WHERE login_id = ?";
    else if (ch == 5) sql = "UPDATE member SET member_type = ? WHERE login_id = ?";
    else return false;

    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        
        // 선택 항목만
        if (ch == 1) ps.setString(1, memberDto.getLogin_id());
        else if (ch == 2) ps.setString(1, memberDto.getPassword());
        else if (ch == 3) ps.setString(1, memberDto.getName());
        else if (ch == 4) ps.setString(1, memberDto.getPhone());
        else if (ch == 5) ps.setString(1, memberDto.getMember_type());
        
        ps.setString(2, oldLoginid);
        return ps.executeUpdate() == 1;
    } catch (SQLException e) {
        System.out.println(e);
    }
    return false;
}

    // 헌혈이력정보수정
    public boolean dUpdate(MemberDto memberDto){
        try {
            String sql = "update donation_history set donation_date = ? where login_id = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, memberDto.getDonation_date());
            ps.setString(2, memberDto.getLogin_id());
            int result = ps.executeUpdate();
            if (result == 1) {return true;}
        } catch (SQLException e) {
            System.out.println(e);
        }return false;
    }
    // 헌혈이력정보만삭제
    public boolean ddelete( String login_id ){
        try{ String sql = "delete from donation_history where login_id = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString( 1 , login_id ); // SQL 문법내 첫번째 ? 에 매개변수 값 대입 
            int result = ps.executeUpdate();
            if( result == 1 ) return true;
        }catch( SQLException e ){ System.out.println( e ); }
        return false;
    }


    // 회원정보삭제(탈퇴)
    public boolean mdelete( String login_id, String password ){
        try{
            String checkSql = "SELECT password FROM member WHERE login_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, login_id);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                // 입력한 비밀번호와 DB 비밀번호가 다르면 삭제X
                if (!dbPassword.equals(password)) {
                    return false; 
                }
            } else {
                return false; // 존재하지 않는 아이디
            }

            // 비밀번호 일치 시 자식 테이블 삭제
            String sql1 = "DELETE FROM donation_history WHERE login_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, login_id);
            ps1.executeUpdate();

            // 부모 테이블 삭제
            String sql2 = "DELETE FROM member WHERE login_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, login_id);

            return ps2.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    

    
}//ce
