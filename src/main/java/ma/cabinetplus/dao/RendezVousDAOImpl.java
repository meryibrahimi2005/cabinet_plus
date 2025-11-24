package ma.cabinetplus.dao;

import ma.cabinetplus.RendezVous;
import ma.cabinetplus.StatutRendezVous;
import ma.cabinetplus.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAOImpl implements RendezVousDAO {

    @Override
    public void ajouter(RendezVous rdv) {
        String sql = "INSERT INTO rendezvous (date, heure, motif, patient_id, statut) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(rdv.getDate()));
            stmt.setString(2, rdv.getHeure());
            stmt.setString(3, rdv.getMotif());
            stmt.setInt(4, getPatientId(rdv.getPatient().getNumeroDossier()));
            stmt.setString(5, rdv.getStatut().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(Long id) {
        String sql = "DELETE FROM rendezvous WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int getPatientId(String numeroDossier) throws SQLException {
        String sql = "SELECT id FROM patient WHERE numero_dossier=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroDossier);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    @Override
    public void mettreAJourStatut(Long id, StatutRendezVous statut) {
        String sql = "UPDATE rendezvous SET statut=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statut.name());
            stmt.setLong(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RendezVous> trouverParPatient(int patientId) {
        List<RendezVous> list = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous WHERE patient_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRdv(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<RendezVous> trouverTous() {
        List<RendezVous> list = new ArrayList<>();
        String sql = "SELECT * FROM rendezvous";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRdv(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public RendezVous trouverParId(Long id) {
        String sql = "SELECT * FROM rendezvous WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRdv(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private RendezVous mapRdv(ResultSet rs) throws SQLException {
        int patientId = rs.getInt("patient_id");
        Patient patient = new PatientDAOImpl().trouverParId(patientId);

        return new RendezVous(
                rs.getLong("id"),
                rs.getDate("date").toLocalDate(),
                rs.getString("heure"),
                rs.getString("motif"),
                patient,
                StatutRendezVous.valueOf(rs.getString("statut"))
        );
    }
}