package github.jackhenry.dto;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateMedicationOrderDTO {
    @XmlElement
    private Timestamp creationDate;
    @XmlElement
    private Timestamp expirationDate;
    @XmlElement
    private int quantity;
    @XmlElement
    private int patientId;
    @XmlElement
    private int drugId;
    @XmlElement
    private int doctorId;

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPatientId() {
        return this.patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDrugId() {
        return this.drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public int getDoctorId() {
        return this.doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
