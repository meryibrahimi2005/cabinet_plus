package ma.cabinetplus.ui.screens;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class RoleSelectionScreenTest {

    private Stage stage;

    // Cette méthode est appelée automatiquement par TestFX
    @Start
    public void start(Stage stage) {
        this.stage = stage;
        RoleSelectionScreen screen = new RoleSelectionScreen(stage);
        screen.show();
    }


    //Test: Vérifie que tous les éléments de l'interface sont présents
    @Test
    void testScreenElementsAreDisplayed(FxRobot robot) {

        assertThat(robot.lookup("Cabinet Plus").queryText()).isVisible();

        assertThat(robot.lookup("Sélectionnez votre rôle").queryText()).isVisible();

        assertThat(robot.lookup("Je suis Médecin").queryButton()).isVisible();
        assertThat(robot.lookup("Je suis Patient").queryButton()).isVisible();
    }

    //Test: Vérifie la navigation vers l'écran d'authentification Médecin
    @Test
    void testNavigationToMedecinAuth(FxRobot robot) {
        // Cliquer sur le bouton Médecin
        robot.clickOn("Je suis Médecin");

        // Vérifier que l'écran d'authentification médecin s'affiche
        assertThat(robot.lookup("Authentification - Médecin").queryText()).isVisible();
        assertThat(robot.lookup("Se connecter").queryButton()).isVisible();
    }


    // Test: Vérifie la navigation vers l'écran d'authentification Patient
    @Test
    void testNavigationToPatientAuth(FxRobot robot) {
        // Cliquer sur le bouton Patient
        robot.clickOn("Je suis Patient");

        // Vérifier que l'écran d'authentification patient s'affiche
        assertThat(robot.lookup("Authentification - Patient").queryText()).isVisible();
        assertThat(robot.lookup("Se connecter").queryButton()).isVisible();
        assertThat(robot.lookup("Créer un compte").queryButton()).isVisible();
    }


    //Test: Vérifie que le titre de la fenêtre est correct
    @Test
    void testWindowTitle(FxRobot robot) {
        assertThat(stage.getTitle()).isEqualTo("Cabinet Plus - Sélection du rôle");
    }


    //Test: Vérifie les dimensions de la fenêtre
    @Test
    void testWindowSize(FxRobot robot) {
        assertThat(stage.getWidth()).isEqualTo(800.0);
        assertThat(stage.getHeight()).isEqualTo(600.0);
    }
}