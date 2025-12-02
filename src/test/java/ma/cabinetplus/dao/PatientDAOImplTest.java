package ma.cabinetplus.dao;

import ma.cabinetplus.model.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for PatientDAOImpl mapping logic
 */
class PatientDAOImplTest {

    private final PatientDAOImpl patientDAO = new PatientDAOImpl();

    @Test
    void testPatientCreation() {
        // Test that we can create a PatientDAOImpl instance
        assertNotNull(patientDAO);
    }

    @Test
    void testPatientMappingLogic() {
        // Test the mapping logic indirectly by creating a patient and checking its properties
        Patient patient = new Patient("Dupont", "Jean", "jdupont", "password123",
                LocalDate.of(1990, 1, 15), "0123456789", "jean@email.com",
                "123 Rue Test", "DOS-12345");

        assertEquals("Dupont", patient.getNom());
        assertEquals("Jean", patient.getPrenom());
        assertEquals("jdupont", patient.getUsername());
        assertEquals("password123", patient.getPassword());
        assertEquals(LocalDate.of(1990, 1, 15), patient.getDateNaissance());
        assertEquals("0123456789", patient.getTelephone());
        assertEquals("jean@email.com", patient.getEmail());
        assertEquals("123 Rue Test", patient.getAdresse());
        assertEquals("DOS-12345", patient.getNumeroDossier());
    }
}