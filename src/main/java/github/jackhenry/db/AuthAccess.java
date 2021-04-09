package github.jackhenry.db;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import javax.naming.NamingException;
import github.jackhenry.exception.model.FailedAuthException;
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

}
