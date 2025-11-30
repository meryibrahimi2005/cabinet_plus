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
    public void supprimer(Long id) {
        String sql = "DELETE FROM medecin WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Medecin trouverParId(Long id) {
        String sql = "SELECT * FROM medecin WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // IMPORTANT : ton constructeur prend EXACTEMENT ces 5 paramètres !
                Medecin m = new Medecin(
                        (long)rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("username"),
                        rs.getString("password")
                );

                // L'id n'est pas stocké dans le constructeur → on doit le remettre :
                m.setId((long)rs.getInt("id"));

                return m;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Medecin trouverParUsername(String username) {
        String sql = "SELECT * FROM medecin WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Medecin m = new Medecin(
                        (long)rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                m.setId((long)rs.getInt("id"));
                return m;
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

                Medecin m = new Medecin(
                        (long)rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("username"),
                        rs.getString("password")
                );

                m.setId((long)rs.getInt("id"));

                medecins.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medecins;
    }
}
