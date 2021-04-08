package github.jackhenry.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import github.jackhenry.dto.AddStockDTO;
import github.jackhenry.dto.UpdateStockDTO;
import github.jackhenry.model.Stock;

public class StockAccess {
    private static StockAccess instance = null;

    private StockAccess() {

    }

    public static StockAccess instance() {
        if (instance == null) {
            instance = new StockAccess();
        }

        return instance;
    }

    public int getNumberOfStockItems() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM stock";
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

    public List<Stock> getStockItemList(String start, String end, String order, String sortKey) {
        String orderBy = sortKey.equals("id") ? "drug_id" : sortKey;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            int limit = Integer.parseInt(end) - Integer.parseInt(start);

            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM stock ORDER BY " + orderBy + " " + order + " LIMIT " + limit
                    + " OFFSET " + start;
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);

            ArrayList<Stock> stockList = new ArrayList<Stock>();
            while (resultSet.next()) {
                stockList.add(Stock.resultToStockItem(resultSet));
            }

            return stockList;
        } catch (SQLException | NamingException ex) {
            return new ArrayList<Stock>();
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public Stock getStockItemById(String id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM stock WHERE drug_id=" + id;
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Stock.resultToStockItem(resultSet);
        } catch (SQLException | NamingException ex) {
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public Stock createStockItem(AddStockDTO dto) {
        int drugId = dto.getDrugId();
        int quantity = dto.getQuantity();
        int threshold = dto.getThreshold();
        Timestamp expirationDate = dto.getExpirationDate();

        Connection connection = null;
        PreparedStatement insertStatement = null;
        try {
            // Return error if stock for drug id already exists
            Stock existingStock = getStockItemById(drugId + "");
            if (existingStock != null) {
                return null;
            }

            connection = DatabaseConnection.instance();
            String sql =
                    "INSERT INTO stock (drug_id, quantity, threshold, drug_expiration) VALUES (?, ?, ?, ?)";
            insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, drugId);
            insertStatement.setInt(2, quantity);
            insertStatement.setInt(3, threshold);
            insertStatement.setTimestamp(4, expirationDate);
            insertStatement.executeUpdate();
            System.out.println("Created drug id: " + drugId);
            return getStockItemById(drugId + "");
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, insertStatement);
        }
    }

    public Stock updateStockItem(UpdateStockDTO dto) {
        int drugId = dto.getDrugId();
        int quantity = dto.getQuantity();
        int threshold = dto.getThreshold();
        Timestamp expirationDate = dto.getExpirationDate();

        Connection connection = null;
        PreparedStatement updateStatement = null;
        try {
            connection = DatabaseConnection.instance();
            String updateSql =
                    "UPDATE stock SET quantity=?, threshold=?, drug_expiration=? WHERE drug_id=?";
            updateStatement =
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
        } finally {
            DatabaseConnection.safelyClose(connection, updateStatement);
        }
    }

    public Stock deleteStockItem(final String id) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            // Get the record being deleted
            Stock stockItem = getStockItemById(id);
            String sql = "DELETE FROM stock WHERE drug_id=" + id;
            statement.executeUpdate(sql);
            return stockItem;
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement);
        }
    }
}
