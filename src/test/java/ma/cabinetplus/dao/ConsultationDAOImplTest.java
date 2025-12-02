package ma.cabinetplus.dao;

import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.model.StatutRendezVous;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for ConsultationDAOImpl
 */
class ConsultationDAOImplTest {

    private final ConsultationDAOImpl consultationDAO = new ConsultationDAOImpl();

    @Test
    void testConsultationDAOInstantiation() {
        // Test that we can create a ConsultationDAOImpl instance
        assertNotNull(consultationDAO);
    }

    @Test
    void testConsultationCreation() {
        Patient patient = new Patient("El Amrani", "Hajar", "helamrani", "pass",
                LocalDate.of(1990, 1, 1), "0123456789", "hajar@email.com",
                "123 Rue Marrakech", "DOS-12345");

        LocalDate date = LocalDate.of(2024, 12, 15);
        Consultation consultation = new Consultation(1L, patient, "DOS-12345",
                date, 50.0, "Consultation de routine");

        assertEquals(1L, consultation.getId());
        assertEquals(patient, consultation.getPatient());
        assertEquals("DOS-12345", consultation.getNumeroDossier());
        assertEquals(date, consultation.getDate());
        assertEquals(50.0, consultation.getPrix());
        assertEquals("Consultation de routine", consultation.getNote());
    }

    @Test
    void testConsultationSetters() {
        Patient patient = new Patient("Benali", "Fatima", "fbenali", "pass",
                LocalDate.of(1990, 1, 1), "0000000000", "fatima@email.com",
                "Test Address", "DOS-00000");

        Consultation consultation = new Consultation(1L, patient, "DOS-00000",
                LocalDate.of(2024, 1, 1), 0.0, "Note initiale");

        consultation.setPrix(75.50);
        assertEquals(75.50, consultation.getPrix());

        consultation.setNote("Notes de consultation mises à jour");
        assertEquals("Notes de consultation mises à jour", consultation.getNote());

        consultation.setStatut(StatutRendezVous.TERMINE);
        assertEquals(StatutRendezVous.TERMINE, consultation.getStatut());
    }
}