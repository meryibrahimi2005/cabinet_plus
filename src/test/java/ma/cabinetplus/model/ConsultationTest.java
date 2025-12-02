package ma.cabinetplus.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for Consultation model class
 */
class ConsultationTest {

    @Test
    void testConsultationCreation() {
        Long id = 1L;
        Patient patient = new Patient("El Amrani", "Hajar", "helamrani", "pass",
                LocalDate.of(1990, 1, 1), "0123456789", "hajar@email.com",
                "123 Rue Marrakech", "DOS-12345");
        String numeroDossier = "DOS-12345";
        LocalDate date = LocalDate.of(2024, 12, 15);
        double prix = 50.0;
        String note = "Consultation de routine";

        Consultation consultation = new Consultation(id, patient, numeroDossier, date, prix, note);

        assertEquals(id, consultation.getId());
        assertEquals(patient, consultation.getPatient());
        assertEquals(numeroDossier, consultation.getNumeroDossier());
        assertEquals(date, consultation.getDate());
        assertEquals(prix, consultation.getPrix());
        assertEquals(note, consultation.getNote());
    }

    @Test
    void testConsultationSetters() {
        Patient patient = new Patient("Test", "User", "test", "pass",
                LocalDate.of(1990, 1, 1), "0000000000", "test@email.com",
                "Test Address", "DOS-00000");
        Consultation consultation = new Consultation(1L, patient, "DOS-00000",
                LocalDate.of(2024, 1, 1), 0.0, "Old note");

        consultation.setPrix(75.50);
        consultation.setNote("Notes de consultation mises à jour");
        consultation.setStatut(StatutRendezVous.TERMINE);

        assertEquals(75.50, consultation.getPrix());
        assertEquals("Notes de consultation mises à jour", consultation.getNote());
        assertEquals(StatutRendezVous.TERMINE, consultation.getStatut());
    }

    @Test
    void testToString() {
        Patient patient = new Patient("El Amrani", "Hajar", "helamrani", "pass",
                LocalDate.of(1990, 1, 1), "0123456789", "hajar@email.com",
                "123 Rue Marrakech", "DOS-12345");
        Consultation consultation = new Consultation(1L, patient, "DOS-12345",
                LocalDate.of(2024, 12, 15), 50.0, "Consultation de routine");

        String result = consultation.toString();

        assertTrue(result.contains("Consultation"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("50.0"));
        assertTrue(result.contains("El Amrani"));
        assertTrue(result.contains("Hajar"));
        assertTrue(result.contains("DOS-12345"));
        assertTrue(result.contains("Consultation de routine"));
    }
}