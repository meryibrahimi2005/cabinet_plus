package ma.cabinetplus.service;

import ma.cabinetplus.model.Medecin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedecinServiceTest {

    private MedecinService medecinService;

    @BeforeEach
    void setUp() {
        medecinService = new MedecinServiceImpl();
    }

    @Test
    void testAjouterMedecinEtTrouverParUsername() {
        Medecin med = new Medecin(0L, "Ali", "Khalid", "akhalid", "pass123");
        medecinService.ajouterMedecin(med);

        Medecin trouvé = medecinService.trouverParUsername("akhalid");
        assertNotNull(trouvé);
        assertEquals("Ali", trouvé.getNom());
        assertEquals("Khalid", trouvé.getPrenom());
    }

    @Test
    void testAjouterMedecinUsernameExist() {
        Medecin med1 = new Medecin(0L, "Test", "User", "dup", "pass");
        medecinService.ajouterMedecin(med1);

        Medecin med2 = new Medecin(0L, "Test2", "User2", "dup", "pass2");
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> medecinService.ajouterMedecin(med2));
        assertEquals("Username déjà utilisé !", exception.getMessage());
    }

    @Test
    void testTrouverTousMedecins() {
        List<Medecin> medecins = medecinService.trouverTous();
        assertNotNull(medecins);
    }

    @Test
    void testSupprimerMedecinInexistant() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> medecinService.supprimerMedecin(-1L));
        assertEquals("Médecin inexistant !", exception.getMessage());
    }
}
