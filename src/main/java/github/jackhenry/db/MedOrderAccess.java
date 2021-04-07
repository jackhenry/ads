package github.jackhenry.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import github.jackhenry.dto.CreateMedicationOrderDTO;
import github.jackhenry.model.MedicationOrder;

public class MedOrderAccess {
    private static MedOrderAccess instance = null;

    private MedOrderAccess() {

    }

    public static MedOrderAccess instance() {
        if (instance != null) {
            instance = new MedOrderAccess();
        }

        return instance;
    }

    public int getNumberOfMedOrders() {
        try {
            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT COUNT(*) FROM medication_order";
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

    public List<MedicationOrder> getMedOrders(String start, String end, String order,
            String sortKey) {
        String orderBy = sortKey.equals("id") ? "order_id" : sortKey;
        try {
            int limit = Integer.parseInt(end) - Integer.parseInt(start);

            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT * FROM medication_order ORDER BY " + orderBy + " " + order
                    + " LIMIT " + limit + " OFFSET " + start;
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<MedicationOrder> medOrderList = new ArrayList<MedicationOrder>();
            while (resultSet.next()) {
                medOrderList.add(MedicationOrder.resultToMedOrder(resultSet));
            }

            return medOrderList;
        } catch (SQLException | NamingException ex) {
            return new ArrayList<MedicationOrder>();
        }
    }

    public MedicationOrder getMedOrderById(String id) {
        try {
            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT * FROM medication_order WHERE order_id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return MedicationOrder.resultToMedOrder(resultSet);
        } catch (SQLException | NamingException ex) {
            return null;
        }
    }

    public MedicationOrder createMedOrder(CreateMedicationOrderDTO dto) {
        int doctorId = dto.getDoctorId();
        int patientId = dto.getPatientId();
        int drugId = dto.getDrugId();
        Timestamp expirationDate = dto.getExpirationDate();
        Timestamp creationDate = dto.getCreationDate();
        try {
            // Return error if stock for drug id already exists
            MedicationOrder existingMedOrder = getMedOrderById(orderId + "");
            if (existingMedOrder != null) {
                return null;
            }

            String sql =
                    "INSERT INTO medication_order (patient_id, doctor_id, creation_date, expiration_date) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = DatabaseConnection.instance().prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, patientId);
            insertStatement.setInt(2, doctorId);
            insertStatement.setTimestamp(3, creationDate);
            insertStatement.setTimestamp(4, expirationDate);
            insertStatement.executeUpdate();
            ResultSet keys = insertStatement.getGeneratedKeys();
            keys.next();
            int orderId = keys.getInt(1);
            System.out.println("Created order id: " + orderId);
            return getMedOrderById(orderId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Stock updateStockItem(UpdateStockDTO dto) {
        int drugId = dto.getDrugId();
        int quantity = dto.getQuantity();
        int threshold = dto.getThreshold();
        Timestamp expirationDate = dto.getExpirationDate();

        try {
            Connection connection = DatabaseConnection.instance();
            String updateSql =
                    "UPDATE stock SET quantity=?, threshold=?, drug_expiration=? WHERE drug_id=?";
            PreparedStatement updateStatement =
                    connection.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, quantity);
            updateStatement.setInt(2, threshold);
            updateStatement.setTimestamp(3, expirationDate);
            updateStatement.setInt(4, drugId);
            updateStatement.executeUpdate();

            return getStockItemById(drugId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Stock deleteStockItem(final String id) {
        try {
            Statement statement = DatabaseConnection.instance().createStatement();
            // Get the record being deleted
            Stock stockItem = getStockItemById(id);
            String sql = "DELETE FROM stock WHERE drug_id=" + id;
            statement.executeUpdate(sql);
            return stockItem;
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
