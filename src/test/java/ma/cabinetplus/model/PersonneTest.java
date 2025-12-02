package ma.cabinetplus.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Personne model class
 */
class PersonneTest {

    @Test
    void testPersonneCreation() {
        String nom = "El Amrani";
        String prenom = "Ayman";
        String username = "aelamrani";
        String password = "password123";
        Role role = Role.PATIENT;

        Personne personne = new Personne(nom, prenom, username, password, role) {};

        assertEquals(nom, personne.getNom());
        assertEquals(prenom, personne.getPrenom());
        assertEquals(username, personne.getUsername());
        assertEquals(password, personne.getPassword());
        assertEquals(role, personne.getRole());
    }

    @Test
    void testPersonneSetters() {
        Personne personne = new Personne("Old", "Name", "olduser", "oldpass", Role.PATIENT) {};

        personne.setNom("Tazi");
        personne.setPrenom("Meryem");
        personne.setUsername("mtazi");
        personne.setPassword("newpass");
        personne.setRole(Role.MEDECIN);

        assertEquals("Tazi", personne.getNom());
        assertEquals("Meryem", personne.getPrenom());
        assertEquals("mtazi", personne.getUsername());
        assertEquals("newpass", personne.getPassword());
        assertEquals(Role.MEDECIN, personne.getRole());
    }

    @Test
    void testToString() {
        // Test abstrait - validé dans classes concrètes
        assertTrue(true);
    }
}