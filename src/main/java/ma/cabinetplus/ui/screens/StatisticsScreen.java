package ma.cabinetplus.ui.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ma.cabinetplus.dao.StatsDAO;
import ma.cabinetplus.ui.model.UserContext;

import java.util.List;
import java.util.Map;

public class StatisticsScreen {

    private Stage stage;
    private StatsDAO statsDAO;

    public StatisticsScreen(Stage stage) {
        this.stage = stage;
        this.statsDAO = new StatsDAO();
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        root.setTop(createTopBar());
        root.setCenter(createMainContent());

        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Cabinet Plus - Statistiques");
        stage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #34495e;");
        topBar.setPadding(new Insets(15));
        topBar.setSpacing(15);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("📊 Statistiques du Cabinet");
        title.setFont(new Font("Arial", 18));
        title.setStyle("-fx-fill: white;");

        Button refreshButton = new Button("🔄 Rafraîchir");
        refreshButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white;");
        refreshButton.setOnAction(e -> {
            statsDAO.refreshAllMaterializedViews();
            show(); // Recharger l'écran
        });

        Button backButton = new Button("← Retour");
        backButton.setStyle("-fx-font-size: 12; -fx-padding: 8 15 8 15; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        backButton.setOnAction(e -> goBack());

        HBox.setHgrow(title, Priority.ALWAYS);
        topBar.getChildren().addAll(title, refreshButton, backButton);
        return topBar;
    }

    private ScrollPane createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: white;");

        // Dashboard avec les métriques clés
        mainContent.getChildren().add(createDashboard());

        // Top patients actifs
        mainContent.getChildren().add(createTopPatientsSection());

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private GridPane createDashboard() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));

        Text dashboardTitle = new Text("📈 Vue d'ensemble");
        dashboardTitle.setFont(new Font("Arial", 16));
        dashboardTitle.setStyle("-fx-fill: #2c3e50; -fx-font-weight: bold;");
        grid.add(dashboardTitle, 0, 0, 2, 1);

        Map<String, String> dashboard = statsDAO.getDashboardMedecin();

        int row = 1;
        for (Map.Entry<String, String> entry : dashboard.entrySet()) {
            VBox card = createStatCard(
                    formatMetricName(entry.getKey()),
                    entry.getValue()
            );
            grid.add(card, (row - 1) % 3, 1 + (row - 1) / 3);
            row++;
        }

        return grid;
    }

    private VBox createStatCard(String label, String value) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: #ecf0f1; -fx-border-radius: 5; " +
                "-fx-background-radius: 5;");
        card.setPrefWidth(200);

        Text valueText = new Text(value);
        valueText.setFont(new Font("Arial", 24));
        valueText.setStyle("-fx-fill: #2980b9; -fx-font-weight: bold;");

        Text labelText = new Text(label);
        labelText.setFont(new Font("Arial", 12));
        labelText.setStyle("-fx-fill: #7f8c8d;");

        card.getChildren().addAll(valueText, labelText);
        return card;
    }

    private VBox createTopPatientsSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));

        Text title = new Text("🏆 Top 10 Patients les Plus Actifs");
        title.setFont(new Font("Arial", 16));
        title.setStyle("-fx-fill: #2c3e50; -fx-font-weight: bold;");

        TableView<Map<String, Object>> table = new TableView<>();

        TableColumn<Map<String, Object>, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().get("nom").toString()
                )
        );

        TableColumn<Map<String, Object>, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().get("prenom").toString()
                )
        );

        TableColumn<Map<String, Object>, String> consultationsCol =
                new TableColumn<>("Consultations");
        consultationsCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().get("nombre_consultations").toString()
                )
        );

        TableColumn<Map<String, Object>, String> depensesCol =
                new TableColumn<>("Dépenses Totales");
        depensesCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("%.2f €", data.getValue().get("total_depenses"))
                )
        );

        table.getColumns().addAll(nomCol, prenomCol, consultationsCol, depensesCol);

        List<Map<String, Object>> topPatients = statsDAO.getTopPatientsActifs();
        table.getItems().addAll(topPatients);

        section.getChildren().addAll(title, table);
        return section;
    }

    private String formatMetricName(String metric) {
        return switch (metric) {
            case "total_patients" -> "Total Patients";
            case "total_consultations" -> "Total Consultations";
            case "total_rdv" -> "Total Rendez-vous";
            case "rdv_aujourd_hui" -> "RDV Aujourd'hui";
            case "revenus_mois" -> "Revenus du Mois";
            case "prix_moyen" -> "Prix Moyen";
            default -> metric;
        };
    }

    private void goBack() {
        AppointmentManagementScreen screen = new AppointmentManagementScreen(stage);
        screen.show();
    }
}