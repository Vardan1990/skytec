package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


public final class DataSource {

    private static final Logger log = Logger.getLogger(DataSource.class.getName());

    static final String JDBC_DRIVER = "org.h2.Driver";

    static final String DB_URL = "jdbc:h2:mem:skytec;OLD_INFORMATION_SCHEMA=TRUE";

    static final String USER = "sa";

    static final String PASSWORD = "password";


    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        log.info("create connection.");
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
