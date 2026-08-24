package Blood_Mangement.Model.DTO;

public class BloodPackDto {
    private int blood_pack_id;
    private String blood_type;
    private int donation_id;
    private String received_date;
    private String expiration_date;
    private String shipment_date;
    private String status;
    // 기본, 전체매개변수 생성자
    public BloodPackDto() {
    }
    public BloodPackDto(int blood_pack_id, String blood_type, int donation_id, String received_date,
            String expiration_date, String shipment_date, String status) {
        this.blood_pack_id = blood_pack_id;
        this.blood_type = blood_type;
        this.donation_id = donation_id;
        this.received_date = received_date;
        this.expiration_date = expiration_date;
        this.shipment_date = shipment_date;
        this.status = status;
    }
    // getter,setter,toString
    public int getBlood_pack_id() {
        return blood_pack_id;
    }
    public void setBlood_pack_id(int blood_pack_id) {
        this.blood_pack_id = blood_pack_id;
    }
    public String getBlood_type() {
        return blood_type;
    }
    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }
    public int getDonation_id() {
        return donation_id;
    }
    public void setDonation_id(int donation_id) {
        this.donation_id = donation_id;
    }
    public String getReceived_date() {
        return received_date;
    }
    public void setReceived_date(String received_date) {
        this.received_date = received_date;
    }
    public String getExpiration_date() {
        return expiration_date;
    }
    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
    public String getShipment_date() {
        return shipment_date;
    }
    public void setShipment_date(String shipment_date) {
        this.shipment_date = shipment_date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "BloodPackDto [blood_pack_id=" + blood_pack_id + ", blood_type=" + blood_type + ", donation_id="
                + donation_id + ", received_date=" + received_date + ", expiration_date=" + expiration_date
                + ", shipment_date=" + shipment_date + ", status=" + status + "]";
    }
    
}
