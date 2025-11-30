package ma.cabinetplus.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedecinTest {

    private Medecin medecin;

    @BeforeEach
    void setUp() {
        medecin = new Medecin(
                10L,
                "Samira",
                "Haddou",
                "samira.h",
                "pass123"
        );
    }

    @Test
    void testConstructorAndGetters() {

        assertEquals(10, medecin.getId());
        assertEquals("Samira", medecin.getNom());
        assertEquals("Haddou", medecin.getPrenom());
        assertEquals("samira.h", medecin.getUsername());
        assertEquals("pass123", medecin.getPassword());
        assertEquals(Role.MEDECIN, medecin.getRole());
    }

    @Test
    void testSetId() {
        medecin.setId(50L);
        assertEquals(50L, medecin.getId());
    }

    @Test
    void testToStringContainsInformation() {
        String str = medecin.toString();

        assertNotNull(str);
        assertTrue(str.contains("Samira"));
        assertTrue(str.contains("Haddou"));
        assertTrue(str.contains("samira.h"));
        assertTrue(str.startsWith("Medecin"));
    }
}
