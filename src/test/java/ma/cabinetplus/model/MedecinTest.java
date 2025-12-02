package ma.cabinetplus.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Medecin model class
 */
class MedecinTest {

    @Test
    void testMedecinCreation() {
        Long id = 1L;
        String nom = "Bennani";
        String prenom = "Karim";
        String username = "kbennani";
        String password = "doctor123";

        Medecin medecin = new Medecin(id, nom, prenom, username, password);

        assertEquals(id, medecin.getId());
        assertEquals(nom, medecin.getNom());
        assertEquals(prenom, medecin.getPrenom());
        assertEquals(username, medecin.getUsername());
        assertEquals(password, medecin.getPassword());
        assertEquals(Role.MEDECIN, medecin.getRole());
    }

    @Test
    void testMedecinSetters() {
        Medecin medecin = new Medecin(1L, "Old", "Name", "olduser", "oldpass");

        medecin.setId(999L);
        medecin.setNom("Alaoui");
        medecin.setPrenom("Sara");
        medecin.setUsername("salaoui");
        medecin.setPassword("newpass");

        assertEquals(999L, medecin.getId());
        assertEquals("Alaoui", medecin.getNom());
        assertEquals("Sara", medecin.getPrenom());
        assertEquals("salaoui", medecin.getUsername());
        assertEquals("newpass", medecin.getPassword());
    }

    @Test
    void testToString() {
        Medecin medecin = new Medecin(1L, "Bennani", "Karim", "kbennani", "doctor123");

        String result = medecin.toString();

        assertTrue(result.contains("Medecin"));
        assertTrue(result.contains("Bennani"));
        assertTrue(result.contains("Karim"));
        assertTrue(result.contains("kbennani"));
    }
}