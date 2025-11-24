package ma.cabinetplus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTest {

    private Patient patient;
    private Consultation consultation;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2024, 3, 10);

        patient = new Patient(
                "Sara",
                "Mouline",
                "sara.m",
                "1234",
                LocalDate.of(2000, 4, 5),
                "0611223344",
                "sara@gmail.com",
                "Rabat",
                "DOS-999"
        );

        consultation = new Consultation(
                1L,
                patient,
                "CONS-123",
                date,
                350.0,
                "Consultation générale"
        );
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1L, consultation.getId());
        assertEquals(patient, consultation.getPatient());
        assertEquals("CONS-123", consultation.getNumeroDossier());
        assertEquals(date, consultation.getDate());
        assertEquals(350.0, consultation.getPrix());
        assertEquals("Consultation générale", consultation.getNote());
    }

    @Test
    void testSetStatut() {
        consultation.setStatut(StatutRendezVous.PREVU);
        assertEquals(StatutRendezVous.PREVU, consultation.getStatut());

        consultation.setStatut(StatutRendezVous.ANNULE);
        assertEquals(StatutRendezVous.ANNULE, consultation.getStatut());

        consultation.setStatut(StatutRendezVous.TERMINE);
        assertEquals(StatutRendezVous.TERMINE, consultation.getStatut());
    }

    @Test
    void testToStringContainsInformation() {
        String str = consultation.toString();

        assertNotNull(str);
        assertTrue(str.contains("Consultation"));
        assertTrue(str.contains("CONS-123"));
        assertTrue(str.contains("Sara"));
        assertTrue(str.contains("Mouline"));
        assertTrue(str.contains("350"));
    }
}
