package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {
    @XmlElement
    public int id;
    @XmlElement
    public String firstname;
    @XmlElement
    public String lastname;
    @XmlElement
    public String employeeType;
    @XmlElement
    public int accountId;

    public Employee(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Employee(int id, String firstname, String lastname, String employeeType, int accountId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.employeeType = employeeType;
        this.accountId = accountId;
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

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public static Employee resultToEmployee(ResultSet result) throws SQLException {
        return new Employee(result.getInt("employee_id"), result.getString("firstname"),
                result.getString("lastname"), result.getString("employee_type"),
                result.getInt("account_id"));
    }
}
