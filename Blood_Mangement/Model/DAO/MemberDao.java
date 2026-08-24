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
    public ArrayList<MemberDto> mView(){
        ArrayList<MemberDto> mlist = new ArrayList<>();
        try {
            String sql = "select * from Member inner join"; // 2.1 SQL 작성한다.
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
            String sql = "select * from board";
            PreparedStatement ps = conn.prepareStatement( sql );
            ResultSet rs =  ps.executeQuery();
            
            while (rs.next() ) {
                MemberDto memberDto = new MemberDto();
                memberDto.setLogin_id(rs.getString("login_id"));
                memberDto.setName(rs.getString("name"));
                memberDto.setPhone(rs.getString("Phone"));
                memberDto.setMember_type(rs.getString("Member_type"));
                memberDto.setDonation_date(rs.getString("Donation_date"));
                minlist = memberDto;    ///////////????????
            }
        } catch (Exception e) {
            System.out.println(e);
        } return minlist;
    }

    // 회원정보수정
    public boolean mUpdate(MemberDto memberDto){
        try {
            String sql = "update board set content = ? where member_id = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, memberDto.getLogin_id());
            ps.setString(2, memberDto.getName());
            ps.setString(3, memberDto.getPhone());
            ps.setString(4, memberDto.getMember_type());
            int result = ps.executeUpdate();
            if (result == 1) {return true;}
        } catch (SQLException e) {
            System.out.println(e);
        }return false;
    }

    // 헌혈이력정보수정
    public boolean dUpdate(MemberDto memberDto){
        try {
            String sql = "update board set content = ? where member_id = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setString(1, memberDto.getDonation_date());
            ps.setString(2, memberDto.getDcreated_at());
            int result = ps.executeUpdate();
            if (result == 1) {return true;}
        } catch (SQLException e) {
            System.out.println(e);
        }return false;
    }

    // 회원정보삭제
    public boolean mdelete( int member_id ){
        try{ String sql = "delete from board where member_id = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , member_id ); // SQL 문법내 첫번째 ? 에 매개변수 값 대입 
            int result = ps.executeUpdate();
            if( result == 1 ) return true;
        }catch( SQLException e ){ System.out.println( e ); }
        return false;
    }

    // 헌혈이력정보삭제
    public boolean ddelete( int donation_id ){
        try{ String sql = "delete from board where donation_id = ?";
            PreparedStatement ps = conn.prepareStatement( sql );
            ps.setInt( 1 , donation_id ); // SQL 문법내 첫번째 ? 에 매개변수 값 대입 
            int result = ps.executeUpdate();
            if( result == 1 ) return true;
        }catch( SQLException e ){ System.out.println( e ); }
        return false;
    }

    
}//ce
