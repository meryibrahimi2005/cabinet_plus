package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.service.ConsultationServiceImpl;
import ma.cabinetplus.service.PatientServiceImpl;

import java.time.LocalDate;

public class ConsultationManagementScreen {

    private final ConsultationServiceImpl consultationService = new ConsultationServiceImpl();
    private final PatientServiceImpl patientService = new PatientServiceImpl();

    private TableView<Consultation> table;

    public void start(Stage stage) {

        // --- TABLE ---
        table = new TableView<>();

        TableColumn<Consultation, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Consultation, String> colPatient = new TableColumn<>("Patient");
        colPatient.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPatient().getNom() + " " + c.getValue().getPatient().getPrenom()
                )
        );

        TableColumn<Consultation, String> colNumero = new TableColumn<>("N° Dossier");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroDossier"));

        TableColumn<Consultation, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Consultation, Double> colPrix = new TableColumn<>("Prix");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Consultation, String> colNote = new TableColumn<>("Note");
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        table.getColumns().addAll(colId, colPatient, colNumero, colDate, colPrix, colNote);

        loadConsultations();


        // --- BUTTONS ---
        Button btnAdd = new Button("Ajouter");
        btnAdd.setOnAction(e -> openAddDialog(stage));

        Button btnDelete = new Button("Supprimer");
        btnDelete.setOnAction(e -> deleteConsultation());

        Button btnReload = new Button("Actualiser");
        btnReload.setOnAction(e -> loadConsultations());

        HBox buttonBar = new HBox(10, btnAdd, btnDelete, btnReload);
        buttonBar.setPadding(new Insets(10));

        VBox root = new VBox(15, table, buttonBar);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 850, 500);
        stage.setTitle("Gestion des Consultations");
        stage.setScene(scene);
        stage.show();
    }


    private void loadConsultations() {
        table.getItems().setAll(consultationService.trouverTous());
    }


    private void deleteConsultation() {
        Consultation c = table.getSelectionModel().getSelectedItem();
        if (c == null) {
            alert("Veuillez sélectionner une consultation.");
            return;
        }

        consultationService.supprimer(c.getId());
        loadConsultations();
    }


    private void openAddDialog(Stage parentStage) {
        new AddEditConsultationDialog(consultationService, patientService, this).show(parentStage);
    }

    public void refreshTable() {
        loadConsultations();
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING, msg);
        a.show();
    }
}
