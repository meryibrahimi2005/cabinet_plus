package ma.cabinetplus.service;
import ma.cabinetplus.dao.PatientDAO;
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
class PatientServiceImplTest {

    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patientValide;

    @BeforeEach
    void setUp() {
        patientValide = new Patient(
                "Alami",
                "Hassan",
                "halami",
                "password123",
                LocalDate.of(1990, 5, 15),
                "0612345678",
                "halami@email.com",
                "123 Rue Fes",
                "DOS001"
        );
        patientValide.setId(1L);
    }

    // ==================== Tests ajouterPatient ====================

    @Test
    void ajouterPatient_AvecDonneesValides_DevraitReussir() {
        // Arrange
        when(patientDAO.trouverParUsername(patientValide.getUsername())).thenReturn(Optional.empty());
        when(patientDAO.trouverParNumeroDossier(patientValide.getNumeroDossier())).thenReturn(Optional.empty());

        // Act
        patientService.ajouterPatient(patientValide);

        // Assert
        verify(patientDAO, times(1)).ajouter(patientValide);
        verify(patientDAO).trouverParUsername(patientValide.getUsername());
        verify(patientDAO).trouverParNumeroDossier(patientValide.getNumeroDossier());
    }

    @Test
    void ajouterPatient_AvecUsernameExistant_DevraitLeverException() {
        // Arrange
        when(patientDAO.trouverParUsername(patientValide.getUsername()))
                .thenReturn(Optional.of(patientValide));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> patientService.ajouterPatient(patientValide));

        assertEquals("Username déjà utilisé !", exception.getMessage());
        verify(patientDAO, never()).ajouter(any());
    }

    @Test
    void ajouterPatient_AvecNumeroDossierExistant_DevraitLeverException() {
        // Arrange
        when(patientDAO.trouverParUsername(patientValide.getUsername())).thenReturn(Optional.empty());
        when(patientDAO.trouverParNumeroDossier(patientValide.getNumeroDossier()))
                .thenReturn(Optional.of(patientValide));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> patientService.ajouterPatient(patientValide));

        assertEquals("Numéro de dossier déjà existant !", exception.getMessage());
        verify(patientDAO, never()).ajouter(any());
    }

    @Test
    void ajouterPatient_AvecDateNaissanceFuture_DevraitLeverException() {
        // Arrange
        Patient patientDateFuture = new Patient(
                "Test", "Test", "test", "pass",
                LocalDate.now().plusDays(1),
                "0612345678", "test@email.com", "Adresse", "DOS999"
        );
        when(patientDAO.trouverParUsername(anyString())).thenReturn(Optional.empty());
        when(patientDAO.trouverParNumeroDossier(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> patientService.ajouterPatient(patientDateFuture));

        assertEquals("La date de naissance est invalide.", exception.getMessage());
        verify(patientDAO, never()).ajouter(any());
    }

    // ==================== Tests supprimerPatient ====================

    @Test
    void supprimerPatient_AvecIdExistant_DevraitReussir() {
        // Arrange
        Long patientId = 1L;
        when(patientDAO.trouverParId(patientId)).thenReturn(Optional.of(patientValide));

        // Act
        patientService.supprimerPatient(patientId);

        // Assert
        verify(patientDAO, times(1)).supprimer(patientId);
        verify(patientDAO).trouverParId(patientId);
    }

    @Test
    void supprimerPatient_AvecIdInexistant_DevraitLeverException() {
        // Arrange
        Long patientId = 999L;
        when(patientDAO.trouverParId(patientId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> patientService.supprimerPatient(patientId));

        assertEquals("Patient introuvable", exception.getMessage());
        verify(patientDAO, never()).supprimer(anyLong());
    }

    // ==================== Tests trouverParId ====================

    @Test
    void trouverParId_AvecIdExistant_DevraitRetournerPatient() {
        // Arrange
        Long patientId = 1L;
        when(patientDAO.trouverParId(patientId)).thenReturn(Optional.of(patientValide));

        // Act
        Patient resultat = patientService.trouverParId(patientId);

        // Assert
        assertNotNull(resultat);
        assertEquals(patientValide.getId(), resultat.getId());
        assertEquals(patientValide.getNom(), resultat.getNom());
        verify(patientDAO).trouverParId(patientId);
    }

    @Test
    void trouverParId_AvecIdInexistant_DevraitRetournerNull() {
        // Arrange
        Long patientId = 999L;
        when(patientDAO.trouverParId(patientId)).thenReturn(Optional.empty());

        // Act
        Patient resultat = patientService.trouverParId(patientId);

        // Assert
        assertNull(resultat);
        verify(patientDAO).trouverParId(patientId);
    }

    // ==================== Tests trouverParUsername ====================

    @Test
    void trouverParUsername_AvecUsernameExistant_DevraitRetournerPatient() {
        // Arrange
        String username = "halami";
        when(patientDAO.trouverParUsername(username)).thenReturn(Optional.of(patientValide));

        // Act
        Patient resultat = patientService.trouverParUsername(username);

        // Assert
        assertNotNull(resultat);
        assertEquals(username, resultat.getUsername());
        verify(patientDAO).trouverParUsername(username);
    }

    @Test
    void trouverParUsername_AvecUsernameInexistant_DevraitRetournerNull() {
        // Arrange
        String username = "inexistant";
        when(patientDAO.trouverParUsername(username)).thenReturn(Optional.empty());

        // Act
        Patient resultat = patientService.trouverParUsername(username);

        // Assert
        assertNull(resultat);
        verify(patientDAO).trouverParUsername(username);
    }

    // ==================== Tests trouverParNumeroDossier ====================

    @Test
    void trouverParNumeroDossier_AvecNumeroExistant_DevraitRetournerPatient() {
        // Arrange
        String numero = "DOS001";
        when(patientDAO.trouverParNumeroDossier(numero)).thenReturn(Optional.of(patientValide));

        // Act
        Patient resultat = patientService.trouverParNumeroDossier(numero);

        // Assert
        assertNotNull(resultat);
        assertEquals(numero, resultat.getNumeroDossier());
        verify(patientDAO).trouverParNumeroDossier(numero);
    }

    @Test
    void trouverParNumeroDossier_AvecNumeroInexistant_DevraitRetournerNull() {
        // Arrange
        String numero = "DOS999";
        when(patientDAO.trouverParNumeroDossier(numero)).thenReturn(Optional.empty());

        // Act
        Patient resultat = patientService.trouverParNumeroDossier(numero);

        // Assert
        assertNull(resultat);
        verify(patientDAO).trouverParNumeroDossier(numero);
    }

    // ==================== Tests trouverTous ====================

    @Test
    void trouverTous_DevraitRetournerListePatients() {
        // Arrange
        Patient patient2 = new Patient(
                "Bennani", "Sara", "sbennani", "pass",
                LocalDate.of(1985, 3, 20),
                "0623456789", "sara@email.com", "456 Rue Rabat", "DOS002"
        );
        patient2.setId(2L);

        List<Patient> patientsAttendus = Arrays.asList(patientValide, patient2);
        when(patientDAO.trouverTous()).thenReturn(patientsAttendus);

        // Act
        List<Patient> resultat = patientService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertEquals(2, resultat.size());
        assertEquals(patientsAttendus, resultat);
        verify(patientDAO).trouverTous();
    }

    @Test
    void trouverTous_AvecAucunPatient_DevraitRetournerListeVide() {
        // Arrange
        when(patientDAO.trouverTous()).thenReturn(Arrays.asList());

        // Act
        List<Patient> resultat = patientService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertTrue(resultat.isEmpty());
        verify(patientDAO).trouverTous();
    }
}