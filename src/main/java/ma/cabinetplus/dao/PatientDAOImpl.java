package ma.cabinetplus.dao;

import ma.cabinetplus.model.Patient;
import ma.cabinetplus.exception.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public void ajouter(Patient patient) {
        String sql = "INSERT INTO patient (nom, prenom, username, password, date_naissance, " +
                     "telephone, email, adresse, numero_dossier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getUsername());
            stmt.setString(4, patient.getPassword());
            stmt.setDate(5, Date.valueOf(patient.getDateNaissance()));
            stmt.setString(6, patient.getTelephone());
            stmt.setString(7, patient.getEmail());
            stmt.setString(8, patient.getAdresse());
            stmt.setString(9, patient.getNumeroDossier());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to add patient: " + e.getMessage(), e);
        }
    }

    @Override
    public void supprimer(Long id) {
        String sql = "DELETE FROM patient WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete patient: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Patient> trouverParId(Long id) {
        String sql = "SELECT id, nom, prenom, username, password, date_naissance, " +
                     "telephone, email, adresse, numero_dossier FROM patient WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapPatient(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find patient: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> trouverTous() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, username, password, date_naissance, " +
                     "telephone, email, adresse, numero_dossier FROM patient";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(mapPatient(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve patients: " + e.getMessage(), e);
        }
        return patients;
    }

    @Override
    public Optional<Patient> trouverParNumeroDossier(String numeroDossier) {
        String sql = "SELECT id, nom, prenom, username, password, date_naissance, " +
                     "telephone, email, adresse, numero_dossier FROM patient WHERE numero_dossier = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroDossier);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapPatient(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find patient by dossier: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Patient> trouverParUsername(String username) {
        String sql = "SELECT id, nom, prenom, username, password, date_naissance, " +
                     "telephone, email, adresse, numero_dossier FROM patient WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapPatient(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find patient by username: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient(
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getDate("date_naissance").toLocalDate(),
                rs.getString("telephone"),
                rs.getString("email"),
                rs.getString("adresse"),
                rs.getString("numero_dossier")
        );
        p.setId(rs.getLong("id"));
        return p;
    }
}
