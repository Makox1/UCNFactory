
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDBSingleton {
    private static SQLiteDBSingleton instance;
    private Connection connection;

    private static final String DATABASE_URL = "jdbc:sqlite:database.db";


    private SQLiteDBSingleton() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error initializing database connection: " + e.getMessage());
            // You can throw a custom exception here if needed.
        }
    }

    public static synchronized SQLiteDBSingleton getInstance() {
        if (instance == null) {
            instance = new SQLiteDBSingleton();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing database connection: " + e.getMessage());
                // You can throw a custom exception here if needed.
            }
        }
    }
}
