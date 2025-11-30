package ma.cabinetplus.service;

import ma.cabinetplus.dao.ConsultationDAO;
import ma.cabinetplus.dao.PatientDAO;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultationServiceTest {

    @Mock
    private ConsultationDAO consultationDAO;

    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private ConsultationServiceImpl service;

    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        patient = new Patient(
                "Dupont", "Jean", "jdupont", "pass123",
                LocalDate.of(1990, 1, 1),
                "0600000000", "jdupont@mail.com",
                "Paris", "D001"
        );
        patient.setId(1L);
    }

    @Test
    void testAjouterConsultationValide() {
        Consultation consultation = new Consultation(
                1L, patient, patient.getNumeroDossier(),
                LocalDate.now().minusDays(1), 50.0, "Note"
        );

        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        assertDoesNotThrow(() -> service.ajouterConsultation(consultation));

        verify(consultationDAO, times(1)).ajouter(consultation);
    }

    @Test
    void testAjouterConsultationPatientInexistant() {
        Consultation consultation = new Consultation(
                1L, patient, patient.getNumeroDossier(),
                LocalDate.now().minusDays(1), 50.0, "Note"
        );

        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.ajouterConsultation(consultation)
        );

        assertEquals("Patient introuvable !", exception.getMessage());
        verify(consultationDAO, never()).ajouter(any());
    }

    @Test
    void testAjouterConsultationPrixNegatif() {
        Consultation consultation = new Consultation(
                1L, patient, patient.getNumeroDossier(),
                LocalDate.now().minusDays(1), -10.0, "Note"
        );

        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.ajouterConsultation(consultation)
        );

        assertEquals("Le prix de la consultation doit être positif !", exception.getMessage());
        verify(consultationDAO, never()).ajouter(any());
    }

    @Test
    void testAjouterConsultationDateFuture() {
        Consultation consultation = new Consultation(
                1L, patient, patient.getNumeroDossier(),
                LocalDate.now().plusDays(5), 50.0, "Note"
        );

        when(patientDAO.trouverParId(patient.getId())).thenReturn(Optional.of(patient));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.ajouterConsultation(consultation)
        );

        assertEquals("La consultation ne peut pas être dans le futur !", exception.getMessage());
        verify(consultationDAO, never()).ajouter(any());
    }

    @Test
    void testTrouverConsultationParId() {
        Consultation consultation = new Consultation(
                1L, patient, patient.getNumeroDossier(),
                LocalDate.now(), 50.0, "Note"
        );

        when(consultationDAO.trouverParId(1L)).thenReturn(Optional.of(consultation));

        Consultation result = service.trouverParId(1L);
        assertEquals(consultation, result);
    }

    @Test
    void testSupprimerConsultation() {
        service.supprimer(1L);
        verify(consultationDAO, times(1)).supprimer(1L);
    }
}
