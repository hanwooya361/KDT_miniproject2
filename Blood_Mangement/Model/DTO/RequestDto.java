package Blood_Mangement.Model.DTO;
import java.time.LocalDate;
public class RequestDto {
    
    private int request_id;
    private String request_type;
    private String member_name;
    private String patient_name;
    private String hospital_name;
    private String blood_type;
    private int requested_quantity;
    private LocalDate deadline;
    private LocalDate created_at;
    private String status;



    public RequestDto() {}
    // 추가용 생성자
    public RequestDto( String request_type, String patient_name, String hospital_name, String blood_type, int requested_quantity, LocalDate deadline, LocalDate created_at){
        this.request_type = request_type;
        this.patient_name = patient_name;
        this.hospital_name = hospital_name;
        this.blood_type = blood_type;
        this.requested_quantity = requested_quantity;
        this.deadline = deadline;
        this.created_at = created_at;
    }

    // 읽기용 생성자
    public RequestDto( int request_id, String request_type, String member_name, String patient_name, String hospital_name, String blood_type, int requested_quantity, LocalDate deadline, LocalDate created_at, String status){
        this.request_id = request_id;
        this.request_type = request_type;
        this.member_name = member_name;
        this.patient_name = patient_name;
        this.hospital_name = hospital_name;
        this.blood_type = blood_type;
        this.requested_quantity = requested_quantity;
        this.deadline = deadline;
        this.created_at = created_at;
        this.status = status;
    }
    // 게터/세터
    public String getRequest_type() {
        return request_type;
    }
    public String getPatient_name() {
        return patient_name;
    }
    public String getMember_name() {
        return member_name;
    }
    public String getHospital_name() {
        return hospital_name;
    }
    public String getBlood_type() {
        return blood_type;
    }
    public int getRequested_quantity() {
        return requested_quantity;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public LocalDate getCreated_at() {
        return created_at;
    }
    public String getStatus() {
        return status;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }
    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }
    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }
    public void setRequested_quantity(int requested_quantity) {
        this.requested_quantity = requested_quantity;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
    

    @Override
    public String toString() {
        return 
                "요청 번호 : '" + request_id + '\'' +
                "요청 타입 : '" + request_type + '\'' +
                ", 회원 이름 : '" + member_name + '\'' +
                ", 환자 이름 : '" + patient_name + '\'' +
                ", 병원 이름 : '" + hospital_name + '\'' +
                ", 혈액형 : '" + blood_type + '\'' +
                ", 요청 수량 : " + requested_quantity +
                ", 기한 : " + deadline +
                ", 작성 날짜 : " + created_at +
                ", 상태 : " + status +
                '}';
    }
}
