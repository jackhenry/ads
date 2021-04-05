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
        try {
            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT COUNT(*) FROM patient";
            ResultSet resultSet = statement.executeQuery(sql);
            int size = 0;
            if (resultSet != null) {
                resultSet.next();
                size = resultSet.getInt("count");
            }
            return size;
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public List<Patient> getPatientList(String start, String end, String order, String sortKey) {
        String orderBy = sortKey.equals("id") ? "patient_id" : sortKey;
        try {
            int limit = Integer.parseInt(end) - Integer.parseInt(start);

            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT * FROM patient ORDER BY " + orderBy + " " + order + " LIMIT "
                    + limit + " OFFSET " + start;
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Patient> employeesList = new ArrayList<Patient>();
            while (resultSet.next()) {
                employeesList.add(Patient.resultToEmployee(resultSet));
            }

            return employeesList;
        } catch (SQLException | NamingException ex) {
            return new ArrayList<Patient>();
        }
    }

    public Patient getPatientById(String id) {
        try {
            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT * FROM patient WHERE patient_id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Patient.resultToEmployee(resultSet);
        } catch (SQLException | NamingException ex) {
            return null;
        }
    }

    public Patient createPatient(CreatePatientDTO dto) {
        String firstname = dto.getFirstname();
        String lastname = dto.getLastname();
        String phoneNumber = dto.getPhoneNumber();
        try {
            String sql = "INSERT INTO patient (firstname, lastname, phone_number) VALUES (?, ?, ?)";
            PreparedStatement insertStatement = DatabaseConnection.instance().prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, firstname);
            insertStatement.setString(2, lastname);
            insertStatement.setString(3, phoneNumber);
            insertStatement.executeUpdate();
            ResultSet keys = insertStatement.getGeneratedKeys();
            keys.next();
            int patientId = keys.getInt(1);
            return getPatientById(patientId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Patient dischargePatient(String id) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "UPDATE patient SET discharge_date='" + timestamp.toString()
                    + "' WHERE patient_id=" + id;
            System.out.println(sql);
            Statement updateStatement = DatabaseConnection.instance().createStatement();
            updateStatement.executeUpdate(sql);
            return getPatientById(id);
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Patient updatePatient(UpdatePatientDTO dto) {
        int patientId = dto.getId();
        String firstname = dto.getFirstname();
        String lastname = dto.getLastname();
        String phoneNumber = dto.getPhoneNumber();

        try {
            Connection connection = DatabaseConnection.instance();
            String updateSql =
                    "UPDATE patient SET firstname=?, lastname=?, phone_number=? WHERE patient_id=?";
            PreparedStatement updateStatement =
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
        }
    }
}
