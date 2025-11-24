package ma.cabinetplus.service;

import ma.cabinetplus.Consultation;
import java.util.List;

public interface ConsultationService extends GenericService<Consultation, Long> {
    List<Consultation> trouverParPatient(int patientId);
}
