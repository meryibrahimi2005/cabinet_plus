package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.service.ConsultationServiceImpl;
import ma.cabinetplus.service.PatientServiceImpl;

import java.time.LocalDate;

public class AddEditConsultationDialog {

    private final ConsultationServiceImpl consultationService;
    private final PatientServiceImpl patientService;
    private final ConsultationManagementScreen parent;

    public AddEditConsultationDialog(ConsultationServiceImpl cs, PatientServiceImpl ps, ConsultationManagementScreen screen) {
        this.consultationService = cs;
        this.patientService = ps;
        this.parent = screen;
    }

    public void show(Stage owner) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);

        // FORM FIELDS
        ComboBox<Patient> cbPatients = new ComboBox<>();
        cbPatients.getItems().addAll(patientService.trouverTous());

        TextField txtNumero = new TextField();
        TextField txtPrix = new TextField();
        DatePicker dpDate = new DatePicker(LocalDate.now());
        TextArea txtNote = new TextArea();

        Button btnSave = new Button("Enregistrer");
        btnSave.setOnAction(e -> {

            Patient p = cbPatients.getValue();
            if (p == null) {
                alert("Sélectionnez un patient.");
                return;
            }

            String num = txtNumero.getText();
            if (num.isEmpty()) {
                alert("Numéro dossier obligatoire.");
                return;
            }

            double prix = Double.parseDouble(txtPrix.getText());
            LocalDate date = dpDate.getValue();
            String note = txtNote.getText();

            Consultation c = new Consultation(
                    null,
                    p,
                    num,
                    date,
                    prix,
                    note
            );

            consultationService.ajouterConsultation(c);
            parent.refreshTable();
            dialog.close();
        });


        // LAYOUT
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(15));

        grid.add(new Label("Patient :"), 0, 0);
        grid.add(cbPatients, 1, 0);

        grid.add(new Label("N° Dossier :"), 0, 1);
        grid.add(txtNumero, 1, 1);

        grid.add(new Label("Prix :"), 0, 2);
        grid.add(txtPrix, 1, 2);

        grid.add(new Label("Date :"), 0, 3);
        grid.add(dpDate, 1, 3);

        grid.add(new Label("Note :"), 0, 4);
        grid.add(txtNote, 1, 4);

        Scene scene = new Scene(grid, 400, 350);
        dialog.setScene(scene);
        dialog.setTitle("Ajouter Consultation");
        dialog.show();
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).show();
    }
}
