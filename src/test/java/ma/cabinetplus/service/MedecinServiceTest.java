package ma.cabinetplus.service;

import ma.cabinetplus.dao.MedecinDAO;
import ma.cabinetplus.model.Medecin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MedecinServiceTest {

    @Mock
    private MedecinDAO medecinDAO;

    @InjectMocks
    private MedecinServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterMedecin_Success() throws Exception {
        Medecin m = new Medecin(1, "Ahmed", "Ali", "ahmed", "pass");

        when(medecinDAO.trouverParUsername("ahmed")).thenReturn(null);

        assertDoesNotThrow(() -> service.ajouterMedecin(m));

        verify(medecinDAO, times(1)).ajouter(m);
    }

    @Test
    void testAjouterMedecin_UsernameExiste() {
        Medecin m = new Medecin(1, "Ahmed", "Ali", "ahmed", "pass");

        when(medecinDAO.trouverParUsername("ahmed")).thenReturn(m);

        assertThrows(Exception.class, () -> service.ajouterMedecin(m));
    }

    @Test
    void testAjouterMedecin_ChampVide() {
        Medecin m = new Medecin(1, "", "Ali", "ahmed", "pass");

        assertThrows(Exception.class, () -> service.ajouterMedecin(m));
    }
}
