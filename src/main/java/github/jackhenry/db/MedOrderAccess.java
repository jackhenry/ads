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
import github.jackhenry.dto.CreateMedicationOrderDTO;
import github.jackhenry.dto.UpdateMedicationOrderDTO;
import github.jackhenry.model.MedicationOrder;

public class MedOrderAccess {
    private static MedOrderAccess instance = null;

    private MedOrderAccess() {

    }

    public static MedOrderAccess instance() {
        if (instance == null) {
            instance = new MedOrderAccess();
        }

        return instance;
    }

    public int getNumberOfMedOrders() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM medication_order";
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

    public List<MedicationOrder> getMedOrders(String start, String end, String order,
            String sortKey) {
        String orderBy = sortKey.equals("id") ? "order_id" : sortKey;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            int limit = Integer.parseInt(end) - Integer.parseInt(start);

            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM medication_order ORDER BY " + orderBy + " " + order
                    + " LIMIT " + limit + " OFFSET " + start;
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);

            ArrayList<MedicationOrder> medOrderList = new ArrayList<MedicationOrder>();
            while (resultSet.next()) {
                MedicationOrder medOrder = MedicationOrder.resultToMedOrder(resultSet);
                medOrderList.add(medOrder);
            }

            return medOrderList;
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return new ArrayList<MedicationOrder>();
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public MedicationOrder getMedOrderById(String id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM medication_order WHERE order_id=" + id;
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            return MedicationOrder.resultToMedOrder(resultSet);
        } catch (SQLException | NamingException ex) {
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public MedicationOrder createMedOrder(CreateMedicationOrderDTO dto) {
        int doctorId = dto.getDoctorId();
        int patientId = dto.getPatientId();
        int drugId = dto.getDrugId();
        int quantity = dto.getQuantity();
        Timestamp expirationDate = dto.getExpirationDate();
        Timestamp creationDate = dto.getCreationDate();

        Connection connection = null;
        PreparedStatement insertStatement = null;
        ResultSet keys = null;
        try {
            String sql =
                    "INSERT INTO medication_order (patient_id, doctor_id, drug_id, creation_date, expiration_date, quantity) VALUES (?, ?, ?, ?, ?, ?)";
            connection = DatabaseConnection.instance();
            insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, patientId);
            insertStatement.setInt(2, doctorId);
            insertStatement.setInt(3, drugId);
            insertStatement.setTimestamp(4, creationDate);
            insertStatement.setTimestamp(5, expirationDate);
            insertStatement.setInt(6, quantity);
            insertStatement.executeUpdate();
            keys = insertStatement.getGeneratedKeys();
            keys.next();
            int orderId = keys.getInt(1);
            System.out.println("Created order id: " + orderId);
            return getMedOrderById(orderId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, insertStatement, keys);
        }
    }

    public MedicationOrder updateMedOrder(UpdateMedicationOrderDTO dto) {
        int orderId = dto.getOrderId();
        int doctorId = dto.getDoctorId();
        int patientId = dto.getPatientId();
        int drugId = dto.getDrugId();
        int quantity = dto.getQuantity();
        Timestamp expirationDate = dto.getExpirationDate();
        Timestamp creationDate = dto.getCreationDate();

        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            connection = DatabaseConnection.instance();
            String updateSql =
                    "UPDATE medication_order SET drug_id=?, doctor_id=?, patient_id=?, quantity=?, expiration_date=?, creation_date=? WHERE drug_id=?";
            updateStatement =
                    connection.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, drugId);
            updateStatement.setInt(2, doctorId);
            updateStatement.setInt(3, patientId);
            updateStatement.setInt(4, quantity);
            updateStatement.setTimestamp(5, expirationDate);
            updateStatement.setTimestamp(6, creationDate);
            updateStatement.setInt(7, orderId);
            updateStatement.executeUpdate();

            return getMedOrderById(orderId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, updateStatement);
        }
    }

    public MedicationOrder deleteMedOrder(final String id) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            // Get the record being deleted
            MedicationOrder medOrder = getMedOrderById(id);
            System.out.println("Med order: " + medOrder.getDrugId());
            String sql = "DELETE FROM medication_order WHERE order_id=" + id;
            statement.executeUpdate(sql);
            return medOrder;
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement);
        }
    }
}
