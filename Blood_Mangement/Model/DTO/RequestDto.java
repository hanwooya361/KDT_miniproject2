package Blood_Mangement.Model.DTO;
import java.time.LocalDate;
public class RequestDto {
    
    private String request_type;
    private String member_name;
    private String patient_name;
    private String hospital_name;
    private String blood_type;
    private int requested_quantity;
    private LocalDate deadline;
    private LocalDate created_at;

    public RequestDto() {}
    public RequestDto( String request_type, String member_name, String patient_name, String hospital_name, String blood_type, int requested_quantity, LocalDate deadline, LocalDate created_at){
        this.request_type = request_type;
        this.member_name = member_name;
        this.patient_name = patient_name;
        this.hospital_name = hospital_name;
        this.blood_type = blood_type;
        this.requested_quantity = requested_quantity;
        this.deadline = deadline;
        this.created_at = created_at;
    }
    // 게터/세터
    public String getRequest_type() {
        return request_type;
    }
    public String getMember_name() {
        return member_name;
    }
    public String getPatient_name() {
        return patient_name;
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

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }
    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
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
    
}
