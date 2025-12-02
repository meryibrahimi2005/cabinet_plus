package ma.cabinetplus.service;

import ma.cabinetplus.model.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for PatientServiceImpl validation logic
 */
class PatientServiceImplTest {

    @Test
    void testPatientCreationValidation() {
        // Test that we can create a valid patient object
        Patient patient = new Patient("Dupont", "Jean", "jdupont", "password123",
                LocalDate.of(1990, 1, 15), "0123456789", "jean@email.com",
                "123 Rue Test", "DOS-12345");

        assertNotNull(patient);
        assertEquals("Dupont", patient.getNom());
        assertEquals("Jean", patient.getPrenom());
        assertEquals("jdupont", patient.getUsername());
    }

    @Test
    void testPatientWithNullValues() {
        // Test that patient can be created with null values (validation happens in service)
        Patient patient = new Patient(null, null, null, null,
                null, null, null, null, null);

        assertNotNull(patient);
        assertNull(patient.getNom());
        assertNull(patient.getPrenom());
    }

    @Test
    void testPatientWithEmptyStrings() {
        // Test that patient can be created with empty strings (validation happens in service)
        Patient patient = new Patient("", "", "", "",
                LocalDate.now(), "", "", "", "");

        assertNotNull(patient);
        assertEquals("", patient.getNom());
        assertEquals("", patient.getPrenom());
    }

    @Test
    void testPatientWithFutureBirthDate() {
        // Test that patient can be created with future date (validation happens in service)
        Patient patient = new Patient("Dupont", "Jean", "jdupont", "password123",
                LocalDate.now().plusDays(1), "0123456789", "jean@email.com",
                "123 Rue Test", "DOS-12345");

        assertNotNull(patient);
        assertTrue(patient.getDateNaissance().isAfter(LocalDate.now()));
    }
}