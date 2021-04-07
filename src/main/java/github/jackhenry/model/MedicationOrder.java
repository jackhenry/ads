package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MedicationOrder {
    @XmlElement
    public int id;
    @XmlElement
    public int quantity;
    @XmlElement
    public Timestamp creationDate;
    @XmlElement
    public Timestamp expirationDate;
    @XmlElement
    public int patientId;
    @XmlElement
    public int drugId;
    @XmlElement
    public int doctorId;

    public MedicationOrder(int id, int quantity, Timestamp creationDate, Timestamp expirationDate,
            int patientId, int drugId, int doctorId) {
        this.id = id;
        this.quantity = quantity;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.patientId = patientId;
        this.drugId = drugId;
        this.doctorId = doctorId;
    }

    public int getOrderId() {
        return this.id;
    }

    public void setOrderId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static MedicationOrder resultToMedOrder(ResultSet rs) throws SQLException {
        return new MedicationOrder(rs.getInt("order_id"), rs.getInt("quantity"),
                rs.getTimestamp("creation_date"), rs.getTimestamp("expiration_date"),
                rs.getInt("patient_id"), rs.getInt("drug_id"), rs.getInt("doctor_id"));
    }

}
