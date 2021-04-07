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
    public String fullname;
    @XmlElement
    public String phoneNumber;
    @XmlElement
    public Timestamp admitDate;
    @XmlElement
    public Timestamp dischargeDate;

    public Patient(int id, String firstname, String lastname, String fullname, String phoneNumber,
            Timestamp admitDate, Timestamp dischargeDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.fullname = fullname;
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

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String fullname = firstname + " " + lastname;
        return new Patient(rs.getInt("patient_id"), firstname, lastname, fullname,
                rs.getString("phone_number"), rs.getTimestamp("admit_date"),
                rs.getTimestamp("discharge_date"));
    }


}
