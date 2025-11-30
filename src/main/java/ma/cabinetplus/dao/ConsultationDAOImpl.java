package ma.cabinetplus.dao;

import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.exception.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationDAOImpl implements ConsultationDAO {

    @Override
    public void ajouter(Consultation consultation) {
        String sql = "INSERT INTO consultation (patient_id, numero_dossier, date_consultation, " +
                     "prix, note) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, consultation.getPatient().getId());
            stmt.setString(2, consultation.getNumeroDossier());
            stmt.setDate(3, Date.valueOf(consultation.getDate()));
            stmt.setDouble(4, consultation.getPrix());
            stmt.setString(5, consultation.getNote());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to add consultation: " + e.getMessage(), e);
        }
    }

    @Override
    public void supprimer(Long id) {
        String sql = "DELETE FROM consultation WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete consultation: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Consultation> trouverParId(Long id) {
        String sql = "SELECT id, patient_id, numero_dossier, date_consultation, prix, note " +
                     "FROM consultation WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapConsultation(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find consultation: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Consultation> trouverTous() {
        List<Consultation> consultations = new ArrayList<>();
        String sql = "SELECT id, patient_id, numero_dossier, date_consultation, prix, note " +
                     "FROM consultation";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                consultations.add(mapConsultation(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve consultations: " + e.getMessage(), e);
        }
        return consultations;
    }

    @Override
    public List<Consultation> trouverParPatient(Long patientId) {
        List<Consultation> consultations = new ArrayList<>();
        String sql = "SELECT id, patient_id, numero_dossier, date_consultation, prix, note " +
                     "FROM consultation WHERE patient_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultations.add(mapConsultation(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find consultations for patient: " + e.getMessage(), e);
        }
        return consultations;
    }

    private Consultation mapConsultation(ResultSet rs) throws SQLException {
        Long patientId = rs.getLong("patient_id");
        Optional<Patient> patient = new PatientDAOImpl().trouverParId(patientId);
        
        if (patient.isEmpty()) {
            throw new DataAccessException("Patient not found for consultation with ID: " + patientId);
        }

        return new Consultation(
                rs.getLong("id"),
                patient.get(),
                rs.getString("numero_dossier"),
                rs.getDate("date_consultation").toLocalDate(),
                rs.getDouble("prix"),
                rs.getString("note")
        );
    }
}
