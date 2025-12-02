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
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.service.ConsultationServiceImpl;
import ma.cabinetplus.ui.model.UserContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Écran pour afficher l'historique des consultations du patient
 */
public class PatientConsultationHistoryScreen {

    private Stage stage;
    private Patient patient;
    private ConsultationServiceImpl consultationService;
    private TableView<Consultation> consultationTable;

    public PatientConsultationHistoryScreen(Stage stage, Patient patient) {
        this.stage = stage;
        this.patient = patient;
        this.consultationService = new ConsultationServiceImpl();
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.setTop(createTopBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Historique des Consultations");
        stage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #34495e;");
        topBar.setPadding(new Insets(15));
        topBar.setSpacing(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Historique des Consultations");
        title.setFont(new Font("Arial", 18));
        title.setStyle("-fx-fill: white;");

        HBox.setHgrow(title, javafx.scene.layout.Priority.ALWAYS);

        Button backButton = new Button("← Retour");
        backButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBack());

        topBar.getChildren().addAll(title, backButton);
        return topBar;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: white;");

        Text patientInfo = new Text("Patient: " + patient.getNom() + " " + patient.getPrenom() + 
                " | Dossier: " + patient.getNumeroDossier());
        patientInfo.setFont(new Font("Arial", 14));
        patientInfo.setStyle("-fx-fill: #2c3e50;");

        HBox actionBar = createActionBar();
        consultationTable = createConsultationTable();
        VBox.setVgrow(consultationTable, javafx.scene.layout.Priority.ALWAYS);

        loadConsultations();

        mainContent.getChildren().addAll(patientInfo, actionBar, consultationTable);
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
        viewButton.setOnAction(e -> viewConsultationDetails());

        Button refreshButton = new Button("↻ Rafraîchir");
        refreshButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        refreshButton.setOnAction(e -> loadConsultations());

        actionBar.getChildren().addAll(viewButton, refreshButton);
        return actionBar;
    }

    private TableView<Consultation> createConsultationTable() {
        TableView<Consultation> table = new TableView<>();
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<Consultation, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getDate() != null ? cellData.getValue().getDate().toString() : "-"
            )
        );

        TableColumn<Consultation, String> prixCol = new TableColumn<>("Prix");
        prixCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                String.format("%.2f €", cellData.getValue().getPrix())
            )
        );

        TableColumn<Consultation, String> noteCol = new TableColumn<>("Résumé");
        noteCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> {
                String note = cellData.getValue().getNote();
                if (note != null && note.length() > 50) {
                    return note.substring(0, 50) + "...";
                }
                return note != null ? note : "-";
            })
        );

        @SuppressWarnings("unchecked")
        TableColumn<Consultation, ?>[] columns = new TableColumn[] {dateCol, prixCol, noteCol};
        table.getColumns().addAll(columns);
        return table;
    }

    private void loadConsultations() {
        try {
            List<Consultation> allConsultations = consultationService.trouverTous();
            // Filter consultations for this patient by dossier number
            List<Consultation> patientConsultations = allConsultations.stream()
                .filter(c -> c.getNumeroDossier().equals(patient.getNumeroDossier()))
                .collect(Collectors.toList());
            
            ObservableList<Consultation> data = FXCollections.observableArrayList(patientConsultations);
            consultationTable.setItems(data);
        } catch (Exception ex) {
            showAlert("Erreur lors du chargement: " + ex.getMessage());
        }
    }

    private void viewConsultationDetails() {
        Consultation selected = consultationTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner une consultation");
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("Date: ").append(selected.getDate()).append("\n");
        details.append("Prix: ").append(String.format("%.2f €", selected.getPrix())).append("\n");
        details.append("Résumé:\n").append(selected.getNote());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la Consultation");
        alert.setHeaderText("Consultation #" + selected.getId());
        alert.setContentText(details.toString());
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cabinet Plus");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void goBack() {
        PatientDashboardScreen dashboard = new PatientDashboardScreen(stage);
        dashboard.show();
    }
}
