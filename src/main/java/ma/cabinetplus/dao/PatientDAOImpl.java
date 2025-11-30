package ma.cabinetplus.dao;

import ma.cabinetplus.model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public void ajouter(Patient patient) {
        String sql = "INSERT INTO patient (nom, prenom, username, password, date_naissance, telephone, email, adresse, numero_dossier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Override
    public Patient trouverParId(Long id) {
        String sql = "SELECT * FROM patient WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPatient(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> trouverTous() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(mapPatient(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public Patient trouverParNumeroDossier(String numeroDossier) {
        String sql = "SELECT * FROM patient WHERE numero_dossier=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroDossier);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPatient(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Patient trouverParUsername(String username) {
        String sql = "SELECT * FROM patient WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPatient(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Patient mapPatient(ResultSet rs) throws SQLException {
        return new Patient(
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
    }
}