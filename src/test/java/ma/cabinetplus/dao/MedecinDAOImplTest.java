package ma.cabinetplus.dao;

import ma.cabinetplus.model.Medecin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MedecinDAOImpl
 */
class MedecinDAOImplTest {

    private final MedecinDAOImpl medecinDAO = new MedecinDAOImpl();

    @Test
    void testMedecinDAOInstantiation() {
        // Test that we can create a MedecinDAOImpl instance
        assertNotNull(medecinDAO);
    }

    @Test
    void testMedecinCreation() {
        Medecin medecin = new Medecin(1L, "Bennani", "Karim", "kbennani", "doctor123");

        assertEquals(1L, medecin.getId());
        assertEquals("Bennani", medecin.getNom());
        assertEquals("Karim", medecin.getPrenom());
        assertEquals("kbennani", medecin.getUsername());
        assertEquals("doctor123", medecin.getPassword());
    }
}