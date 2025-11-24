package ma.cabinetplus.dao;

import ma.cabinetplus.model.Consultation;
import java.util.List;

public interface ConsultationDAO extends GenericDAO<Consultation, Long> {
    List<Consultation> trouverParPatient(int patientId);
}