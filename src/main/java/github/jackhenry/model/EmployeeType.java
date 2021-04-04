package github.jackhenry.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeType {
    public int employeeId;
    public int accountId;

    public EmployeeType(int employeeId, int accountId) {
        this.employeeId = employeeId;
        this.accountId = accountId;
    }

    public int getEmployee_id() {
        return employeeId;
    }

    public void setEmployee_id(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getAccount_id() {
        return accountId;
    }

    public void setAccount_id(int accountId) {
        this.accountId = accountId;
    }

    public static EmployeeType resultToEmployee(ResultSet result) throws SQLException {
        return new EmployeeType(result.getInt("employee_id"), result.getInt("account_id"));
    }
}
