package ma.cabinetplus.dao;

import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.model.StatutRendezVous;
import ma.cabinetplus.exception.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RendezVousDAOImpl implements RendezVousDAO {

    @Override
    public void ajouter(RendezVous rendezVous) {
        String sql = "INSERT INTO rendezvous (date_rdv, motif, patient_id, statut) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, rendezVous.getDateHeureRendezVous());
            stmt.setString(2, rendezVous.getMotif());
            stmt.setLong(3, rendezVous.getPatient().getId());
            stmt.setString(4, rendezVous.getStatut().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to add rendezvous: " + e.getMessage(), e);
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
            throw new DataAccessException("Failed to delete rendezvous: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<RendezVous> trouverParId(Long id) {
        String sql = "SELECT id, date_rdv, motif, patient_id, statut FROM rendezvous WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRendezVous(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find rendezvous: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<RendezVous> trouverTous() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String sql = "SELECT id, date_rdv, motif, patient_id, statut FROM rendezvous";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rendezVousList.add(mapRendezVous(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve rendezvous list: " + e.getMessage(), e);
        }
        return rendezVousList;
    }

    @Override
    public void mettreAJourStatut(Long id, StatutRendezVous statut) {
        String sql = "UPDATE rendezvous SET statut=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statut.toString());
            stmt.setLong(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update rendezvous status: " + e.getMessage(), e);
        }
    }

    @Override
    public List<RendezVous> trouverParPatient(Long patientId) {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String sql = "SELECT id, date_rdv, motif, patient_id, statut FROM rendezvous " +
                     "WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rendezVousList.add(mapRendezVous(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find rendezvous for patient: " + e.getMessage(), e);
        }
        return rendezVousList;
    }

    private RendezVous mapRendezVous(ResultSet rs) throws SQLException {
        Long patientId = rs.getLong("patient_id");
        Optional<Patient> patient = new PatientDAOImpl().trouverParId(patientId);
        
        if (patient.isEmpty()) {
            throw new DataAccessException("Patient not found for rendezvous with ID: " + patientId);
        }

        return new RendezVous(
                rs.getLong("id"),
                rs.getObject("date_rdv", java.time.LocalDateTime.class),
                rs.getString("motif"),
                patient.get(),
                StatutRendezVous.valueOf(rs.getString("statut"))
        );
    }

    @Override
    public void mettreAJour(RendezVous rendezVous) {
        String sql = "UPDATE rendezvous SET date_rdv=?, motif=?, patient_id=?, statut=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, rendezVous.getDateHeureRendezVous());
            stmt.setString(2, rendezVous.getMotif());
            stmt.setLong(3, rendezVous.getPatient().getId());
            stmt.setString(4, rendezVous.getStatut().toString());
            stmt.setLong(5, rendezVous.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update rendezvous: " + e.getMessage(), e);
        }
    }
}
