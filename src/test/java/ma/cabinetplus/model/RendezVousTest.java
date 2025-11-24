package ma.cabinetplus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RendezVousTest {

    private RendezVous rendezVous;
    private Patient patient;
    private LocalDate date;

    @BeforeEach
    void setUp() {

        date = LocalDate.of(2024, 4, 12);

        patient = new Patient(
                "Youssef",
                "Karimi",
                "youssef.k",
                "pass123",
                LocalDate.of(1998, 7, 20),
                "0655443322",
                "youssef@gmail.com",
                "Marrakech",
                "DOS-777"
        );

        rendezVous = new RendezVous(
                15L,
                date,
                "10:30",
                "Consultation de routine",
                patient,
                StatutRendezVous.PREVU
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(15L, rendezVous.getId());
        assertEquals(date, rendezVous.getDate());
        assertEquals("10:30", rendezVous.getHeure());
        assertEquals("Consultation de routine", rendezVous.getMotif());
        assertEquals(patient, rendezVous.getPatient());
        assertEquals(StatutRendezVous.PREVU, rendezVous.getStatut());
    }

    @Test
    void testSetStatut() {
        rendezVous.setStatut(StatutRendezVous.ANNULE);
        assertEquals(StatutRendezVous.ANNULE, rendezVous.getStatut());

        rendezVous.setStatut(StatutRendezVous.TERMINE);
        assertEquals(StatutRendezVous.TERMINE, rendezVous.getStatut());
    }

    @Test
    void testToStringContainsInformation() {
        String str = rendezVous.toString();

        assertNotNull(str);
        assertTrue(str.contains("RendezVous"));
        assertTrue(str.contains("10:30"));
        assertTrue(str.contains("Consultation de routine"));
        assertTrue(str.contains("Youssef"));
        assertTrue(str.contains("Karimi"));
        assertTrue(str.contains("PREVU"));
    }
}
