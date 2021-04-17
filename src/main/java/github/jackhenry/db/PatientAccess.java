package github.jackhenry.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import github.jackhenry.dto.CreatePatientDTO;
import github.jackhenry.dto.UpdatePatientDTO;
import github.jackhenry.model.Patient;

public class PatientAccess {
    private static PatientAccess instance = null;

    private PatientAccess() {
    }

    public static PatientAccess instance() {
        if (instance == null) {
            instance = new PatientAccess();
        }

        return instance;
    }

    public int getTotalNumberOfPatients() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM patient";
            resultSet = statement.executeQuery(sql);
            int size = 0;
            if (resultSet != null) {
                resultSet.next();
                size = resultSet.getInt("count");
            }
            return size;
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public List<Patient> getPatientList(String start, String end, String order, String sortKey) {
        String orderBy = sortKey.equals("id") ? "patient_id" : sortKey;
        orderBy = sortKey.equals("admitDate") ? "admit_date" : orderBy;
        orderBy = sortKey.equals("dischargeDate") ? "discharge_date" : orderBy;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            int limit = Integer.parseInt(end) - Integer.parseInt(start);

            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM patient ORDER BY " + orderBy + " " + order + " LIMIT "
                    + limit + " OFFSET " + start;
            resultSet = statement.executeQuery(sql);

            ArrayList<Patient> employeesList = new ArrayList<Patient>();
            while (resultSet.next()) {
                employeesList.add(Patient.resultToEmployee(resultSet));
            }

            return employeesList;
        } catch (SQLException | NamingException ex) {
            return new ArrayList<Patient>();
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public Patient getPatientById(String id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM patient WHERE patient_id=" + id;
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Patient.resultToEmployee(resultSet);
        } catch (SQLException | NamingException ex) {
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public Patient createPatient(CreatePatientDTO dto) {
        String firstname = dto.getFirstname();
        String lastname = dto.getLastname();
        String phoneNumber = dto.getPhoneNumber();

        Connection connection = null;
        PreparedStatement insertStatement = null;
        ResultSet keys = null;
        try {
            String sql = "INSERT INTO patient (firstname, lastname, phone_number) VALUES (?, ?, ?)";
            connection = DatabaseConnection.instance();
            insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, firstname);
            insertStatement.setString(2, lastname);
            insertStatement.setString(3, phoneNumber);
            insertStatement.executeUpdate();
            keys = insertStatement.getGeneratedKeys();
            keys.next();
            int patientId = keys.getInt(1);
            return getPatientById(patientId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, insertStatement, keys);
        }
    }

    public Patient dischargePatient(String id) {
        Connection connection = null;
        Statement updateStatement = null;
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "UPDATE patient SET discharge_date='" + timestamp.toString()
                    + "' WHERE patient_id=" + id;
            connection = DatabaseConnection.instance();
            updateStatement = connection.createStatement();
            updateStatement.executeUpdate(sql);
            return getPatientById(id);
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, updateStatement);
        }
    }

    public Patient updatePatient(UpdatePatientDTO dto) {
        int patientId = dto.getId();
        String firstname = dto.getFirstname();
        String lastname = dto.getLastname();
        String phoneNumber = dto.getPhoneNumber();

        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            connection = DatabaseConnection.instance();
            String updateSql =
                    "UPDATE patient SET firstname=?, lastname=?, phone_number=? WHERE patient_id=?";
            updateStatement =
                    connection.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setString(1, firstname);
            updateStatement.setString(2, lastname);
            updateStatement.setString(3, phoneNumber);
            updateStatement.setInt(4, patientId);
            updateStatement.executeUpdate();

            return getPatientById(patientId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, updateStatement);
        }
    }
}
