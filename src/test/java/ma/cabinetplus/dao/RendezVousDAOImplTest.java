package ma.cabinetplus.dao;

import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.model.StatutRendezVous;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Unit tests for RendezVousDAOImpl
 */
class RendezVousDAOImplTest {

    private final RendezVousDAOImpl rendezVousDAO = new RendezVousDAOImpl();

    @Test
    void testRendezVousDAOInstantiation() {
        // Test that we can create a RendezVousDAOImpl instance
        assertNotNull(rendezVousDAO);
    }

    @Test
    void testRendezVousCreation() {
        Patient patient = new Patient("El Amrani", "Hajar", "helamrani", "pass",
                LocalDate.of(1990, 1, 1), "0123456789", "hajar@email.com",
                "123 Rue Marrakech", "DOS-12345");

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 15, 14, 30);
        RendezVous rendezVous = new RendezVous(1L, dateTime, "Consultation générale",
                patient, StatutRendezVous.PREVU);

        assertEquals(1L, rendezVous.getId());
        assertEquals(dateTime, rendezVous.getDateHeureRendezVous());
        assertEquals("Consultation générale", rendezVous.getMotif());
        assertEquals(patient, rendezVous.getPatient());
        assertEquals(StatutRendezVous.PREVU, rendezVous.getStatut());
    }

    @Test
    void testStatutUpdate() {
        Patient patient = new Patient("Benali", "Fatima", "fbenali", "pass",
                LocalDate.of(1990, 1, 1), "0000000000", "fatima@email.com",
                "Test Address", "DOS-00000");

        RendezVous rendezVous = new RendezVous(1L, LocalDateTime.now(), "Rendez-vous test",
                patient, StatutRendezVous.PREVU);

        assertEquals(StatutRendezVous.PREVU, rendezVous.getStatut());

        rendezVous.setStatut(StatutRendezVous.TERMINE);
        assertEquals(StatutRendezVous.TERMINE, rendezVous.getStatut());
    }
}