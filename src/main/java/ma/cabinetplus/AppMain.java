package ma.cabinetplus;

import javafx.application.Application;
import javafx.stage.Stage;
import ma.cabinetplus.ui.CabinetPlusApp;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CabinetPlusApp app = new CabinetPlusApp();
        app.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
