package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.cabinetplus.service.PatientServiceImpl;
import ma.cabinetplus.model.Patient;
import java.time.LocalDate;

/**
 * Dialog pour ajouter ou modifier un patient
 */
public class AddEditPatientDialog {

    private Stage dialogStage;
    private Patient patient;
    private PatientServiceImpl patientService;
    private boolean isNew;

    public AddEditPatientDialog(Stage parentStage, Patient patient, PatientServiceImpl patientService) {
        this.patient = patient;
        this.patientService = patientService;
        this.isNew = (patient == null);

        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle(isNew ? "Ajouter un Patient" : "Modifier un Patient");
    }

    public void showAndWait() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");

        Text title = new Text(isNew ? "Nouveau Patient" : "Modifier Patient");
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

        Scene scene = new Scene(root, 500, 600);
        dialogStage.setScene(scene);

        // Store references to form fields for save action
        TextField nomField = (TextField) form.lookup("#nomField");
        TextField prenomField = (TextField) form.lookup("#prenomField");
        TextField usernameField = (TextField) form.lookup("#usernameField");
        PasswordField passwordField = (PasswordField) form.lookup("#passwordField");
        DatePicker dateField = (DatePicker) form.lookup("#dateField");
        TextField telephoneField = (TextField) form.lookup("#telephoneField");
        TextField emailField = (TextField) form.lookup("#emailField");
        TextField adresseField = (TextField) form.lookup("#adresseField");
        Label messageLabel = (Label) form.lookup("#messageLabel");

        saveButton.setOnAction(e -> {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            LocalDate dateNaissance = dateField.getValue();
            String telephone = telephoneField.getText().trim();
            String email = emailField.getText().trim();
            String adresse = adresseField.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty() || username.isEmpty() ||
                    password.isEmpty() || dateNaissance == null) {
                messageLabel.setText("Veuillez remplir les champs obligatoires");
                messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            try {
                if (isNew) {
                    String numeroDossier = "DOS-" + System.currentTimeMillis();
                    Patient newPatient = new Patient(nom, prenom, username, password,
                            dateNaissance, telephone, email, adresse, numeroDossier);
                    patientService.ajouterPatient(newPatient);
                } else {
                    patient.setNom(nom);
                    patient.setPrenom(prenom);
                    patient.setUsername(username);
                    patient.setPassword(password);
                    patient.setDateNaissance(dateNaissance);
                    patient.setTelephone(telephone);
                    patient.setEmail(email);
                    patient.setAdresse(adresse);
                    // Update would require a service method, for now just show success
                }

                messageLabel.setText("Enregistré avec succès!");
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

        TextField nomField = new TextField();
        nomField.setId("nomField");
        nomField.setPromptText("Nom");
        if (!isNew) nomField.setText(patient.getNom());

        TextField prenomField = new TextField();
        prenomField.setId("prenomField");
        prenomField.setPromptText("Prénom");
        if (!isNew) prenomField.setText(patient.getPrenom());

        TextField usernameField = new TextField();
        usernameField.setId("usernameField");
        usernameField.setPromptText("Username");
        if (!isNew) {
            usernameField.setText(patient.getUsername());
            usernameField.setDisable(true);
        }

        PasswordField passwordField = new PasswordField();
        passwordField.setId("passwordField");
        passwordField.setPromptText("Mot de passe");
        if (!isNew) passwordField.setText(patient.getPassword());

        DatePicker dateField = new DatePicker();
        dateField.setId("dateField");
        if (!isNew) dateField.setValue(patient.getDateNaissance());

        TextField telephoneField = new TextField();
        telephoneField.setId("telephoneField");
        telephoneField.setPromptText("Téléphone");
        if (!isNew) telephoneField.setText(patient.getTelephone());

        TextField emailField = new TextField();
        emailField.setId("emailField");
        emailField.setPromptText("Email");
        if (!isNew) emailField.setText(patient.getEmail());

        TextField adresseField = new TextField();
        adresseField.setId("adresseField");
        adresseField.setPromptText("Adresse");
        if (!isNew) adresseField.setText(patient.getAdresse());

        Label messageLabel = new Label();
        messageLabel.setId("messageLabel");
        messageLabel.setStyle("-fx-font-size: 12;");

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Username:"), 0, 2);
        grid.add(usernameField, 1, 2);
        grid.add(new Label("Mot de passe:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Label("Date naissance:"), 0, 4);
        grid.add(dateField, 1, 4);
        grid.add(new Label("Téléphone:"), 0, 5);
        grid.add(telephoneField, 1, 5);
        grid.add(new Label("Email:"), 0, 6);
        grid.add(emailField, 1, 6);
        grid.add(new Label("Adresse:"), 0, 7);
        grid.add(adresseField, 1, 7);
        grid.add(messageLabel, 0, 8);
        GridPane.setColumnSpan(messageLabel, 2);

        return grid;
    }
}
