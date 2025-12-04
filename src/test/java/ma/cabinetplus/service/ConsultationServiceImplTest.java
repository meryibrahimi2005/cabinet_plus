package ma.cabinetplus.service;

import ma.cabinetplus.dao.ConsultationDAO;
import ma.cabinetplus.dao.PatientDAOImpl;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultationServiceImplTest {

    @Mock
    private ConsultationDAO consultationDAO;

    @Mock
    private PatientDAOImpl patientDAO;

    @InjectMocks
    private ConsultationServiceImpl consultationService;

    private Patient patient;
    private Consultation consultationValide;

    @BeforeEach
    void setUp() {
        patient = new Patient(
                "Alami", "Hassan", "halami", "password",
                LocalDate.of(1990, 5, 15),
                "0612345678", "halami@email.com",
                "123 Rue Fes", "DOS001"
        );
        patient.setId(1L);

        consultationValide = new Consultation(
                1L,
                patient,
                "DOS001",
                LocalDate.now(),
                200.0,
                "Consultation de routine"
        );
    }

    // ==================== Tests ajouterConsultation ====================

    @Test
    void ajouterConsultation_AvecDonneesValides_DevraitReussir() {
        // Arrange
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.ajouterConsultation(consultationValide);

        // Assert
        verify(consultationDAO, times(1)).ajouter(consultationValide);
        verify(patientDAO).trouverParId(patient.getId());
    }

    @Test
    void ajouterConsultation_AvecPatientInexistant_DevraitLeverException() {
        // Arrange
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> consultationService.ajouterConsultation(consultationValide));

        assertEquals("Patient introuvable !", exception.getMessage());
        verify(consultationDAO, never()).ajouter(any());
    }

    @Test
    void ajouterConsultation_AvecPrixNegatif_DevraitLeverException() {
        // Arrange
        Consultation consultationPrixNegatif = new Consultation(
                null, patient, "DOS001", LocalDate.now(), -100.0, "Note"
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> consultationService.ajouterConsultation(consultationPrixNegatif));

        assertEquals("Le prix de la consultation doit être positif !", exception.getMessage());
        verify(consultationDAO, never()).ajouter(any());
    }

    @Test
    void ajouterConsultation_AvecPrixZero_DevraitReussir() {
        // Arrange
        Consultation consultationPrixZero = new Consultation(
                null, patient, "DOS001", LocalDate.now(), 0.0, "Consultation gratuite"
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.ajouterConsultation(consultationPrixZero);

        // Assert
        verify(consultationDAO).ajouter(consultationPrixZero);
    }

    @Test
    void ajouterConsultation_AvecDateFuture_DevraitLeverException() {
        // Arrange
        Consultation consultationFuture = new Consultation(
                null, patient, "DOS001", LocalDate.now().plusDays(1), 200.0, "Note"
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> consultationService.ajouterConsultation(consultationFuture));

        assertEquals("La consultation ne peut pas être dans le futur !", exception.getMessage());
        verify(consultationDAO, never()).ajouter(any());
    }

    @Test
    void ajouterConsultation_AvecDatePassee_DevraitReussir() {
        // Arrange
        Consultation consultationPassee = new Consultation(
                null, patient, "DOS001", LocalDate.now().minusDays(5), 200.0, "Consultation passée"
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.ajouterConsultation(consultationPassee);

        // Assert
        verify(consultationDAO).ajouter(consultationPassee);
    }

    @Test
    void ajouterConsultation_AvecDateAujourdhui_DevraitReussir() {
        // Arrange
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.ajouterConsultation(consultationValide);

        // Assert
        verify(consultationDAO).ajouter(consultationValide);
    }

    // ==================== Tests mettreAJourConsultation ====================

    @Test
    void mettreAJourConsultation_AvecDonneesValides_DevraitReussir() {
        // Arrange
        consultationValide.setPrix(250.0);
        consultationValide.setNote("Consultation mise à jour");
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.mettreAJourConsultation(consultationValide);

        // Assert
        verify(consultationDAO, times(1)).mettreAJour(consultationValide);
        verify(patientDAO).trouverParId(patient.getId());
    }

    @Test
    void mettreAJourConsultation_AvecPatientInexistant_DevraitLeverException() {
        // Arrange
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> consultationService.mettreAJourConsultation(consultationValide));

        assertEquals("Patient introuvable !", exception.getMessage());
        verify(consultationDAO, never()).mettreAJour(any());
    }

    @Test
    void mettreAJourConsultation_AvecPrixNegatif_DevraitLeverException() {
        // Arrange
        consultationValide.setPrix(-50.0);
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> consultationService.mettreAJourConsultation(consultationValide));

        assertEquals("Le prix de la consultation doit être positif !", exception.getMessage());
        verify(consultationDAO, never()).mettreAJour(any());
    }

    @Test
    void mettreAJourConsultation_ModificationNote_DevraitReussir() {
        // Arrange
        consultationValide.setNote("Nouvelle note médicale détaillée");
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.mettreAJourConsultation(consultationValide);

        // Assert
        verify(consultationDAO).mettreAJour(consultationValide);
        assertEquals("Nouvelle note médicale détaillée", consultationValide.getNote());
    }

    // ==================== Tests trouverParId ====================

    @Test
    void trouverParId_AvecIdExistant_DevraitRetournerConsultation() {
        // Arrange
        Long consultationId = 1L;
        when(consultationDAO.trouverParId(consultationId)).thenReturn(Optional.of(consultationValide));

        // Act
        Consultation resultat = consultationService.trouverParId(consultationId);

        // Assert
        assertNotNull(resultat);
        assertEquals(consultationValide.getId(), resultat.getId());
        assertEquals(consultationValide.getPrix(), resultat.getPrix());
        assertEquals(consultationValide.getNote(), resultat.getNote());
        verify(consultationDAO).trouverParId(consultationId);
    }

    @Test
    void trouverParId_AvecIdInexistant_DevraitRetournerNull() {
        // Arrange
        Long consultationId = 999L;
        when(consultationDAO.trouverParId(consultationId)).thenReturn(Optional.empty());

        // Act
        Consultation resultat = consultationService.trouverParId(consultationId);

        // Assert
        assertNull(resultat);
        verify(consultationDAO).trouverParId(consultationId);
    }

    // ==================== Tests trouverParPatient ====================

    @Test
    void trouverParPatient_AvecPatientAyantConsultations_DevraitRetournerListe() {
        // Arrange
        Long patientId = 1L;
        Consultation consultation2 = new Consultation(
                2L, patient, "DOS001",
                LocalDate.now().minusDays(30),
                180.0, "Suivi"
        );
        List<Consultation> consultationsAttendues = Arrays.asList(consultationValide, consultation2);
        when(consultationDAO.trouverParPatient(patientId)).thenReturn(consultationsAttendues);

        // Act
        List<Consultation> resultat = consultationService.trouverParPatient(patientId);

        // Assert
        assertNotNull(resultat);
        assertEquals(2, resultat.size());
        assertEquals(consultationsAttendues, resultat);
        verify(consultationDAO).trouverParPatient(patientId);
    }

    @Test
    void trouverParPatient_AvecPatientSansConsultation_DevraitRetournerListeVide() {
        // Arrange
        Long patientId = 1L;
        when(consultationDAO.trouverParPatient(patientId)).thenReturn(Arrays.asList());

        // Act
        List<Consultation> resultat = consultationService.trouverParPatient(patientId);

        // Assert
        assertNotNull(resultat);
        assertTrue(resultat.isEmpty());
        verify(consultationDAO).trouverParPatient(patientId);
    }

    // ==================== Tests trouverTous ====================

    @Test
    void trouverTous_DevraitRetournerListeConsultations() {
        // Arrange
        Patient patient2 = new Patient(
                "Bennani", "Sara", "sbennani", "pass",
                LocalDate.of(1985, 3, 20),
                "0623456789", "sara@email.com", "Adresse", "DOS002"
        );
        patient2.setId(2L);

        Consultation consultation2 = new Consultation(
                2L, patient2, "DOS002",
                LocalDate.now().minusDays(1),
                300.0, "Urgence"
        );

        List<Consultation> consultationsAttendues = Arrays.asList(consultationValide, consultation2);
        when(consultationDAO.trouverTous()).thenReturn(consultationsAttendues);

        // Act
        List<Consultation> resultat = consultationService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertEquals(2, resultat.size());
        assertEquals(consultationsAttendues, resultat);
        verify(consultationDAO).trouverTous();
    }

    @Test
    void trouverTous_AvecAucuneConsultation_DevraitRetournerListeVide() {
        // Arrange
        when(consultationDAO.trouverTous()).thenReturn(Arrays.asList());

        // Act
        List<Consultation> resultat = consultationService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertTrue(resultat.isEmpty());
        verify(consultationDAO).trouverTous();
    }

    // ==================== Tests supprimer ====================

    @Test
    void supprimer_AvecIdValide_DevraitAppelerDAO() {
        // Arrange
        Long consultationId = 1L;

        // Act
        consultationService.supprimer(consultationId);

        // Assert
        verify(consultationDAO, times(1)).supprimer(consultationId);
    }

    @Test
    void supprimer_DevraitTransmettreIdCorrect() {
        // Arrange
        Long consultationId = 42L;

        // Act
        consultationService.supprimer(consultationId);

        // Assert
        verify(consultationDAO).supprimer(eq(42L));
    }

    // ==================== Tests de validations multiples ====================

    @Test
    void ajouterConsultation_AvecToutesLesValidations_DevraitVerifierDansOrdre() {
        // Arrange
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.ajouterConsultation(consultationValide);

        // Assert
        // Vérifie que patient existe d'abord
        verify(patientDAO).trouverParId(patient.getId());
        // Puis ajoute la consultation
        verify(consultationDAO).ajouter(consultationValide);
    }

    @Test
    void ajouterConsultation_AvecPrixLimites_DevraitAccepterPrixValides() {
        // Arrange
        Consultation consultationPrixEleve = new Consultation(
                null, patient, "DOS001", LocalDate.now(), 10000.0, "Chirurgie"
        );
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.ajouterConsultation(consultationPrixEleve);

        // Assert
        verify(consultationDAO).ajouter(consultationPrixEleve);
    }

    @Test
    void mettreAJourConsultation_AvecChangementPrix_DevraitValiderNouveauPrix() {
        // Arrange
        consultationValide.setPrix(150.0);
        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        // Act
        consultationService.mettreAJourConsultation(consultationValide);

        // Assert
        verify(consultationDAO).mettreAJour(consultationValide);
        assertEquals(150.0, consultationValide.getPrix());
    }
}