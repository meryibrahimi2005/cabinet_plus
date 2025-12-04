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
import javafx.stage.Stage;
import ma.cabinetplus.service.PatientServiceImpl;
import ma.cabinetplus.model.Patient;
import java.time.LocalDate;

/**
 * Écran d'inscription pour les patients
 */
public class PatientSignUpScreen {

    private Stage stage;
    private PatientServiceImpl patientService;

    public PatientSignUpScreen(Stage stage) {
        this.stage = stage;
        this.patientService = new PatientServiceImpl();
    }

    public void show() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #f5f5f5;");

        //HBox topBox : barre en haut (contient button retour)
        HBox topBox = new HBox();
        topBox.setStyle("-fx-background-color: #34495e;");
        topBox.setPadding(new Insets(20));

        Button backButton = new Button("← Retour");
        backButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBack());
        topBox.getChildren().add(backButton);

        //VBox centreBox : structure vertcale en centre ( contient titer + formulaire (sous forme d un gridPnane )
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(40));
        centerBox.setStyle("-fx-background-color: white;");

        Text title = new Text("Créer un compte Patient");
        title.setFont(new Font("Arial", 24));
        title.setStyle("-fx-fill: #2c3e50;");

        //cree une formulaire
        GridPane form = createSignUpForm();
        centerBox.getChildren().addAll(title, form);

        root.getChildren().addAll(topBox, centerBox);

        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Inscription Patient");
        stage.show();
    }

    //methode qui cree une formulaire
    //  | Label "Nom:"          |  TextField nomField           |
    //  | (colonne 0 )          |  (colonne 1 )               |
    //  | (ligne 0  )           |  (ligne 0  )                |

    private GridPane createSignUpForm() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-border-color: #ecf0f1; -fx-border-width: 1; -fx-border-radius: 5;");

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        nomField.setPrefWidth(200);

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        prenomField.setPrefWidth(200);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setPrefWidth(200);

        DatePicker dateNaissanceField = new DatePicker();
        dateNaissanceField.setPrefWidth(200);

        TextField telephoneField = new TextField();
        telephoneField.setPromptText("Téléphone");
        telephoneField.setPrefWidth(200);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(200);

        TextField adresseField = new TextField();
        adresseField.setPromptText("Adresse");
        adresseField.setPrefWidth(200);

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12;");

        Button signUpButton = new Button("S'inscrire");
        signUpButton.setStyle("-fx-font-size: 14; -fx-padding: 10 40 10 40; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white;");
        signUpButton.setOnAction(e -> {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            LocalDate dateNaissance = dateNaissanceField.getValue();
            String telephone = telephoneField.getText().trim();
            String email = emailField.getText().trim();
            String adresse = adresseField.getText().trim();

            // si il y a des champs vide
            if (nom.isEmpty() || prenom.isEmpty() || username.isEmpty() ||
                    password.isEmpty() || dateNaissance == null || telephone.isEmpty() ||
                    email.isEmpty() || adresse.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs");
                messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            try {
                String numeroDossier = "DOS-" + System.currentTimeMillis();   // incremante le nombre dossier automatiquement
                Patient newPatient = new Patient(nom, prenom, username, password,
                        dateNaissance, telephone, email, adresse, numeroDossier);

                patientService.ajouterPatient(newPatient);   // pour gerer la logique metiers

                messageLabel.setText("Compte créé avec succès! Redirection...");
                messageLabel.setStyle("-fx-text-fill: #27ae60;");

                // Le code attend 2 secondes puis renvoie à la page précédente ( on utilise les threads )
                javafx.application.Platform.runLater(() -> {
                    try {
                        Thread.sleep(2000);
                        goBack();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });

            } catch (Exception ex) {
                messageLabel.setText("Erreur: " + ex.getMessage());
                messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            }
        });

        // ajouter dans la positionnement dans la grill
        // syntaxe : grid.add(element, colonne, ligne)
        // result : Nom: [___________]
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Nom d'utilisateur:"), 0, 2);
        grid.add(usernameField, 1, 2);
        grid.add(new Label("Mot de passe:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Label("Date de naissance:"), 0, 4);
        grid.add(dateNaissanceField, 1, 4);
        grid.add(new Label("Téléphone:"), 0, 5);
        grid.add(telephoneField, 1, 5);
        grid.add(new Label("Email:"), 0, 6);
        grid.add(emailField, 1, 6);
        grid.add(new Label("Adresse:"), 0, 7);
        grid.add(adresseField, 1, 7);

        VBox submitBox = new VBox(10);
        submitBox.setAlignment(Pos.CENTER);
        submitBox.getChildren().addAll(signUpButton, messageLabel);

        grid.add(submitBox, 0, 8);
        GridPane.setColumnSpan(submitBox, 2);

        return grid;
    }

    private void goBack() {
        AuthenticationScreen authScreen = new AuthenticationScreen(stage, ma.cabinetplus.ui.model.UserRole.PATIENT);
        authScreen.show();
    }
}
