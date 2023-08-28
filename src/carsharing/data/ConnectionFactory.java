package carsharing.data;
import java.sql.*;
import java.util.Optional;

public class ConnectionFactory {
    private final String fileName;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static String DB_URL = "jdbc:h2:./src/carsharing/db/";

    public ConnectionFactory(String fileName) {
        this.fileName = fileName;
        DB_URL += fileName;

    }

    public Connection connectToDatabase() {
        Connection conn = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 2: Open a connection
//            System.out.println("Connecting to database...");

            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (conn == null) {
            throw new RuntimeException("Connection unsuccesful!");
        } else {
            return conn;
        }
    }

    public Connection getConn() {
        Connection conn = connectToDatabase();
        return conn;
    }
}
