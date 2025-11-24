package ma.cabinetplus.dao;

import ma.cabinetplus.Consultation;
import ma.cabinetplus.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDAOImpl implements ConsultationDAO {

    @Override
    public void ajouter(Consultation c) {
        String sql = "INSERT INTO consultation (patient_id, numero_dossier, date, prix, note) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getPatient().getId());
            stmt.setString(2, c.getNumeroDossier());
            stmt.setDate(3, Date.valueOf(c.getDate()));
            stmt.setDouble(4, c.getPrix());
            stmt.setString(5, c.getNote());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }


    @Override
    public List<Consultation> trouverParPatient(int patientId) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM consultation WHERE patient_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapConsultation(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Consultation> trouverTous() {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM consultation";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapConsultation(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Consultation trouverParId(Long id) {
        String sql = "SELECT * FROM consultation WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapConsultation(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Consultation mapConsultation(ResultSet rs) throws SQLException {
        int patientId = rs.getInt("patient_id");
        Patient patient = new PatientDAOImpl().trouverParId(patientId);

        return new Consultation(
                rs.getLong("id"),
                patient,
                rs.getString("numero_dossier"),
                rs.getDate("date").toLocalDate(),
                rs.getDouble("prix"),
                rs.getString("note")
        );
    }
}