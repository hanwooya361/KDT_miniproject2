package Blood_Mangement.Model.DTO;
//정우의 홈그라운드
public class MemberDto {
    private int member_id;
    private String login_id;
    private String name;
    private String phone;
    private String member_type;
    private String donation_date;

    public MemberDto(){super();}

    public MemberDto(int member_id, String login_id, String name, String phone, String member_type, String donation_date) {
        this.member_id = member_id;
        this.login_id = login_id;
        this.name = name;
        this.phone = phone;
        this.member_type = member_type;
        this.donation_date = donation_date;
    }

    public int getMember_id() {return member_id;} 
    public String getLogin_id() {return login_id;}
    public String getName() {return name;}
    public String getPhone() {return phone;}
    public String getMember_type() {return member_type;}
    public String getDonation_date() {return donation_date;}

    public void setMember_id(int member_id){
        this.member_id = member_id;}
    public void setLogin_id(String login_id) {
        this.login_id = login_id;}
    public void setName(String name) {
        this.name = name;}
    public void setPhone(String phone) {
        this.phone = phone;}
    public void setMember_type(String member_type) {
        this.member_type = member_type;}
    public void setDonation_date(String donation_date) {
        this.donation_date = donation_date;}
    
    @Override
    public String toString() {
        return "MemberDto [login_id: "+login_id+", name: "+name+", phone: "+phone+", member_type: "+member_type+", donation_date: "+donation_date;
    }
   


}

