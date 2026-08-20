package Blood_Mangement.Model.DTO;
import java.time.LocalDate;
public class RequestDto {
    
    private String blood_type;
    private int requested_quantity;
    private LocalDate deadline;

    public RequestDto() {}
    public RequestDto( String blood_type, int requested_quantity, LocalDate deadline){
        this.blood_type = blood_type;
        this.requested_quantity = requested_quantity;
        this.deadline = deadline;
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
    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }
    public void setRequested_quantity(int requested_quantity) {
        this.requested_quantity = requested_quantity;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
