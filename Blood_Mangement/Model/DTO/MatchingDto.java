package Blood_Mangement.Model.DTO;

public class MatchingDto {
            private int matching_id;
            private int member_id;
            private int blood_pack_id;
            private String hospital_name;
            private String blood_type;
            private String shipment_date;
            private String status;
            private String patient_name;

            public MatchingDto( ){ }
            
            public MatchingDto(int matching_id, int member_id, int blood_pack_id, String hospital_name, 
                            String blood_type, String shipment_date, String status, String patient_name) {
                this.matching_id = matching_id;
                this.member_id = member_id;
                this.blood_pack_id = blood_pack_id;
                this.hospital_name = hospital_name;
                this.blood_type = blood_type;
                this.shipment_date = shipment_date;
                this.status = status;
                this.patient_name = patient_name; }

            public int getMatching_id() {
                return matching_id;
            }

            public void setMatching_id(int matching_id) {
                this.matching_id = matching_id;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public int getBlood_pack_id() {
                return blood_pack_id;
            }

            public void setBlood_pack_id(int blood_pack_id) {
                this.blood_pack_id = blood_pack_id;
            }

            public String getHospital_name() {
                return hospital_name;
            }

            public void setHospital_name(String hospital_name) {
                this.hospital_name = hospital_name;
            }

            public String getBlood_type() {
                return blood_type;
            }

            public void setBlood_type(String blood_type) {
                this.blood_type = blood_type;
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

            public String getPatient_name() {
                return patient_name;
            }

            public void setPatient_name(String patient_name) {
                this.patient_name = patient_name;
            }

            @Override
            public String toString() {
                return "MatchingDto [matching_id=" + matching_id + ", member_id=" + member_id + ", blood_pack_id="
                        + blood_pack_id + ", hospital_name=" + hospital_name + ", blood_type=" + blood_type
                        + ", shipment_date=" + shipment_date + ", status=" + status + ", patient_name=" + patient_name
                        + "]";
            }
            
                

            }
