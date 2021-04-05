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
        try {
            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT COUNT(*) FROM stock";
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

    public List<Stock> getStockItemList(String start, String end, String order, String sortKey) {
        String orderBy = sortKey.equals("id") ? "drug_id" : sortKey;
        try {
            int limit = Integer.parseInt(end) - Integer.parseInt(start);

            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT * FROM stock ORDER BY " + orderBy + " " + order + " LIMIT " + limit
                    + " OFFSET " + start;
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Stock> stockList = new ArrayList<Stock>();
            while (resultSet.next()) {
                stockList.add(Stock.resultToStockItem(resultSet));
            }

            return stockList;
        } catch (SQLException | NamingException ex) {
            return new ArrayList<Stock>();
        }
    }

    public Stock getStockItemById(String id) {
        try {
            Statement statement = DatabaseConnection.instance().createStatement();
            String sql = "SELECT * FROM stock WHERE drug_id=" + id;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Stock.resultToStockItem(resultSet);
        } catch (SQLException | NamingException ex) {
            return null;
        }
    }

    public Stock createStockItem(AddStockDTO dto) {
        int drugId = dto.getDrugId();
        int quantity = dto.getQuantity();
        int threshold = dto.getThreshold();
        Timestamp expirationDate = dto.getExpirationDate();
        try {
            String sql =
                    "INSERT INTO stock (drug_id, quantity, threshold, drug_expiration) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = DatabaseConnection.instance().prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, drugId);
            insertStatement.setInt(2, quantity);
            insertStatement.setInt(3, threshold);
            insertStatement.setTimestamp(4, expirationDate);
            insertStatement.executeUpdate();
            ResultSet keys = insertStatement.getGeneratedKeys();
            keys.next();
            int createdDrugId = keys.getInt(1);
            return getStockItemById(createdDrugId + "");
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
}
