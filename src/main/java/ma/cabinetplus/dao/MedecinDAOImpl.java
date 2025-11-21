package ma.cabinetplus.dao;

import ma.cabinetplus.model.Medecin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            e.printStackTrace();
        }
    }

    @Override
    public Medecin trouverParUsername(String username) {
        String sql = "SELECT * FROM medecin WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Medecin(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medecin> trouverTous() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM medecin";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medecins.add(new Medecin(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }
}