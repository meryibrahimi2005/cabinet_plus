package ma.cabinetplus.dao;

import ma.cabinetplus.model.Consultation;
import java.util.List;

public interface ConsultationDAO {
    void ajouter(Consultation c);
    List<Consultation> trouverParPatient(int patientId);
    List<Consultation> trouverTous();
    Consultation trouverParId(Long id);
}