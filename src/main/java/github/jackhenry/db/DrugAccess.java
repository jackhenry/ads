package github.jackhenry.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import github.jackhenry.dto.CreateDrugDTO;
import github.jackhenry.model.Drug;

public class DrugAccess {
    private static DrugAccess instance = null;

    private DrugAccess() {

    }

    public static DrugAccess instance() {
        if (instance == null) {
            instance = new DrugAccess();
        }

        return instance;
    }

    public List<Drug> getDrugList(String start, String end, String order, String sortKey) {
        String orderBy = sortKey.equals("id") ? "drug_id" : sortKey;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            int limit = Integer.parseInt(end) - Integer.parseInt(start);

            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM drug ORDER BY " + orderBy + " " + order + " LIMIT " + limit
                    + " OFFSET " + start;
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);

            ArrayList<Drug> drugList = new ArrayList<>();
            while (resultSet.next()) {
                drugList.add(Drug.resultToDrug(resultSet));
            }

            return drugList;
        } catch (SQLException | NamingException ex) {
            return new ArrayList<Drug>();
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public int getTotalNumberOfDrugs() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) FROM drug";
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

    public Drug getDrugById(int id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            String sql = "SELECT * FROM drug WHERE drug_id=" + id;
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Drug.resultToDrug(resultSet);
        } catch (SQLException | NamingException ex) {
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public Drug createDrug(CreateDrugDTO dto) {
        String drugName = dto.getDrugName();
        String concentration = dto.getConcentration();

        Connection connection = null;
        PreparedStatement insertStatement = null;
        ResultSet generatedKey = null;
        try {
            String sql = "INSERT INTO drug (drug_name, concentration) VALUES (?, ?)";
            connection = DatabaseConnection.instance();
            insertStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, drugName);
            insertStatement.setString(2, concentration);
            insertStatement.executeUpdate();
            generatedKey = insertStatement.getGeneratedKeys();
            generatedKey.next();
            int drugId = generatedKey.getInt(1);

            return getDrugById(drugId);

        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, insertStatement, generatedKey);
        }
    }

    public Drug deleteDrug(final String id) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseConnection.instance();
            statement = connection.createStatement();
            // Get the record being deleted
            Drug drug = getDrugById(Integer.parseInt(id));
            String sql = "DELETE FROM drug WHERE drug_id=" + id;
            statement.executeUpdate(sql);
            return drug;
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement);
        }
    }
}
