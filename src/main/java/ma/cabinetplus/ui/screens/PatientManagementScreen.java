package ma.cabinetplus.ui.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ma.cabinetplus.service.PatientServiceImpl;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.ui.model.UserContext;
import java.util.List;

/**
 * Écran de gestion des patients (Dashboard Médecin)
 */
public class PatientManagementScreen {

    private Stage stage;
    private PatientServiceImpl patientService;
    private TableView<Patient> patientTable;

    public PatientManagementScreen(Stage stage) {
        this.stage = stage;
        this.patientService = new PatientServiceImpl();
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.setTop(createTopBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Gestion des Patients");
        stage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #34495e;");
        topBar.setPadding(new Insets(15));
        topBar.setSpacing(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Gestion des Patients");
        title.setFont(new Font("Arial", 18));
        title.setStyle("-fx-fill: white;");

        String medecinName = UserContext.getInstance().getCurrentUser().getPrenom() + " " +
                UserContext.getInstance().getCurrentUser().getNom();
        Text welcome = new Text("Bienvenue, Dr. " + medecinName);
        welcome.setFont(new Font("Arial", 14));
        welcome.setStyle("-fx-fill: #ecf0f1;");

        HBox.setHgrow(title, javafx.scene.layout.Priority.ALWAYS);

        Button logoutButton = new Button("Déconnexion");
        logoutButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        logoutButton.setOnAction(e -> logout());

        topBar.getChildren().addAll(title, welcome, logoutButton);
        return topBar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: white;");

        HBox actionBar = createActionBar();

        patientTable = createPatientTable();
        VBox.setVgrow(patientTable, javafx.scene.layout.Priority.ALWAYS);

        loadPatients();

        mainContent.getChildren().addAll(actionBar, patientTable);
        return mainContent;
    }

    private HBox createActionBar() {
        HBox actionBar = new HBox(15);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.setPadding(new Insets(10));
        actionBar.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 0 0 1 0;");

        Button addButton = new Button("+ Ajouter Patient");
        addButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white;");
        addButton.setOnAction(e -> openAddPatientDialog());

        Button editButton = new Button("✎ Modifier");
        editButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #3498db; -fx-text-fill: white;");
        editButton.setOnAction(e -> editSelectedPatient());

        Button deleteButton = new Button("🗑 Supprimer");
        deleteButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> deleteSelectedPatient());

        Button refreshButton = new Button("↻ Rafraîchir");
        refreshButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        refreshButton.setOnAction(e -> loadPatients());

        actionBar.getChildren().addAll(addButton, editButton, deleteButton, refreshButton);
        return actionBar;
    }

    private TableView<Patient> createPatientTable() {
        TableView<Patient> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Patient, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Patient, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Patient, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Patient, String> telephoneCol = new TableColumn<>("Téléphone");
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn<Patient, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Patient, String> dossierCol = new TableColumn<>("N° Dossier");
        dossierCol.setCellValueFactory(new PropertyValueFactory<>("numeroDossier"));

        table.getColumns().addAll(nomCol, prenomCol, usernameCol, telephoneCol, emailCol, dossierCol);

        return table;
    }

    private void loadPatients() {
        List<Patient> patients = patientService.trouverTous();
        ObservableList<Patient> observablePatients = FXCollections.observableArrayList(patients);
        patientTable.setItems(observablePatients);
    }

    private void openAddPatientDialog() {
        AddEditPatientDialog dialog = new AddEditPatientDialog(stage, null, patientService);
        dialog.showAndWait();
        loadPatients();
    }

    private void editSelectedPatient() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert("Veuillez sélectionner un patient");
            return;
        }

        AddEditPatientDialog dialog = new AddEditPatientDialog(stage, selectedPatient, patientService);
        dialog.showAndWait();
        loadPatients();
    }

    private void deleteSelectedPatient() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert("Veuillez sélectionner un patient");
            return;
        }

        if (showConfirmation("Êtes-vous sûr de vouloir supprimer ce patient ?")) {
            try {
                patientService.supprimerPatient(selectedPatient.getId());
                loadPatients();
                showAlert("Patient supprimé avec succès");
            } catch (Exception ex) {
                showAlert("Erreur: " + ex.getMessage());
            }
        }
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

    private boolean showConfirmation(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.CONFIRMATION
        );
        alert.setTitle("Cabinet Plus");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().get() == javafx.scene.control.ButtonType.OK;
    }

    private void logout() {
        UserContext.getInstance().logout();
        RoleSelectionScreen roleScreen = new RoleSelectionScreen(stage);
        roleScreen.show();
    }
}
