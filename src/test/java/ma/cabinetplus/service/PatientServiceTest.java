package ma.cabinetplus.service;

import ma.cabinetplus.dao.PatientDAO;
import ma.cabinetplus.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient("Doe", "Jane", "jane", "pass123",
                LocalDate.of(2000, 1, 1), "0612345678",
                "jane@example.com", "123 rue", "D001");
    }

    @Test
    void testAjouterPatient() {
        patientService.ajouterPatient(patient);
        verify(patientDAO, times(1)).ajouter(patient);
    }

    @Test
    void testTrouverParId() {
        when(patientDAO.trouverParId(1L)).thenReturn(Optional.of(patient));
        Patient result = patientService.trouverParId(1L);
        assertNotNull(result);
        assertEquals("Jane", result.getPrenom());
    }

    @Test
    void testListerPatients() {
        when(patientDAO.trouverTous()).thenReturn(Arrays.asList(patient));
        List<Patient> patients = patientService.trouverTous();
        assertEquals(1, patients.size());
    }
}
