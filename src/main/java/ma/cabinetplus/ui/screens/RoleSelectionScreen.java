package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ma.cabinetplus.ui.model.UserRole;

/**
 * Écran de sélection du rôle (Médecin ou Patient)
 */
public class RoleSelectionScreen {

    private Stage stage;

    public RoleSelectionScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));
        centerBox.setStyle("-fx-background-color: white;");

        Text title = new Text("Cabinet Plus");
        title.setFont(new Font("Arial", 40));
        title.setStyle("-fx-fill: #2c3e50;");

        Text subtitle = new Text("Sélectionnez votre rôle");
        subtitle.setFont(new Font("Arial", 16));
        subtitle.setStyle("-fx-fill: #7f8c8d;");

        Button medecinButton = new Button("Je suis Médecin");
        medecinButton.setStyle("-fx-font-size: 16; -fx-padding: 15 60 15 60; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");
        medecinButton.setOnMouseEntered(e ->
                medecinButton.setStyle("-fx-font-size: 16; -fx-padding: 15 60 15 60; " +
                        "-fx-background-color: #2980b9; -fx-text-fill: white; -fx-cursor: hand;"));
        medecinButton.setOnMouseExited(e ->
                medecinButton.setStyle("-fx-font-size: 16; -fx-padding: 15 60 15 60; " +
                        "-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;"));

        Button patientButton = new Button("Je suis Patient");
        patientButton.setStyle("-fx-font-size: 16; -fx-padding: 15 60 15 60; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand;");
        patientButton.setOnMouseEntered(e ->
                patientButton.setStyle("-fx-font-size: 16; -fx-padding: 15 60 15 60; " +
                        "-fx-background-color: #229954; -fx-text-fill: white; -fx-cursor: hand;"));
        patientButton.setOnMouseExited(e ->
                patientButton.setStyle("-fx-font-size: 16; -fx-padding: 15 60 15 60; " +
                        "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand;"));

        medecinButton.setOnAction(e -> openAuthScreen(UserRole.MEDECIN));
        patientButton.setOnAction(e -> openAuthScreen(UserRole.PATIENT));

        centerBox.getChildren().addAll(title, subtitle, medecinButton, patientButton);

        root.setCenter(centerBox);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Sélection du rôle");
        stage.show();
    }

    private void openAuthScreen(UserRole role) {
        AuthenticationScreen authScreen = new AuthenticationScreen(stage, role);
        authScreen.show();
    }
}
