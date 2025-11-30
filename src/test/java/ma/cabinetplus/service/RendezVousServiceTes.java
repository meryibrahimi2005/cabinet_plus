package ma.cabinetplus.service;

import ma.cabinetplus.dao.RendezVousDAO;
import ma.cabinetplus.dao.PatientDAO;
import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import ma.cabinetplus.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendezVousServiceTest {

    @Mock
    private RendezVousDAO rdvDAO;

    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private RendezVousServiceImpl service;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        patient = new Patient(
                "Martin", "Claire", "cmartin", "pass123",
                LocalDate.of(1992, 5, 10),
                "0601020304", "claire@mail.com",
                "Marseille", "DOS-2"
        );
        patient.setId(2L);
    }

    @Test
    void testAjouterRendezVousValide() {
        RendezVous rdv = new RendezVous(
                1L, LocalDate.now().plusDays(1), "10:00",
                "Consultation", patient, StatutRendezVous.PREVU
        );

        when(patientDAO.trouverParId(patient.getId())).thenReturn(patient);
        when(rdvDAO.trouverParPatient(patient.getId())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> service.ajouterRendezVous(rdv));
        verify(rdvDAO, times(1)).ajouter(rdv);
    }

    @Test
    void testAjouterRendezVousPatientInexistant() {
        RendezVous rdv = new RendezVous(
                1L, LocalDate.now().plusDays(1), "10:00",
                "Consultation", patient, StatutRendezVous.PREVU
        );

        when(patientDAO.trouverParId(patient.getId())).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.ajouterRendezVous(rdv));
        assertEquals("Patient introuvable !", ex.getMessage());
        verify(rdvDAO, never()).ajouter(any());
    }

    @Test
    void testAjouterRendezVousPasse() {
        RendezVous rdv = new RendezVous(
                1L, LocalDate.now().minusDays(1), "10:00",
                "Consultation", patient, StatutRendezVous.PREVU
        );

        when(patientDAO.trouverParId(patient.getId())).thenReturn(patient);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.ajouterRendezVous(rdv));
        assertEquals("Impossible d’ajouter un RDV dans le passé !", ex.getMessage());
        verify(rdvDAO, never()).ajouter(any());
    }

    @Test
    void testAjouterRendezVousDouble() {
        RendezVous rdv = new RendezVous(
                1L, LocalDate.now().plusDays(1), "10:00",
                "Consultation", patient, StatutRendezVous.PREVU
        );

        List<RendezVous> existants = new ArrayList<>();
        existants.add(new RendezVous(2L, LocalDate.now().plusDays(1), "10:00", "Autre", patient, StatutRendezVous.PREVU));

        when(patientDAO.trouverParId(patient.getId())).thenReturn(patient);
        when(rdvDAO.trouverParPatient(patient.getId())).thenReturn(existants);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.ajouterRendezVous(rdv));
        assertEquals("Le patient a déjà un rdv à la même heure !", ex.getMessage());
        verify(rdvDAO, never()).ajouter(any());
    }

    @Test
    void testChangerStatutValide() {
        RendezVous rdv = new RendezVous(
                1L, LocalDate.now().plusDays(1), "10:00",
                "Consultation", patient, StatutRendezVous.PREVU
        );

        when(rdvDAO.trouverParId(1L)).thenReturn(rdv);

        assertDoesNotThrow(() -> service.changerStatut(1L, StatutRendezVous.TERMINE));
        verify(rdvDAO, times(1)).mettreAJourStatut(1L, StatutRendezVous.TERMINE);
    }

    @Test
    void testChangerStatutRDVIntrouvable() {
        when(rdvDAO.trouverParId(1L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.changerStatut(1L, StatutRendezVous.TERMINE));
        assertEquals("RDV introuvable !", ex.getMessage());
    }

    @Test
    void testChangerStatutRDVTermine() {
        RendezVous rdv = new RendezVous(
                1L, LocalDate.now().plusDays(1), "10:00",
                "Consultation", patient, StatutRendezVous.TERMINE
        );

        when(rdvDAO.trouverParId(1L)).thenReturn(rdv);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.changerStatut(1L, StatutRendezVous.PREVU));
        assertEquals("Impossible de modifier un RDV terminé !", ex.getMessage());
    }

    @Test
    void testTrouverParId() {
        RendezVous rdv = new RendezVous(
                1L, LocalDate.now().plusDays(1), "10:00",
                "Consultation", patient, StatutRendezVous.PREVU
        );

        when(rdvDAO.trouverParId(1L)).thenReturn(rdv);
        assertEquals(rdv, service.trouverParId(1L));
    }

    @Test
    void testTrouverParPatient() {
        List<RendezVous> list = new ArrayList<>();
        when(rdvDAO.trouverParPatient(patient.getId())).thenReturn(list);
        assertEquals(list, service.trouverParPatient(patient.getId()));
    }

    @Test
    void testTrouverTous() {
        List<RendezVous> list = new ArrayList<>();
        when(rdvDAO.trouverTous()).thenReturn(list);
        assertEquals(list, service.trouverTous());
    }
}
