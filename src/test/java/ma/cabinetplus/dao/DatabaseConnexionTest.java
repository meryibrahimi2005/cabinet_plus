package ma.cabinetplus.dao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testGetConnection() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "La connexion ne doit pas être null");
            assertFalse(connection.isClosed(), "La connexion doit être ouverte");
        } catch (SQLException e) {
            fail("Impossible de se connecter à la base : " + e.getMessage());
        }
    }
}
