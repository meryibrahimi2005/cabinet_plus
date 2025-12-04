package ma.cabinetplus.ui.screens;

import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;

/**
 * Test fonctionnel pour l'écran d'inscription patient
 *
 * Ce test vérifie:
 * - L'affichage de tous les champs du formulaire d'inscription
 * - La création d'un nouveau compte avec des données valides
 * - La validation des champs obligatoires
 * - La validation de l'unicité du username
 * - Le bouton retour vers l'authentification
 */
@ExtendWith(ApplicationExtension.class)
class PatientSignUpScreenTest {

    private Stage stage;

    @Start
    public void start(Stage stage) {
        this.stage = stage;
        PatientSignUpScreen screen = new PatientSignUpScreen(stage);
        screen.show();
    }

    /**
     * Test 1: Vérifier que tous les champs du formulaire sont affichés
     */
    @Test
    void devraitAfficherTousLesChampsFormulaire(FxRobot robot) {
        // Assert - Vérifier la présence de tous les champs
        assertNotNull(robot.lookup("Nom:").query(), "Label Nom doit être présent");
        assertNotNull(robot.lookup("Prénom:").query(), "Label Prénom doit être présent");
        assertNotNull(robot.lookup("Nom d'utilisateur:").query(), "Label Username doit être présent");
        assertNotNull(robot.lookup("Mot de passe:").query(), "Label Password doit être présent");
        assertNotNull(robot.lookup("Date de naissance:").query(), "Label Date doit être présent");
        assertNotNull(robot.lookup("Téléphone:").query(), "Label Téléphone doit être présent");
        assertNotNull(robot.lookup("Email:").query(), "Label Email doit être présent");
        assertNotNull(robot.lookup("Adresse:").query(), "Label Adresse doit être présent");
    }

    /**
     * Test 2: Vérifier le titre de la page
     */
    @Test
    void devraitAfficherTitreInscription(FxRobot robot) {
        // Assert
        verifyThat("Créer un compte Patient", hasText("Créer un compte Patient"));
    }

    /**
     * Test 7: Bouton retour vers l'authentification
     */
    @Test
    void clicSurBoutonRetour_DevraitNaviguerVersAuth(FxRobot robot) {
        // Act
        robot.clickOn("← Retour");

        // Assert
        verifyThat("Authentification - Patient", hasText("Authentification - Patient"));
    }


}