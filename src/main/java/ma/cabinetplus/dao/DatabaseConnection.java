package ma.cabinetplus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton pour la gestion des connexions à la base de données
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/cabinet_db";
    private static final String USER = "cabinet";
    private static final String PASSWORD = "cabinetpass";
    private static final int CONNECTION_TIMEOUT = 5000; 

    // Empêcher l'instantiation
    private DatabaseConnection() {}


    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setNetworkTimeout(null, CONNECTION_TIMEOUT);
            return conn;
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database at " + URL, e);
        }
    }
}
