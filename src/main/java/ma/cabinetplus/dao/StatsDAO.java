package ma.cabinetplus.dao;

import java.sql.*;
import java.util.*;

public class StatsDAO {

    // Récupérer les statistiques mensuelles
    public List<Map<String, Object>> getStatsConsultationsMensuelles() {
        List<Map<String, Object>> stats = new ArrayList<>();
        String sql = "SELECT * FROM stats_consultations_mensuelles ORDER BY mois DESC LIMIT 12";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("mois", rs.getDate("mois"));
                row.put("nombre_consultations", rs.getInt("nombre_consultations"));
                row.put("revenus_total", rs.getDouble("revenus_total"));
                row.put("prix_moyen", rs.getDouble("prix_moyen"));
                row.put("nombre_patients_uniques", rs.getInt("nombre_patients_uniques"));
                stats.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des stats", e);
        }

        return stats;
    }

    // Récupérer le dashboard médecin
    public Map<String, String> getDashboardMedecin() {
        Map<String, String> dashboard = new HashMap<>();
        String sql = "SELECT metrique, valeur, description FROM dashboard_medecin";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                dashboard.put(rs.getString("metrique"), rs.getString("valeur"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du dashboard", e);
        }

        return dashboard;
    }

    // Récupérer les top patients
    public List<Map<String, Object>> getTopPatientsActifs() {
        List<Map<String, Object>> patients = new ArrayList<>();
        String sql = "SELECT * FROM top_patients_actifs";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> patient = new HashMap<>();
                patient.put("nom", rs.getString("nom"));
                patient.put("prenom", rs.getString("prenom"));
                patient.put("numero_dossier", rs.getString("numero_dossier"));
                patient.put("nombre_consultations", rs.getInt("nombre_consultations"));
                patient.put("total_depenses", rs.getDouble("total_depenses"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des top patients", e);
        }

        return patients;
    }

    // Rafraîchir toutes les vues matérialisées
    public void refreshAllMaterializedViews() {
        String sql = "SELECT refresh_all_materialized_views()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.execute();
            System.out.println("Vues matérialisées rafraîchies avec succès");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du rafraîchissement des vues", e);
        }
    }
}