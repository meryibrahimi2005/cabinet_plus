package ma.cabinetplus.service;

import ma.cabinetplus.dao.PatientDAOImpl;
import ma.cabinetplus.dao.RendezVousDAO;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RendezVousServiceImplTest {

    @Mock
    private RendezVousDAO rdvDAO;

    @Mock
    private PatientDAOImpl patientDAO;

    @InjectMocks
    private RendezVousServiceImpl rdvService;

    private Patient patient;
    private RendezVous rdvValide;
    private LocalDateTime dateFuture;

    @BeforeEach
    void setUp() {
        patient = new Patient(
                "Alami", "Hassan", "halami", "password",
                LocalDate.of(1990, 5, 15),
                "0612345678", "halami@email.com",
                "123 Rue Fes", "DOS001"
        );
        patient.setId(1L);

        dateFuture = LocalDateTime.now().plusDays(7);
        rdvValide = new RendezVous(
                1L,
                dateFuture,
                "Consultation générale",
                patient,
                StatutRendezVous.PREVU
        );
    }

    // ==================== Tests ajouterRendezVous ====================

    @Test
    void ajouterRendezVous_AvecDonneesValides_DevraitReussir() {
        // Arrange
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));
        when(rdvDAO.trouverParPatient(patient.getId())).thenReturn(Arrays.asList());

        // Act
        rdvService.ajouterRendezVous(rdvValide);

        // Assert
        verify(rdvDAO, times(1)).ajouter(rdvValide);
        verify(patientDAO).trouverParId(patient.getId());
        verify(rdvDAO).trouverParPatient(patient.getId());
    }

    @Test
    void ajouterRendezVous_AvecPatientInexistant_DevraitLeverException() {
        // Arrange
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rdvService.ajouterRendezVous(rdvValide));

        assertEquals("Patient introuvable !", exception.getMessage());
        verify(rdvDAO, never()).ajouter(any());
    }

    @Test
    void ajouterRendezVous_AvecDateDansLePasse_DevraitLeverException() {
        // Arrange
        LocalDateTime datePasse = LocalDateTime.now().minusDays(1);
        RendezVous rdvPasse = new RendezVous(
                null, datePasse, "Motif", patient, StatutRendezVous.PREVU
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rdvService.ajouterRendezVous(rdvPasse));

        assertEquals("Impossible d'ajouter un RDV dans le passé !", exception.getMessage());
        verify(rdvDAO, never()).ajouter(any());
    }

    @Test
    void ajouterRendezVous_AvecRdvExistantMemeMoment_DevraitLeverException() {
        // Arrange
        RendezVous rdvExistant = new RendezVous(
                2L, dateFuture, "Autre motif", patient, StatutRendezVous.PREVU
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));
        when(rdvDAO.trouverParPatient(patient.getId())).thenReturn(Arrays.asList(rdvExistant));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rdvService.ajouterRendezVous(rdvValide));

        assertEquals("Le patient a déjà un rdv à la même heure !", exception.getMessage());
        verify(rdvDAO, never()).ajouter(any());
    }

    @Test
    void ajouterRendezVous_AvecRdvExistantAutreMoment_DevraitReussir() {
        // Arrange
        LocalDateTime autreDate = dateFuture.plusHours(2);
        RendezVous rdvExistant = new RendezVous(
                2L, autreDate, "Autre motif", patient, StatutRendezVous.PREVU
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));
        when(rdvDAO.trouverParPatient(patient.getId())).thenReturn(Arrays.asList(rdvExistant));

        // Act
        rdvService.ajouterRendezVous(rdvValide);

        // Assert
        verify(rdvDAO).ajouter(rdvValide);
    }

    // ==================== Tests changerStatut ====================

    @Test
    void changerStatut_AvecRdvPrevu_DevraitReussir() {
        // Arrange
        Long rdvId = 1L;
        when(rdvDAO.trouverParId(rdvId)).thenReturn(Optional.of(rdvValide));

        // Act
        rdvService.changerStatut(rdvId, StatutRendezVous.ANNULE);

        // Assert
        verify(rdvDAO, times(1)).mettreAJourStatut(rdvId, StatutRendezVous.ANNULE);
        verify(rdvDAO).trouverParId(rdvId);
    }

    @Test
    void changerStatut_AvecRdvInexistant_DevraitLeverException() {
        // Arrange
        Long rdvId = 999L;
        when(rdvDAO.trouverParId(rdvId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rdvService.changerStatut(rdvId, StatutRendezVous.ANNULE));

        assertEquals("RDV introuvable !", exception.getMessage());
        verify(rdvDAO, never()).mettreAJourStatut(anyLong(), any());
    }

    @Test
    void changerStatut_AvecRdvTermine_DevraitLeverException() {
        // Arrange
        Long rdvId = 1L;
        RendezVous rdvTermine = new RendezVous(
                rdvId, dateFuture, "Motif", patient, StatutRendezVous.TERMINE
        );
        when(rdvDAO.trouverParId(rdvId)).thenReturn(Optional.of(rdvTermine));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> rdvService.changerStatut(rdvId, StatutRendezVous.ANNULE));

        assertEquals("Impossible de modifier un RDV terminé !", exception.getMessage());
        verify(rdvDAO, never()).mettreAJourStatut(anyLong(), any());
    }

    @Test
    void changerStatut_DePreveVersTermine_DevraitReussir() {
        // Arrange
        Long rdvId = 1L;
        when(rdvDAO.trouverParId(rdvId)).thenReturn(Optional.of(rdvValide));

        // Act
        rdvService.changerStatut(rdvId, StatutRendezVous.TERMINE);

        // Assert
        verify(rdvDAO).mettreAJourStatut(rdvId, StatutRendezVous.TERMINE);
    }

    @Test
    void changerStatut_DeAnnuleVersPrevu_DevraitReussir() {
        // Arrange
        Long rdvId = 1L;
        RendezVous rdvAnnule = new RendezVous(
                rdvId, dateFuture, "Motif", patient, StatutRendezVous.ANNULE
        );
        when(rdvDAO.trouverParId(rdvId)).thenReturn(Optional.of(rdvAnnule));

        // Act
        rdvService.changerStatut(rdvId, StatutRendezVous.PREVU);

        // Assert
        verify(rdvDAO).mettreAJourStatut(rdvId, StatutRendezVous.PREVU);
    }

    // ==================== Tests trouverParId ====================

    @Test
    void trouverParId_AvecIdExistant_DevraitRetournerRdv() {
        // Arrange
        Long rdvId = 1L;
        when(rdvDAO.trouverParId(rdvId)).thenReturn(Optional.of(rdvValide));

        // Act
        RendezVous resultat = rdvService.trouverParId(rdvId);

        // Assert
        assertNotNull(resultat);
        assertEquals(rdvValide.getId(), resultat.getId());
        assertEquals(rdvValide.getMotif(), resultat.getMotif());
        assertEquals(rdvValide.getDateHeureRendezVous(), resultat.getDateHeureRendezVous());
        verify(rdvDAO).trouverParId(rdvId);
    }

    @Test
    void trouverParId_AvecIdInexistant_DevraitRetournerNull() {
        // Arrange
        Long rdvId = 999L;
        when(rdvDAO.trouverParId(rdvId)).thenReturn(Optional.empty());

        // Act
        RendezVous resultat = rdvService.trouverParId(rdvId);

        // Assert
        assertNull(resultat);
        verify(rdvDAO).trouverParId(rdvId);
    }

    // ==================== Tests trouverParPatient ====================

    @Test
    void trouverParPatient_AvecPatientAyantRdv_DevraitRetournerListe() {
        // Arrange
        Long patientId = 1L;
        RendezVous rdv2 = new RendezVous(
                2L,
                dateFuture.plusDays(1),
                "Suivi",
                patient,
                StatutRendezVous.PREVU
        );
        List<RendezVous> rdvAttendus = Arrays.asList(rdvValide, rdv2);
        when(rdvDAO.trouverParPatient(patientId)).thenReturn(rdvAttendus);

        // Act
        List<RendezVous> resultat = rdvService.trouverParPatient(patientId);

        // Assert
        assertNotNull(resultat);
        assertEquals(2, resultat.size());
        assertEquals(rdvAttendus, resultat);
        verify(rdvDAO).trouverParPatient(patientId);
    }

    @Test
    void trouverParPatient_AvecPatientSansRdv_DevraitRetournerListeVide() {
        // Arrange
        Long patientId = 1L;
        when(rdvDAO.trouverParPatient(patientId)).thenReturn(Arrays.asList());

        // Act
        List<RendezVous> resultat = rdvService.trouverParPatient(patientId);

        // Assert
        assertNotNull(resultat);
        assertTrue(resultat.isEmpty());
        verify(rdvDAO).trouverParPatient(patientId);
    }

    // ==================== Tests trouverTous ====================

    @Test
    void trouverTous_DevraitRetournerListeRdv() {
        // Arrange
        Patient patient2 = new Patient(
                "Bennani", "Sara", "sbennani", "pass",
                LocalDate.of(1985, 3, 20),
                "0623456789", "sara@email.com", "Adresse", "DOS002"
        );
        patient2.setId(2L);

        RendezVous rdv2 = new RendezVous(
                2L,
                dateFuture.plusDays(2),
                "Contrôle",
                patient2,
                StatutRendezVous.PREVU
        );

        List<RendezVous> rdvAttendus = Arrays.asList(rdvValide, rdv2);
        when(rdvDAO.trouverTous()).thenReturn(rdvAttendus);

        // Act
        List<RendezVous> resultat = rdvService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertEquals(2, resultat.size());
        assertEquals(rdvAttendus, resultat);
        verify(rdvDAO).trouverTous();
    }

    @Test
    void trouverTous_AvecAucunRdv_DevraitRetournerListeVide() {
        // Arrange
        when(rdvDAO.trouverTous()).thenReturn(Arrays.asList());

        // Act
        List<RendezVous> resultat = rdvService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertTrue(resultat.isEmpty());
        verify(rdvDAO).trouverTous();
    }

    // ==================== Tests de scénarios complexes ====================

    @Test
    void ajouterRendezVous_AvecPlusieursRdvExistants_DevraitVerifierTous() {
        // Arrange
        LocalDateTime date1 = dateFuture.plusHours(1);
        LocalDateTime date2 = dateFuture.plusHours(3);
        RendezVous rdv1 = new RendezVous(2L, date1, "RDV1", patient, StatutRendezVous.PREVU);
        RendezVous rdv2 = new RendezVous(3L, date2, "RDV2", patient, StatutRendezVous.PREVU);

        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));
        when(rdvDAO.trouverParPatient(patient.getId())).thenReturn(Arrays.asList(rdv1, rdv2));

        // Act
        rdvService.ajouterRendezVous(rdvValide);

        // Assert
        verify(rdvDAO).ajouter(rdvValide);
    }
}