package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.ui.model.UserContext;

/**
 * Dashboard pour les patients - affichage des informations personnelles
 */
public class PatientDashboardScreen {

    private Stage stage;

    public PatientDashboardScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.setTop(createTopBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Mon Profil");
        stage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #34495e;");
        topBar.setPadding(new Insets(15));
        topBar.setSpacing(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Mon Profil");
        title.setFont(new Font("Arial", 18));
        title.setStyle("-fx-fill: white;");

        HBox.setHgrow(title, javafx.scene.layout.Priority.ALWAYS);

        Button logoutButton = new Button("Déconnexion");
        logoutButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> logout());

        topBar.getChildren().addAll(title, logoutButton);
        return topBar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40));
        mainContent.setStyle("-fx-background-color: white;");
        mainContent.setAlignment(Pos.TOP_CENTER);

        Patient patient = (Patient) UserContext.getInstance().getCurrentUser();

        Text welcome = new Text("Bienvenue, " + patient.getPrenom() + " " + patient.getNom());
        welcome.setFont(new Font("Arial", 24));
        welcome.setStyle("-fx-fill: #2c3e50;");

        GridPane infoGrid = createInfoGrid(patient);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button editButton = new Button("✎ Modifier mon profil");
        editButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30 10 30; " +
                "-fx-background-color: #3498db; -fx-text-fill: white;");
        editButton.setOnAction(e -> openEditProfile(patient));

        Button viewConsultationsButton = new Button("Mes Consultations");
        viewConsultationsButton.setStyle("-fx-font-size: 14; -fx-padding: 10 30 10 30; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        viewConsultationsButton.setOnAction(e -> showAlert("Consultations - Fonctionnalité à venir"));

        buttonBox.getChildren().addAll(editButton, viewConsultationsButton);

        mainContent.getChildren().addAll(welcome, new Text(" "), infoGrid, buttonBox);
        return mainContent;
    }

    private GridPane createInfoGrid(Patient patient) {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 1; -fx-border-radius: 5;");

        addInfoRow(grid, 0, "Nom:", patient.getNom());
        addInfoRow(grid, 1, "Prénom:", patient.getPrenom());
        addInfoRow(grid, 2, "Date de naissance:", patient.getDateNaissance().toString());
        addInfoRow(grid, 3, "Numéro de dossier:", patient.getNumeroDossier());
        addInfoRow(grid, 4, "Téléphone:", patient.getTelephone());
        addInfoRow(grid, 5, "Email:", patient.getEmail());
        addInfoRow(grid, 6, "Adresse:", patient.getAdresse());
        addInfoRow(grid, 7, "Username:", patient.getUsername());

        return grid;
    }

    private void addInfoRow(GridPane grid, int row, String label, String value) {
        Label labelControl = new Label(label);
        labelControl.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");

        Label valueControl = new Label(value != null ? value : "-");
        valueControl.setStyle("-fx-font-size: 12;");

        grid.add(labelControl, 0, row);
        grid.add(valueControl, 1, row);
    }

    private void openEditProfile(Patient patient) {
        EditPatientProfileDialog editDialog = new EditPatientProfileDialog(stage, patient);
        editDialog.showAndWait();
    }

    private void showAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION
        );
        alert.setTitle("Cabinet Plus");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void logout() {
        UserContext.getInstance().logout();
        RoleSelectionScreen roleScreen = new RoleSelectionScreen(stage);
        roleScreen.show();
    }
}
