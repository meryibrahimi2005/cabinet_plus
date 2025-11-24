package ma.cabinetplus.dao;

import ma.cabinetplus.Consultation;
import java.util.List;

public interface ConsultationDAO extends GenericDAO<Consultation, Long> {
    List<Consultation> trouverParPatient(int patientId);
}