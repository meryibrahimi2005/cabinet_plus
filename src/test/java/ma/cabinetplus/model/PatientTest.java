package ma.cabinetplus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient patient;
    private LocalDate dateNaissance;

    @BeforeEach
    void setUp() {
        dateNaissance = LocalDate.of(1995, 5, 18);

        patient = new Patient(
                "Ahmed",
                "Benzakour",
                "ahmed.b",
                "123456",
                dateNaissance,
                "0612345678",
                "ahmed@gmail.com",
                "Casablanca",
                "DOS-123"
        );
    }

    @Test
    void testConstructorAndGetters() {
        // Champs hérités de Personne
        assertEquals("Ahmed", patient.getNom());
        assertEquals("Benzakour", patient.getPrenom());
        assertEquals("ahmed.b", patient.getUsername());
        assertEquals("123456", patient.getPassword());
        assertEquals(Role.PATIENT, patient.getRole());

        // Champs propres à Patient
        assertEquals(dateNaissance, patient.getDateNaissance());
        assertEquals("0612345678", patient.getTelephone());
        assertEquals("ahmed@gmail.com", patient.getEmail());
        assertEquals("Casablanca", patient.getAdresse());
        assertEquals("DOS-123", patient.getNumeroDossier());
    }

    @Test
    void testIdDefaultValue() {
        // id n'est jamais défini → il doit être à null par défaut (Long object)
        assertNull(patient.getId());
    }

    @Test
    void testToStringContainsUsefulInformation() {
        String result = patient.toString();

        assertNotNull(result);
        assertTrue(result.contains("Ahmed"));
        assertTrue(result.contains("Benzakour"));
        assertTrue(result.contains("DOS-123"));
        assertTrue(result.contains("0612345678"));
    }
}
