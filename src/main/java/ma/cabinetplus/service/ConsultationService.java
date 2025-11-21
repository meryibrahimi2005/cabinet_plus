package ma.cabinetplus.service;

import ma.cabinetplus.model.Consultation;
import java.util.List;

public interface ConsultationService extends GenericService<Consultation, Long> {
    List<Consultation> trouverParPatient(int patientId);
}
