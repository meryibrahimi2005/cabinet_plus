package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import ma.cabinetplus.service.RendezVousServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Écran pour qu'un patient prenne un rendez-vous
 */
public class BookAppointmentScreen {

    private Stage dialogStage;
    private Patient patient;
    private RendezVousServiceImpl rendezVousService;
    private Runnable onSuccess;
    // Form fields as instance members to avoid lookup issues
    private DatePicker dateField;
    private ComboBox<String> timeField;
    private TextField motifField;
    private Label messageLabel;

    public BookAppointmentScreen(Stage parentStage, Patient patient, RendezVousServiceImpl rendezVousService, Runnable onSuccess) {
        this.patient = patient;
        this.rendezVousService = rendezVousService;
        this.onSuccess = onSuccess;

        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("Prendre un Rendez-vous");
    }

    public void showAndWait() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;");

        Text title = new Text("Prendre un Rendez-vous");
        title.setFont(new Font("Arial", 18));
        title.setStyle("-fx-fill: #2c3e50;");

        GridPane form = createForm();

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button bookButton = new Button("Réserver");
        bookButton.setStyle("-fx-font-size: 12; -fx-padding: 8 20 8 20; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white;");

        Button cancelButton = new Button("Annuler");
        cancelButton.setStyle("-fx-font-size: 12; -fx-padding: 8 20 8 20; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> dialogStage.close());

        buttonBox.getChildren().addAll(bookButton, cancelButton);

        root.getChildren().addAll(title, form, buttonBox);

        Scene scene = new Scene(root, 550, 500);
        dialogStage.setScene(scene);

        // Instance form fields were set in createForm(), wire the button now
        bookButton.setOnAction(e -> {
            try {
                LocalDate date = dateField.getValue();
                String timeStr = timeField.getValue();
                String motif = motifField.getText().trim();

                if (date == null || timeStr == null || motif.isEmpty()) {
                    messageLabel.setText("Veuillez remplir tous les champs");
                    messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                    return;
                }

                if (date.atTime(LocalTime.MIDNIGHT).isBefore(LocalDate.now().atTime(LocalTime.MIDNIGHT))) {
                    messageLabel.setText("La date ne peut pas être dans le passé");
                    messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                    return;
                }

                LocalDateTime dateTime;
                try {
                    dateTime = date.atTime(LocalTime.parse(timeStr));
                } catch (Exception parseEx) {
                    messageLabel.setText("Format d'heure invalide");
                    messageLabel.setStyle("-fx-text-fill: #e74c3c;");
                    return;
                }

                Long rdvId = (long) (System.currentTimeMillis() % 100000);
                RendezVous rdv = new RendezVous(
                        rdvId,
                        dateTime,
                        motif,
                        patient,
                        StatutRendezVous.PREVU
                );

                rendezVousService.ajouterRendezVous(rdv);

                messageLabel.setText("Rendez-vous réservé avec succès!");
                messageLabel.setStyle("-fx-text-fill: #27ae60;");

                // Close after short delay so user sees success message
                new Thread(() -> {
                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException ex) {
                        // ignore
                    }
                    javafx.application.Platform.runLater(() -> {
                        dialogStage.close();
                        if (onSuccess != null) onSuccess.run();
                    });
                }).start();

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
        // Date picker
        dateField = new DatePicker();
        dateField.setValue(LocalDate.now().plusDays(1));
        dateField.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now().plusDays(1)) || item.getDayOfWeek().getValue() > 5);
            }
        });

        // Time selector (business hours: 9AM to 5PM, 30-min slots)
        timeField = new ComboBox<>();
        for (int hour = 9; hour < 17; hour++) {
            timeField.getItems().add(String.format("%02d:00", hour));
            if (hour < 16) {
                timeField.getItems().add(String.format("%02d:30", hour));
            }
        }
        timeField.setPrefWidth(150);

        // Reason field
        motifField = new TextField();
        motifField.setPromptText("Motif de la consultation");
        motifField.setPrefWidth(200);

        // Message label
        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 12;");

        // Add to grid
        grid.add(new Label("Date:"), 0, 0);
        grid.add(dateField, 1, 0);

        grid.add(new Label("Heure:"), 0, 1);
        grid.add(timeField, 1, 1);

        grid.add(new Label("Motif:"), 0, 2);
        grid.add(motifField, 1, 2);

        grid.add(messageLabel, 0, 3);
        GridPane.setColumnSpan(messageLabel, 2);

        // Add availability info
        Text availabilityInfo = new Text("Disponibilités: Lundi-Vendredi, 9h-17h");
        availabilityInfo.setStyle("-fx-font-style: italic; -fx-fill: #7f8c8d;");
        grid.add(availabilityInfo, 0, 4);
        GridPane.setColumnSpan(availabilityInfo, 2);

        return grid;
    }
}
