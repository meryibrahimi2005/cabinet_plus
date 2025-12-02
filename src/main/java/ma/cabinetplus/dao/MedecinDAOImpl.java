package ma.cabinetplus.dao;

import ma.cabinetplus.model.Medecin;
import ma.cabinetplus.exception.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinDAOImpl implements MedecinDAO {

    @Override
    public void ajouter(Medecin medecin) {
        String sql = "INSERT INTO medecin (nom, prenom, username, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medecin.getNom());
            stmt.setString(2, medecin.getPrenom());
            stmt.setString(3, medecin.getUsername());
            stmt.setString(4, medecin.getPassword());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to add medecin: " + e.getMessage(), e);
        }
    }

    @Override
    public void supprimer(Long id) {
        String sql = "DELETE FROM medecin WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete medecin: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Medecin> trouverParId(Long id) {
        String sql = "SELECT id, nom, prenom, username, password FROM medecin WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapMedecin(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find medecin: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Medecin> trouverTous() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, username, password FROM medecin";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medecins.add(mapMedecin(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve medecins: " + e.getMessage(), e);
        }
        return medecins;
    }

    @Override
    public Optional<Medecin> trouverParUsername(String username) {
        String sql = "SELECT id, nom, prenom, username, password FROM medecin WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapMedecin(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find medecin by username: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Medecin mapMedecin(ResultSet rs) throws SQLException {
        Medecin m = new Medecin(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("username"),
                rs.getString("password")
        );
        return m;
    }

    @Override
    public void mettreAJour(Medecin medecin) {
        String sql = "UPDATE medecin SET nom=?, prenom=?, username=?, password=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, medecin.getNom());
            stmt.setString(2, medecin.getPrenom());
            stmt.setString(3, medecin.getUsername());
            stmt.setString(4, medecin.getPassword());
            stmt.setLong(5, medecin.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update medecin: " + e.getMessage(), e);
        }
    }
}
