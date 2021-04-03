package github.jackhenry.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnection {
    private static final String ENV_NAME = "java:comp/env";
    private static final String DB_NAME = "jdbc/ads";
    private static Connection instance = null;

    private DatabaseConnection() {
    }

    public static Connection instance() throws SQLException, NamingException {
        if (instance == null) {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(ENV_NAME);
            DataSource datasource = (DataSource) envContext.lookup(DB_NAME);
            instance = datasource.getConnection();
        }

        return instance;
    }
}
