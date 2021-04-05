package github.jackhenry.model;

import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MedicationOrder {
    @XmlElement
    private int orderId;
    @XmlElement
    private Timestamp creationDate;
    @XmlElement
    private Timestamp expirationDate;
    @XmlElement
    private int patientId;
    @XmlElement
    private int drugId;
    @XmlElement
    private int doctorId;

    public MedicationOrder(int orderId, Timestamp creationDate, Timestamp expirationDate,
            int patientId, int drugId, int doctorId) {
        this.orderId = orderId;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.patientId = patientId;
        this.drugId = drugId;
        this.doctorId = doctorId;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

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


}
