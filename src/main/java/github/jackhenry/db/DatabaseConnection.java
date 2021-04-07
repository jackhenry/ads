package github.jackhenry.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnection {
    private static final String ENV_NAME = "java:comp/env";
    private static final String DB_NAME = "jdbc/ads";
    private static DataSource instance = null;

    static {
        try {
            Context envContext = (Context) new InitialContext().lookup(ENV_NAME);
            instance = (DataSource) envContext.lookup(DB_NAME);
        } catch (NamingException ne) {
            throw new ExceptionInInitializerError("Error initializing data source.");
        }
    }

    public static Connection instance() throws SQLException, NamingException {
        return instance.getConnection();
    }

    public static void safelyClose(Connection connection, Statement statement,
            ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            // do nothing
        }
        try {
            statement.close();
        } catch (Exception e) {
            // do nothing
        }
        try {
            connection.close();
        } catch (Exception e) {
            // do nothing
        }
    }

    public static void safelyClose(Connection connection, Statement statement) {
        safelyClose(connection, statement, null);
    }
}
