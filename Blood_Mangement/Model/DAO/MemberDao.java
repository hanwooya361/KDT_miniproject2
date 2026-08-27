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
            String sql = "SELECT * FROM member WHERE login_id = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login_id);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();

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
            String sql = "SELECT m.login_id, m.name, m.phone, m.member_type, dh.donation_date " +
                     "FROM member m LEFT JOIN donation_history dh ON m.member_id = dh.member_id"; 
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

    // 개별 회원 조회 (매개변수로 조회할 login_id를 직접 입력받기)
    public MemberDto minView(String login_id) {
        MemberDto memberDto = null; // 초기값을 null로 설정 (조회 결과 없을 때 구분용
        try {
            String sql = "SELECT m.login_id, m.name, m.phone, m.member_type, dh.donation_date " +
                     "FROM member m LEFT JOIN donation_history dh ON m.member_id = dh.member_id " +
                     "WHERE m.login_id = ?";
                        
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login_id); // 입력받은 login_id 바인딩
            ResultSet rs = ps.executeQuery();
            
            // 개별 조회의 결과는 1건이므로 while 대신 if 사용
            if (rs.next()) {
                memberDto = new MemberDto();
                memberDto.setLogin_id(rs.getString("login_id"));
                memberDto.setName(rs.getString("name"));
                memberDto.setPhone(rs.getString("phone"));
                memberDto.setMember_type(rs.getString("Member_type"));
                memberDto.setDonation_date(rs.getString("donation_date"));
            }
        } catch (Exception e) {
            System.out.println("개별 조회 오류: " + e.getMessage());
        } return memberDto; // 조회 결과가 없으면 null 반환
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

    // 헌혈 이력 정보 수정
    public boolean dUpdate(MemberDto memberDto) {
        try {
            String sql = "UPDATE donation_history dh " +
                        "JOIN member m ON dh.member_id = m.member_id " +
                        "SET dh.donation_date = ? WHERE m.login_id = ?";       
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, memberDto.getDonation_date());
            ps.setString(2, memberDto.getLogin_id());
            
            int result = ps.executeUpdate();
            ps.close(); 
            if (result >= 1) return true;
        } catch (SQLException e) {
            System.out.println("헌혈 이력 수정 오류: " + e.getMessage());
        }
        return false;
    }

    // 헌혈 이력 정보만 삭제
    public boolean ddelete(String login_id) {
        try {
            String sql = "DELETE dh FROM donation_history dh " +
                        "JOIN member m ON dh.member_id = m.member_id " +
                        "WHERE m.login_id = ?"; 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login_id);
            
            int result = ps.executeUpdate();
            ps.close(); 
            if (result >= 1) return true;
        } catch (SQLException e) {
            System.out.println("헌혈 이력 삭제 오류: " + e.getMessage());
        }
        return false;
    }


        // 회원정보삭제(탈퇴)
    public boolean mdelete(String login_id, String password) {
        PreparedStatement checkPs = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 비밀번호 검증
            String checkSql = "SELECT password FROM member WHERE login_id = ?";
            checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, login_id);
            rs = checkPs.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (!dbPassword.equals(password)) {
                    return false;
                }
            } else {
                return false; // 존재하지 않는 회원
            }

            String sql = "DELETE dh, m FROM member m " +
                        "LEFT JOIN donation_history dh ON m.member_id = dh.member_id " +
                        "WHERE m.login_id = ?";
                        
            ps = conn.prepareStatement(sql);
            ps.setString(1, login_id);

            int result = ps.executeUpdate();
            // m(member)은 1건 삭제되고, dh(donation_history)는 0건 이상 삭제되므로
            // result(총 삭제된 행 수)가 1 이상이면 회원 탈퇴 성공
            return result >= 1;

        } catch (SQLException e) {
            System.out.println("회원 탈퇴 처리 중 오류: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (checkPs != null) checkPs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                System.out.println("자원 해제 오류: " + e.getMessage());
            }
        }
        return false;
    }
    
}//ce
