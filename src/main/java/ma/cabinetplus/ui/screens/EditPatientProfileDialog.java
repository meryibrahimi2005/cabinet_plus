package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.cabinetplus.model.Patient;

/**
 * Dialog pour éditer le profil du patient
 */
public class EditPatientProfileDialog {

    private Stage dialogStage;
    private Patient patient;

    public EditPatientProfileDialog(Stage parentStage, Patient patient) {
        this.patient = patient;

        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Modifier mon profil");
    }

    public void showAndWait() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");

        Text title = new Text("Modifier mes informations");
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

        root.getChildren().addAll(title, form, buttonBox);

        Scene scene = new Scene(root, 500, 450);
        dialogStage.setScene(scene);

        // Get form field references
        TextField telephoneField = (TextField) form.lookup("#telephoneField");
        TextField emailField = (TextField) form.lookup("#emailField");
        TextField adresseField = (TextField) form.lookup("#adresseField");
        Label messageLabel = (Label) form.lookup("#messageLabel");

        saveButton.setOnAction(e -> {
            String telephone = telephoneField.getText().trim();
            String email = emailField.getText().trim();
            String adresse = adresseField.getText().trim();

            if (telephone.isEmpty() || email.isEmpty() || adresse.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs");
                messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            try {
                patient.setTelephone(telephone);
                patient.setEmail(email);
                patient.setAdresse(adresse);

                messageLabel.setText("Profil mis à jour!");
                messageLabel.setStyle("-fx-text-fill: #27ae60;");

                javafx.application.Platform.runLater(() -> {
                    try {
                        Thread.sleep(1500);
                        dialogStage.close();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });

            } catch (Exception ex) {
                messageLabel.setText("Erreur: " + ex.getMessage());
                messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            }
        });

        dialogStage.showAndWait();
    }

    private GridPane createForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(15));

        TextField telephoneField = new TextField();
        telephoneField.setId("telephoneField");
        telephoneField.setPromptText("Téléphone");
        telephoneField.setText(patient.getTelephone());

        TextField emailField = new TextField();
        emailField.setId("emailField");
        emailField.setPromptText("Email");
        emailField.setText(patient.getEmail());

        TextField adresseField = new TextField();
        adresseField.setId("adresseField");
        adresseField.setPromptText("Adresse");
        adresseField.setText(patient.getAdresse());

        Label messageLabel = new Label();
        messageLabel.setId("messageLabel");
        messageLabel.setStyle("-fx-font-size: 12;");

        grid.add(new Label("Téléphone:"), 0, 0);
        grid.add(telephoneField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Adresse:"), 0, 2);
        grid.add(adresseField, 1, 2);
        grid.add(messageLabel, 0, 3);
        GridPane.setColumnSpan(messageLabel, 2);

        return grid;
    }
}
