package github.jackhenry.dto;

import java.sql.Timestamp;

public class UpdatePatientDTO extends CreatePatientDTO {
    private Timestamp admitDate;
    private Timestamp dischargeDate;

    public Timestamp getAdmitDate() {
        return this.admitDate;
    }

    public void setAdmitDate(Timestamp admitDate) {
        this.admitDate = admitDate;
    }

    public Timestamp getDischargeDate() {
        return this.dischargeDate;
    }

    public void setDischargeDate(Timestamp dischargeDate) {
        this.dischargeDate = dischargeDate;
    }


}
