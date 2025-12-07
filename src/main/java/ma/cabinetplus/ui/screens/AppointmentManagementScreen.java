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
import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import ma.cabinetplus.service.RendezVousServiceImpl;
import ma.cabinetplus.service.ConsultationServiceImpl;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.ui.model.UserContext;
import java.util.List;
import java.time.format.DateTimeFormatter;

/**
 * Écran de gestion des rendez-vous pour le médecin
 */
public class AppointmentManagementScreen {

    private Stage stage;
    private RendezVousServiceImpl rendezVousService;
    private ConsultationServiceImpl consultationService;
    private TableView<RendezVous> appointmentTable;

    public AppointmentManagementScreen(Stage stage) {
        this.stage = stage;
        this.rendezVousService = new RendezVousServiceImpl();
        this.consultationService = new ConsultationServiceImpl();
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.setTop(createTopBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Gestion des Rendez-vous");
        stage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #34495e;");
        topBar.setPadding(new Insets(15));
        topBar.setSpacing(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Gestion des Rendez-vous");
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
        appointmentTable = createAppointmentTable();
        VBox.setVgrow(appointmentTable, javafx.scene.layout.Priority.ALWAYS);

        loadAppointments();

        mainContent.getChildren().addAll(actionBar, appointmentTable);
        return mainContent;
    }

    private HBox createActionBar() {
        HBox actionBar = new HBox(15);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.setPadding(new Insets(10));
        actionBar.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 0 0 1 0;");

        Button confirmButton = new Button("✓ Confirmer");
        confirmButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white;");
        confirmButton.setOnAction(e -> confirmAppointment());

        Button cancelButton = new Button("✗ Annuler");
        cancelButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> cancelAppointment());

        Button completeButton = new Button("✓ Terminé");
        completeButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #3498db; -fx-text-fill: white;");
        completeButton.setOnAction(e -> completeAppointment());

        Button refreshButton = new Button("↻ Rafraîchir");
        refreshButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        refreshButton.setOnAction(e -> loadAppointments());

        Button consultationsButton = new Button("📋 Consultations");
        consultationsButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #9b59b6; -fx-text-fill: white;");
        consultationsButton.setOnAction(e -> openConsultations());

        Button statsButton = new Button("📊 Statistiques");
        statsButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #8e44ad; -fx-text-fill: white;");
        statsButton.setOnAction(e -> openStatistics());

        actionBar.getChildren().addAll(
                confirmButton, cancelButton, completeButton,
                refreshButton, consultationsButton, statsButton
        );


        return actionBar;
    }

    private TableView<RendezVous> createAppointmentTable() {
        TableView<RendezVous> table = new TableView<>();
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<RendezVous, String> patientCol = new TableColumn<>("Patient");
        patientCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getPatient() != null ? 
                cellData.getValue().getPatient().getNom() + " " + cellData.getValue().getPatient().getPrenom() : "-"
            )
        );

        TableColumn<RendezVous, String> dateTimeCol = new TableColumn<>("Date et Heure");
        dateTimeCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getDateHeureRendezVous() != null ? 
                cellData.getValue().getDateHeureRendezVous().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "-"
            )
        );

        TableColumn<RendezVous, String> motifCol = new TableColumn<>("Motif");
        motifCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getMotif() != null ? cellData.getValue().getMotif() : "-"
            )
        );

        TableColumn<RendezVous, String> statutCol = new TableColumn<>("Statut");
        statutCol.setCellValueFactory(cellData -> 
            javafx.beans.binding.Bindings.createStringBinding(() -> 
                cellData.getValue().getStatut() != null ? cellData.getValue().getStatut().toString() : "-"
            )
        );

        @SuppressWarnings("unchecked")
        TableColumn<RendezVous, ?>[] columns = new TableColumn[] {patientCol, dateTimeCol, motifCol, statutCol};
        table.getColumns().addAll(columns);
        return table;
    }

    private void loadAppointments() {
        try {
            List<RendezVous> appointments = rendezVousService.trouverTous();
            ObservableList<RendezVous> data = FXCollections.observableArrayList(appointments);
            appointmentTable.setItems(data);
        } catch (Exception ex) {
            showAlert("Erreur lors du chargement des rendez-vous: " + ex.getMessage());
        }
    }

    private void confirmAppointment() {
        RendezVous selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un rendez-vous");
            return;
        }

        try {
            if (selected.getStatut() == StatutRendezVous.PREVU) {
                rendezVousService.changerStatut(selected.getId(), StatutRendezVous.PREVU);
                loadAppointments();
                showAlert("Rendez-vous confirmé");
            } else {
                showAlert("Seuls les rendez-vous planifiés peuvent être confirmés");
            }
        } catch (Exception ex) {
            showAlert("Erreur: " + ex.getMessage());
        }
    }

    private void completeAppointment() {
        RendezVous selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un rendez-vous");
            return;
        }

        try {
            rendezVousService.changerStatut(selected.getId(), StatutRendezVous.TERMINE);
            
            // Auto-create consultation when marking RDV as complete
            Consultation consultation = new Consultation(
                (long)(System.currentTimeMillis() % 100000),
                selected.getPatient(),
                selected.getPatient().getNumeroDossier(),
                java.time.LocalDate.now(),
                0.0,  // Default price, doctor can edit later
                "Consultation créée automatiquement. Motif RDV: " + selected.getMotif()
            );
            consultationService.ajouterConsultation(consultation);
            
            loadAppointments();
            showAlert("Rendez-vous marqué comme terminé et consultation créée");
        } catch (Exception ex) {
            showAlert("Erreur: " + ex.getMessage());
        }
    }

    private void cancelAppointment() {
        RendezVous selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Veuillez sélectionner un rendez-vous");
            return;
        }

        if (showConfirmation("Êtes-vous sûr d'annuler ce rendez-vous?")) {
            try {
                rendezVousService.changerStatut(selected.getId(), StatutRendezVous.ANNULE);
                loadAppointments();
                showAlert("Rendez-vous annulé");
            } catch (Exception ex) {
                showAlert("Erreur: " + ex.getMessage());
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cabinet Plus");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    private void logout() {
        UserContext.getInstance().logout();
        RoleSelectionScreen roleScreen = new RoleSelectionScreen(stage);
        roleScreen.show();
    }

    private void openConsultations() {
        ConsultationManagementScreen consultationScreen = new ConsultationManagementScreen(stage);
        consultationScreen.show();
    }

    private void openStatistics() {
        StatisticsScreen statsScreen = new StatisticsScreen(stage);
        statsScreen.show();
    }
}
