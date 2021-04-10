package github.jackhenry.db;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.naming.NamingException;
import github.jackhenry.exception.model.FailedAuthException;
import github.jackhenry.model.Account;
import github.jackhenry.model.Token;

public class AuthAccess {
    public static AuthAccess instance = null;
    private final SecureRandom secureRandom = new SecureRandom();
    private final Base64.Encoder base64encoder = Base64.getUrlEncoder();

    private AuthAccess() {

    }

    public static AuthAccess instance() {
        if (instance == null) {
            instance = new AuthAccess();
        }

        return instance;
    }

    private String generateToken() {
        byte[] bytes = new byte[24];
        secureRandom.nextBytes(bytes);
        return base64encoder.encodeToString(bytes);
    }

    public Token login(String username, String password) throws FailedAuthException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.instance();
            String findAccountSql = "SELECT * FROM account WHERE username=?";
            statement = connection.prepareStatement(findAccountSql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();
            String fetchedPassword = resultSet.getString("password");
            int accountId = resultSet.getInt("account_id");
            statement.close();

            if (fetchedPassword == null || !fetchedPassword.equals(password)) {
                throw new FailedAuthException("Invalid password.");
            }

            String token = generateToken();
            String createTokenSql = "INSERT INTO token (account_id, token) VALUES (?, ?)"
                    + "\nON CONFLICT (account_id) DO UPDATE" + "\nSET token=?";
            statement = connection.prepareStatement(createTokenSql);
            statement.setInt(1, accountId);
            statement.setString(2, token);
            statement.setString(3, token);
            statement.executeUpdate();
            return new Token(token, accountId);
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public void logout(String token) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.instance();
            String sql = "DELETE FROM token WHERE token=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.executeUpdate();
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseConnection.safelyClose(connection, statement);
        }
    }

    public String getTokenRole(String token) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            String sql = "SELECT employee_type FROM" + "\n(SELECT employee_id FROM token as t"
                    + "\nLEFT JOIN account a on a.account_id = t.account_id"
                    + "\nWHERE t.token=?) as employeeinfo"
                    + "\nLEFT JOIN all_employees ON employeeinfo.employee_id=all_employees.employee_id";
            statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getString("employee_type");

        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }

    public Account getAccount(int accountId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.instance();
            String sql = "SELECT * FROM all_employees WHERE account_id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, accountId);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return Account.resultToAccount(resultSet);
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            DatabaseConnection.safelyClose(connection, statement, resultSet);
        }
    }
}
