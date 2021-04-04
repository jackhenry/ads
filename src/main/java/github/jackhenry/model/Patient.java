package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Patient {
    @XmlElement
    public int id;
    @XmlElement
    public String firstname;
    @XmlElement
    public String lastname;
    @XmlElement
    public String phoneNumber;
    @XmlElement
    public Timestamp admitDate;
    @XmlElement
    public Timestamp dischargeDate;

    public Patient(int id, String firstname, String lastname, String phoneNumber,
            Timestamp admitDate, Timestamp dischargeDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.admitDate = admitDate;
        this.dischargeDate = dischargeDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public static Patient resultToEmployee(ResultSet rs) throws SQLException {
        return new Patient(rs.getInt("patient_id"), rs.getString("firstname"),
                rs.getString("lastname"), rs.getString("phone_number"),
                rs.getTimestamp("admit_date"), rs.getTimestamp("discharge_date"));
    }


}
