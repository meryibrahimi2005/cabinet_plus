package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ma.cabinetplus.ui.model.UserContext;
import ma.cabinetplus.ui.model.UserRole;
import ma.cabinetplus.service.PatientServiceImpl;
import ma.cabinetplus.service.MedecinServiceImpl;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.model.Medecin;
import java.time.LocalDate;

/**
 * Écran d'authentification (Login/SignUp)
 */
public class AuthenticationScreen {

    private Stage stage;
    private UserRole role;
    private PatientServiceImpl patientService;
    private MedecinServiceImpl medecinService;
    private boolean isSignUp;

    public AuthenticationScreen(Stage stage, UserRole role) {
        this.stage = stage;
        this.role = role;
        this.patientService = new PatientServiceImpl();
        this.medecinService = new MedecinServiceImpl();
        this.isSignUp = false;
    }

    public void show() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox topBox = new HBox();
        topBox.setStyle("-fx-background-color: #34495e;");
        topBox.setPadding(new Insets(20));
        topBox.setSpacing(10);

        Button backButton = new Button("← Retour");
        backButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBack());
        topBox.getChildren().add(backButton);

        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        Text roleText = new Text("Authentification - " + role.getDisplayName());
        roleText.setFont(new Font("Arial", 28));
        roleText.setStyle("-fx-fill: #2c3e50;");

        if (role == UserRole.PATIENT) {
            centerBox.getChildren().add(createPatientAuthUI());
        } else {
            centerBox.getChildren().add(createMedecinAuthUI());
        }

        VBox mainContent = new VBox();
        mainContent.setStyle("-fx-background-color: white;");
        mainContent.getChildren().addAll(roleText, centerBox);
        VBox.setVgrow(mainContent, javafx.scene.layout.Priority.ALWAYS);

        root.getChildren().addAll(topBox, mainContent);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Authentification");
        stage.show();
    }

    private VBox createMedecinAuthUI() {
        VBox box = new VBox(15);
        box.setAlignment(Pos.TOP_CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        usernameField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        passwordField.setPrefWidth(300);

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12;");

        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-font-size: 14; -fx-padding: 10 50 10 50; " +
                "-fx-background-color: #3498db; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs");
                return;
            }

            try {
                Medecin medecin = medecinService.trouverParUsername(username);
                if (medecin != null && medecin.getPassword().equals(password)) {
                    UserContext.getInstance().setCurrentUser(medecin, UserRole.MEDECIN);
                    openMedecinDashboard();
                } else {
                    messageLabel.setText("Identifiants incorrects");
                }
            } catch (Exception ex) {
                messageLabel.setText("Erreur: " + ex.getMessage());
            }
        });

        box.getChildren().addAll(usernameField, passwordField, loginButton, messageLabel);
        return box;
    }

    private VBox createPatientAuthUI() {
        VBox box = new VBox(15);
        box.setAlignment(Pos.TOP_CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        usernameField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        passwordField.setPrefWidth(300);

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12;");

        Button loginButton = new Button("Se connecter");
        loginButton.setStyle("-fx-font-size: 14; -fx-padding: 10 50 10 50; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs");
                return;
            }

            try {
                Patient patient = patientService.trouverParUsername(username);
                if (patient != null && patient.getPassword().equals(password)) {
                    UserContext.getInstance().setCurrentUser(patient, UserRole.PATIENT);
                    openPatientDashboard();
                } else {
                    messageLabel.setText("Identifiants incorrects");
                }
            } catch (Exception ex) {
                messageLabel.setText("Erreur: " + ex.getMessage());
            }
        });

        Button signUpButton = new Button("Créer un compte");
        signUpButton.setStyle("-fx-font-size: 14; -fx-padding: 10 50 10 50; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        signUpButton.setOnAction(e -> {
            openPatientSignUp();
        });

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, signUpButton);

        box.getChildren().addAll(usernameField, passwordField, buttonBox, messageLabel);
        return box;
    }

    private void openPatientSignUp() {
        PatientSignUpScreen signUpScreen = new PatientSignUpScreen(stage);
        signUpScreen.show();
    }

    private void openMedecinDashboard() {
        PatientManagementScreen dashboard = new PatientManagementScreen(stage);
        dashboard.show();
    }

    private void openPatientDashboard() {
        PatientDashboardScreen dashboard = new PatientDashboardScreen(stage);
        dashboard.show();
    }

    private void goBack() {
        RoleSelectionScreen roleScreen = new RoleSelectionScreen(stage);
        roleScreen.show();
    }
}
