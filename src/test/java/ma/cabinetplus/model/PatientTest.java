package ma.cabinetplus.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for Patient model class
 */
class PatientTest {

    @Test
    void testPatientCreation() {
        String nom = "El Amrani";
        String prenom = "Hajar";
        String username = "helamrani";
        String password = "password123";
        LocalDate dateNaissance = LocalDate.of(1990, 1, 15);
        String telephone = "0123456789";
        String email = "hajar@email.com";
        String adresse = "123 Rue de Marrakech";
        String numeroDossier = "DOS-12345";

        Patient patient = new Patient(nom, prenom, username, password,
                dateNaissance, telephone, email, adresse, numeroDossier);

        assertEquals(nom, patient.getNom());
        assertEquals(prenom, patient.getPrenom());
        assertEquals(username, patient.getUsername());
        assertEquals(password, patient.getPassword());
        assertEquals(Role.PATIENT, patient.getRole());
        assertEquals(dateNaissance, patient.getDateNaissance());
        assertEquals(telephone, patient.getTelephone());
        assertEquals(email, patient.getEmail());
        assertEquals(adresse, patient.getAdresse());
        assertEquals(numeroDossier, patient.getNumeroDossier());
    }

    @Test
    void testPatientSetters() {
        Patient patient = new Patient("Old", "Name", "olduser", "oldpass",
                LocalDate.of(1980, 1, 1), "0000000000", "old@email.com",
                "old address", "DOS-00000");

        patient.setNom("Bennani");
        patient.setPrenom("Fatima");
        patient.setUsername("fbennani");
        patient.setPassword("newpass");
        patient.setDateNaissance(LocalDate.of(1995, 5, 20));
        patient.setTelephone("0987654321");
        patient.setEmail("fatima@email.com");
        patient.setAdresse("456 Rue Rabat");
        patient.setNumeroDossier("DOS-99999");
        patient.setId(123L);

        assertEquals("Bennani", patient.getNom());
        assertEquals("Fatima", patient.getPrenom());
        assertEquals("fbennani", patient.getUsername());
        assertEquals("newpass", patient.getPassword());
        assertEquals(LocalDate.of(1995, 5, 20), patient.getDateNaissance());
        assertEquals("0987654321", patient.getTelephone());
        assertEquals("fatima@email.com", patient.getEmail());
        assertEquals("456 Rue Rabat", patient.getAdresse());
        assertEquals("DOS-99999", patient.getNumeroDossier());
        assertEquals(123L, patient.getId());
    }

    @Test
    void testToString() {
        Patient patient = new Patient("El Amrani", "Hajar", "helamrani", "password123",
                LocalDate.of(1990, 1, 15), "0123456789", "hajar@email.com",
                "123 Rue de Marrakech", "DOS-12345");

        String result = patient.toString();

        assertTrue(result.contains("Patient"));
        assertTrue(result.contains("El Amrani"));
        assertTrue(result.contains("Hajar"));
        assertTrue(result.contains("helamrani"));
        assertTrue(result.contains("DOS-12345"));
        assertTrue(result.contains("0123456789"));
    }
}