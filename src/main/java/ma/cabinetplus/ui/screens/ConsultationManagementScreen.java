package ma.cabinetplus.ui.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.service.ConsultationServiceImpl;
import ma.cabinetplus.ui.model.UserContext;
import java.util.List;

/**
 * Écran de gestion des consultations pour le médecin
 */
public class ConsultationManagementScreen {

    private Stage stage;
    private ConsultationServiceImpl consultationService;
    private TableView<Consultation> consultationTable;

    public ConsultationManagementScreen(Stage stage) {
        this.stage = stage;
        this.consultationService = new ConsultationServiceImpl();
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.setTop(createTopBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Gestion des Consultations");
        stage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #34495e;");
        topBar.setPadding(new Insets(15));
        topBar.setSpacing(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Gestion des Consultations");
        title.setFont(new Font("Arial", 18));
        title.setStyle("-fx-fill: white;");

        String medecinName = UserContext.getInstance().getCurrentUser().getPrenom() + " " +
                UserContext.getInstance().getCurrentUser().getNom();
        Text welcome = new Text("Dr. " + medecinName);
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
        consultationTable = createConsultationTable();
        VBox.setVgrow(consultationTable, javafx.scene.layout.Priority.ALWAYS);

        loadConsultations();

        mainContent.getChildren().addAll(actionBar, consultationTable);
        return mainContent;
    }

    private HBox createActionBar() {
        HBox actionBar = new HBox(15);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.setPadding(new Insets(10));
        actionBar.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 0 0 1 0;");

        Button viewButton = new Button("👁 Afficher");
        viewButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #3498db; -fx-text-fill: white;");
        viewButton.setOnAction(e -> viewConsultation());

        Button editButton = new Button("✎ Éditer");
        editButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #f39c12; -fx-text-fill: white;");
        editButton.setOnAction(e -> editConsultation());

        Button refreshButton = new Button("↻ Rafraîchir");
        refreshButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        refreshButton.setOnAction(e -> loadConsultations());

        actionBar.getChildren().addAll(viewButton, editButton, refreshButton);
        return actionBar;
    }

    private TableView<Consultation> createConsultationTable() {
        TableView<Consultation> table = new TableView<>();
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<Consultation, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getPatient() != null ? 
                cellData.getValue().getPatient().getNom() + " " + cellData.getValue().getPatient().getPrenom() : "-"
            )
        );

        TableColumn<Consultation, String> dossierCol = new TableColumn<>("Dossier");
        dossierCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getNumeroDossier() != null ? cellData.getValue().getNumeroDossier() : "-"
            )
        );

        TableColumn<Consultation, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getDate() != null ? 
                cellData.getValue().getDate().toString() : "-"
            )
        );

        TableColumn<Consultation, String> prixCol = new TableColumn<>("Prix");
        prixCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                String.format("%.2f €", cellData.getValue().getPrix())
            )
        );

        TableColumn<Consultation, String> noteCol = new TableColumn<>("Notes");
        noteCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getNote() != null ? cellData.getValue().getNote() : "-"
            )
        );

        @SuppressWarnings("unchecked")
        TableColumn<Consultation, ?>[] columns = new TableColumn[] {patientCol, dossierCol, dateCol, prixCol, noteCol};
        table.getColumns().addAll(columns);
        return table;
    }

    private void loadConsultations() {
        try {
            List<Consultation> consultations = consultationService.trouverTous();
            ObservableList<Consultation> data = FXCollections.observableArrayList(consultations);
            consultationTable.setItems(data);
        } catch (Exception ex) {
            showAlert("Erreur lors du chargement des consultations: " + ex.getMessage());
        }
    }

    private void viewConsultation() {
        Consultation selected = consultationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner une consultation");
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("Numéro de dossier: ").append(selected.getNumeroDossier()).append("\n");
        details.append("Date: ").append(selected.getDate()).append("\n");
        details.append("Prix: ").append(String.format("%.2f €", selected.getPrix())).append("\n");
        details.append("Notes:\n").append(selected.getNote());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la Consultation");
        alert.setHeaderText("Consultation #" + selected.getId());
        alert.setContentText(details.toString());
        alert.showAndWait();
    }

    private void editConsultation() {
        Consultation selected = consultationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner une consultation");
            return;
        }

        EditConsultationDialog dialog = new EditConsultationDialog(stage, selected, consultationService);
        dialog.showAndWait();

        // Refresh table after dialog closes
        loadConsultations();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
