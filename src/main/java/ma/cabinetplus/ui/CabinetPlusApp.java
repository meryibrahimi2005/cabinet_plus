package ma.cabinetplus.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import ma.cabinetplus.ui.screens.RoleSelectionScreen;

/**
 * Point d'entrée principal pour l'application JavaFX
 */
public class CabinetPlusApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        RoleSelectionScreen roleScreen = new RoleSelectionScreen(primaryStage);
        roleScreen.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
