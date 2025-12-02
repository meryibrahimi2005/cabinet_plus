package ma.cabinetplus.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Unit tests for RendezVous model class
 */
class RendezVousTest {

    @Test
    void testRendezVousCreation() {
        Long id = 1L;
        LocalDateTime dateHeure = LocalDateTime.of(2024, 12, 15, 14, 30);
        String motif = "Consultation générale";
        Patient patient = new Patient("El Amrani", "Hajar", "helamrani", "pass",
                LocalDate.of(1990, 1, 1), "0123456789", "hajar@email.com",
                "123 Rue Marrakech", "DOS-12345");
        StatutRendezVous statut = StatutRendezVous.PREVU;

        RendezVous rendezVous = new RendezVous(id, dateHeure, motif, patient, statut);

        assertEquals(id, rendezVous.getId());
        assertEquals(dateHeure, rendezVous.getDateHeureRendezVous());
        assertEquals(motif, rendezVous.getMotif());
        assertEquals(patient, rendezVous.getPatient());
        assertEquals(statut, rendezVous.getStatut());
    }

    @Test
    void testRendezVousSetters() {
        Patient patient = new Patient("Test", "User", "test", "pass",
                LocalDate.of(1990, 1, 1), "0000000000", "test@email.com",
                "Test Address", "DOS-00000");
        RendezVous rendezVous = new RendezVous(1L, LocalDateTime.now(), "Old motif",
                patient, StatutRendezVous.PREVU);

        rendezVous.setStatut(StatutRendezVous.TERMINE);

        assertEquals(StatutRendezVous.TERMINE, rendezVous.getStatut());
    }

    @Test
    void testToString() {
        Patient patient = new Patient("El Amrani", "Hajar", "helamrani", "pass",
                LocalDate.of(1990, 1, 1), "0123456789", "hajar@email.com",
                "123 Rue Marrakech", "DOS-12345");
        RendezVous rendezVous = new RendezVous(1L, LocalDateTime.of(2024, 12, 15, 14, 30),
                "Consultation générale", patient, StatutRendezVous.PREVU);

        String result = rendezVous.toString();

        assertTrue(result.contains("RendezVous"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Consultation générale"));
        assertTrue(result.contains("El Amrani"));
        assertTrue(result.contains("Hajar"));
        assertTrue(result.contains("PREVU"));
    }
}