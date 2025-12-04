package ma.cabinetplus.service;

import ma.cabinetplus.dao.MedecinDAO;
import ma.cabinetplus.model.Medecin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedecinServiceImplTest {

    @Mock
    private MedecinDAO medecinDAO;

    @InjectMocks
    private MedecinServiceImpl medecinService;

    private Medecin medecinValide;

    @BeforeEach
    void setUp() {
        medecinValide = new Medecin(
                1L,
                "Tahiri",
                "Ahmed",
                "atahiri",
                "securePassword"
        );
    }

    // ==================== Tests ajouterMedecin ====================

    @Test
    void ajouterMedecin_AvecUsernameUnique_DevraitReussir() {
        // Arrange
        when(medecinDAO.trouverParUsername(medecinValide.getUsername()))
                .thenReturn(Optional.empty());

        // Act
        medecinService.ajouterMedecin(medecinValide);

        // Assert
        verify(medecinDAO, times(1)).ajouter(medecinValide);
        verify(medecinDAO).trouverParUsername(medecinValide.getUsername());
    }

    @Test
    void ajouterMedecin_AvecUsernameExistant_DevraitLeverException() {
        // Arrange
        when(medecinDAO.trouverParUsername(medecinValide.getUsername()))
                .thenReturn(Optional.of(medecinValide));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> medecinService.ajouterMedecin(medecinValide));

        assertEquals("Username déjà utilisé !", exception.getMessage());
        verify(medecinDAO, never()).ajouter(any());
    }

    @Test
    void ajouterMedecin_AvecNouveauMedecin_DevraitVerifierUsername() {
        // Arrange
        Medecin nouveauMedecin = new Medecin(
                null, "Benali", "Fatima", "fbenali", "password"
        );
        when(medecinDAO.trouverParUsername("fbenali")).thenReturn(Optional.empty());

        // Act
        medecinService.ajouterMedecin(nouveauMedecin);

        // Assert
        verify(medecinDAO).trouverParUsername("fbenali");
        verify(medecinDAO).ajouter(nouveauMedecin);
    }

    // ==================== Tests supprimerMedecin ====================

    @Test
    void supprimerMedecin_AvecIdExistant_DevraitReussir() {
        // Arrange
        Long medecinId = 1L;
        when(medecinDAO.trouverParId(medecinId)).thenReturn(Optional.of(medecinValide));

        // Act
        medecinService.supprimerMedecin(medecinId);

        // Assert
        verify(medecinDAO, times(1)).supprimer(medecinId);
        verify(medecinDAO).trouverParId(medecinId);
    }

    @Test
    void supprimerMedecin_AvecIdInexistant_DevraitLeverException() {
        // Arrange
        Long medecinId = 999L;
        when(medecinDAO.trouverParId(medecinId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> medecinService.supprimerMedecin(medecinId));

        assertEquals("Médecin inexistant !", exception.getMessage());
        verify(medecinDAO, never()).supprimer(anyLong());
    }

    @Test
    void supprimerMedecin_DevraitVerifierExistenceAvantSuppression() {
        // Arrange
        Long medecinId = 5L;
        when(medecinDAO.trouverParId(medecinId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> medecinService.supprimerMedecin(medecinId));

        verify(medecinDAO).trouverParId(medecinId);
        verify(medecinDAO, never()).supprimer(medecinId);
    }

    // ==================== Tests trouverParId ====================

    @Test
    void trouverParId_AvecIdExistant_DevraitRetournerMedecin() {
        // Arrange
        Long medecinId = 1L;
        when(medecinDAO.trouverParId(medecinId)).thenReturn(Optional.of(medecinValide));

        // Act
        Medecin resultat = medecinService.trouverParId(medecinId);

        // Assert
        assertNotNull(resultat);
        assertEquals(medecinValide.getId(), resultat.getId());
        assertEquals(medecinValide.getNom(), resultat.getNom());
        assertEquals(medecinValide.getPrenom(), resultat.getPrenom());
        assertEquals(medecinValide.getUsername(), resultat.getUsername());
        verify(medecinDAO).trouverParId(medecinId);
    }

    @Test
    void trouverParId_AvecIdInexistant_DevraitRetournerNull() {
        // Arrange
        Long medecinId = 999L;
        when(medecinDAO.trouverParId(medecinId)).thenReturn(Optional.empty());

        // Act
        Medecin resultat = medecinService.trouverParId(medecinId);

        // Assert
        assertNull(resultat);
        verify(medecinDAO).trouverParId(medecinId);
    }

    // ==================== Tests trouverParUsername ====================

    @Test
    void trouverParUsername_AvecUsernameExistant_DevraitRetournerMedecin() {
        // Arrange
        String username = "atahiri";
        when(medecinDAO.trouverParUsername(username)).thenReturn(Optional.of(medecinValide));

        // Act
        Medecin resultat = medecinService.trouverParUsername(username);

        // Assert
        assertNotNull(resultat);
        assertEquals(username, resultat.getUsername());
        assertEquals("Tahiri", resultat.getNom());
        verify(medecinDAO).trouverParUsername(username);
    }

    @Test
    void trouverParUsername_AvecUsernameInexistant_DevraitRetournerNull() {
        // Arrange
        String username = "inexistant";
        when(medecinDAO.trouverParUsername(username)).thenReturn(Optional.empty());

        // Act
        Medecin resultat = medecinService.trouverParUsername(username);

        // Assert
        assertNull(resultat);
        verify(medecinDAO).trouverParUsername(username);
    }

    @Test
    void trouverParUsername_AvecUsernameVide_DevraitRetournerNull() {
        // Arrange
        String username = "";
        when(medecinDAO.trouverParUsername(username)).thenReturn(Optional.empty());

        // Act
        Medecin resultat = medecinService.trouverParUsername(username);

        // Assert
        assertNull(resultat);
        verify(medecinDAO).trouverParUsername(username);
    }

    // ==================== Tests trouverTous ====================

    @Test
    void trouverTous_DevraitRetournerListeMedecins() {
        // Arrange
        Medecin medecin2 = new Medecin(
                2L, "Alaoui", "Mohammed", "malaoui", "password"
        );
        Medecin medecin3 = new Medecin(
                3L, "Cherkaoui", "Leila", "lcherkaoui", "password"
        );

        List<Medecin> medecinsAttendus = Arrays.asList(medecinValide, medecin2, medecin3);
        when(medecinDAO.trouverTous()).thenReturn(medecinsAttendus);

        // Act
        List<Medecin> resultat = medecinService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertEquals(3, resultat.size());
        assertEquals(medecinsAttendus, resultat);
        verify(medecinDAO).trouverTous();
    }

    @Test
    void trouverTous_AvecAucunMedecin_DevraitRetournerListeVide() {
        // Arrange
        when(medecinDAO.trouverTous()).thenReturn(Arrays.asList());

        // Act
        List<Medecin> resultat = medecinService.trouverTous();

        // Assert
        assertNotNull(resultat);
        assertTrue(resultat.isEmpty());
        verify(medecinDAO).trouverTous();
    }

    @Test
    void trouverTous_DevraitAppelerDAOUneFois() {
        // Arrange
        when(medecinDAO.trouverTous()).thenReturn(Arrays.asList(medecinValide));

        // Act
        medecinService.trouverTous();

        // Assert
        verify(medecinDAO, times(1)).trouverTous();
    }

    // ==================== Tests de validations supplémentaires ====================

    @Test
    void ajouterMedecin_AvecDonneesCompletes_DevraitPersisterToutesLesInfos() {
        // Arrange
        Medecin medecinComplet = new Medecin(
                null,
                "Zahiri",
                "Karim",
                "kzahiri",
                "strongPassword123"
        );
        when(medecinDAO.trouverParUsername("kzahiri")).thenReturn(Optional.empty());

        // Act
        medecinService.ajouterMedecin(medecinComplet);

        // Assert
        verify(medecinDAO).ajouter(medecinComplet);
        assertEquals("Zahiri", medecinComplet.getNom());
        assertEquals("Karim", medecinComplet.getPrenom());
        assertEquals("kzahiri", medecinComplet.getUsername());
    }
}