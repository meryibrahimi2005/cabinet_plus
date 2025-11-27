package ma.cabinetplus.service;

import ma.cabinetplus.model.Consultation;
import java.util.List;

public interface ConsultationService {
    void ajouterConsultation(Consultation c);
    Consultation trouverParId(Long id);
    List<Consultation> trouverParPatient(int id);
    List<Consultation> trouverTous();
    void supprimer(Long id);
}
