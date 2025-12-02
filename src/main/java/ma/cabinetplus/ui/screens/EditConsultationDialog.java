package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.service.ConsultationServiceImpl;

/**
 * Dialog pour éditer une consultation
 */
public class EditConsultationDialog {

    private Stage dialogStage;
    private Consultation consultation;
    private ConsultationServiceImpl consultationService;
    private TextField prixField;
    private TextArea noteArea;
    private Label messageLabel;

    public EditConsultationDialog(Stage parentStage, Consultation consultation, ConsultationServiceImpl consultationService) {
        this.consultation = consultation;
        this.consultationService = consultationService;

        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Éditer Consultation");
    }

    public void showAndWait() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");

        Text title = new Text("Éditer Consultation #" + consultation.getId());
        title.setFont(new Font("Arial", 18));
        title.setStyle("-fx-fill: #2c3e50;");

        GridPane form = createForm();

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-font-size: 12; -fx-padding: 8 20 8 20; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white;");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-font-size: 12; -fx-padding: 8 20 8 20; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> dialogStage.close());

        buttonBox.getChildren().addAll(saveButton, cancelButton);

        root.getChildren().addAll(title, form, messageLabel, buttonBox);

        Scene scene = new Scene(root, 600, 500);
        dialogStage.setScene(scene);

        saveButton.setOnAction(e -> saveConsultation());

        dialogStage.showAndWait();
    }

    private GridPane createForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(15));

        // Patient info (read-only)
        Label patientLabel = new Label("Patient:");
        patientLabel.setStyle("-fx-font-weight: bold;");
        Label patientValue = new Label(consultation.getPatient().getNom() + " " + 
                consultation.getPatient().getPrenom());
        grid.add(patientLabel, 0, 0);
        grid.add(patientValue, 1, 0);

        // Dossier (read-only)
        Label dossierLabel = new Label("Dossier:");
        dossierLabel.setStyle("-fx-font-weight: bold;");
        Label dossierValue = new Label(consultation.getNumeroDossier());
        grid.add(dossierLabel, 0, 1);
        grid.add(dossierValue, 1, 1);

        // Date (read-only)
        Label dateLabel = new Label("Date:");
        dateLabel.setStyle("-fx-font-weight: bold;");
        Label dateValue = new Label(consultation.getDate().toString());
        grid.add(dateLabel, 0, 2);
        grid.add(dateValue, 1, 2);

        // Prix (editable)
        Label prixLabel = new Label("Prix (€):");
        prixLabel.setStyle("-fx-font-weight: bold;");
        prixField = new TextField();
        prixField.setText(String.format("%.2f", consultation.getPrix()));
        prixField.setPrefWidth(200);
        grid.add(prixLabel, 0, 3);
        grid.add(prixField, 1, 3);

        // Notes (editable)
        Label noteLabel = new Label("Notes:");
        noteLabel.setStyle("-fx-font-weight: bold; -fx-vertical-alignment: top;");
        noteArea = new TextArea();
        noteArea.setText(consultation.getNote());
        noteArea.setWrapText(true);
        noteArea.setPrefRowCount(6);
        noteArea.setPrefWidth(300);
        grid.add(noteLabel, 0, 4);
        grid.add(noteArea, 1, 4);

        // Message label
        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 12;");

        return grid;
    }

    private void saveConsultation() {
        try {
            // Validate and update prix
            double prix;
            try {
                prix = Double.parseDouble(prixField.getText().trim());
                if (prix < 0) {
                    messageLabel.setText("Le prix ne peut pas être négatif");
                    messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                    return;
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Prix invalide - entrez un nombre");
                messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            // Update consultation
            consultation.setPrix(prix);
            consultation.setNote(noteArea.getText().trim());

            // Save via service
            consultationService.mettreAJourConsultation(consultation);

            messageLabel.setText("Consultation mise à jour avec succès!");
            messageLabel.setStyle("-fx-text-fill: #27ae60;");

            // Close after short delay
            new Thread(() -> {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ex) {
                    // ignore
                }
                javafx.application.Platform.runLater(() -> dialogStage.close());
            }).start();

        } catch (Exception ex) {
            messageLabel.setText("Erreur: " + ex.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
    }
}
