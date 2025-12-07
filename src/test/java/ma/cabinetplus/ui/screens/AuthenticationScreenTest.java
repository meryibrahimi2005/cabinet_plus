package ma.cabinetplus.ui.screens;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.cabinetplus.ui.model.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextMatchers.hasText;


@ExtendWith(ApplicationExtension.class)
class AuthenticationScreenTest {

    private Stage stage;


    //Setup pour le test Médecin
    @Start
    public void start(Stage stage) {
        this.stage = stage;
        AuthenticationScreen screen = new AuthenticationScreen(stage, UserRole.MEDECIN);
        screen.show();
    }


    //Test 1: Vérifier que les champs de connexion sont affiché
    @Test
    void devraitAfficherChampsConnexion(FxRobot robot) {
        // Assert
        TextField usernameField = robot.lookup(".text-field").queryAs(TextField.class);
        PasswordField passwordField = robot.lookup(".password-field").queryAs(PasswordField.class);

        assertNotNull(usernameField, "Le champ username doit être présent");
        assertNotNull(passwordField, "Le champ password doit être présent");
    }


    //Test 2: Vérifier le titre d'authentification Médecin
    @Test
    void devraitAfficherTitreAuthMedecin(FxRobot robot) {
        // Assert
        verifyThat("Authentification - Médecin", hasText("Authentification - Médecin"));
    }


    //Test 3: Connexion avec identifiants valides (médecin par défaut)
    @Test
    void connexionAvecIdentifiantsValides_DevraitNaviguerVersDashboard(FxRobot robot) {
        // Arrange
        TextField usernameField = robot.lookup(".text-field").queryAs(TextField.class);
        PasswordField passwordField = robot.lookup(".password-field").queryAs(PasswordField.class);

        // Act
        robot.clickOn(usernameField).write("medecin");
        robot.clickOn(passwordField).write("1234");
        robot.clickOn("Se connecter");

        // Assert (après un court délai pour la connexion à la BD)
        // Vérifier qu'on est maintenant sur le dashboard
        verifyThat("Gestion des Rendez-vous", hasText("Gestion des Rendez-vous"));
    }




    //Bouton retour vers la sélection de rôle
    @Test
    void clicSurBoutonRetour_DevraitNaviguerVersSelectionRole(FxRobot robot) {
        // Act
        robot.clickOn("← Retour");

        // Assert
        verifyThat("Cabinet Plus", hasText("Cabinet Plus"));
        verifyThat("Sélectionnez votre rôle", hasText("Sélectionnez votre rôle"));
    }
}


//Tests spécifiques pour l'authentification Patient
@ExtendWith(ApplicationExtension.class)
class AuthenticationScreenPatientTest {

    private Stage stage;

    @Start
    public void start(Stage stage) {
        this.stage = stage;
        AuthenticationScreen screen = new AuthenticationScreen(stage, UserRole.PATIENT);
        screen.show();
    }

    //Vérifier que le bouton "Créer un compte" est présent pour les patients
    @Test
    void devraitAfficherBoutonCreerCompte(FxRobot robot) {
        // Assert
        Button creerCompteButton = robot.lookup("Créer un compte").queryButton();
        assertNotNull(creerCompteButton, "Le bouton Créer un compte doit être présent");
    }

    //Clic sur "Créer un compte" navigue vers l'inscription
    @Test
    void clicSurCreerCompte_DevraitNaviguerVersInscription(FxRobot robot) {
        // Act
        robot.clickOn("Créer un compte");

        // Assert
        verifyThat("Créer un compte Patient", hasText("Créer un compte Patient"));
    }


     //Test 9: Connexion patient valide (avec le patient par défaut)
    @Test
    void connexionPatientValide_DevraitNaviguerVersDashboard(FxRobot robot) {
        // Arrange
        TextField usernameField = robot.lookup(".text-field").queryAs(TextField.class);
        PasswordField passwordField = robot.lookup(".password-field").queryAs(PasswordField.class);

        // Act
        robot.clickOn(usernameField).write("patient");
        robot.clickOn(passwordField).write("1234");
        robot.clickOn("Se connecter");

        // Assert
        verifyThat("Mon Profil", hasText("Mon Profil"));
    }
}