package ma.cabinetplus.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {

    @Test
    void testConstructorAndGetters() {
        Personne personne = new Personne(
                "Meryem",
                "Ibrahimi",
                "meryem.i",
                "pass123",
                Role.PATIENT
        );

        assertEquals("Meryem", personne.getNom());
        assertEquals("Ibrahimi", personne.getPrenom());
        assertEquals("meryem.i", personne.getUsername());
        assertEquals("pass123", personne.getPassword());
        assertEquals(Role.PATIENT, personne.getRole());
    }
}
