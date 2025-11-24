package ma.cabinetplus.service;

import ma.cabinetplus.dao.ConsultationDAO;
import ma.cabinetplus.dao.ConsultationDAOImpl;
import ma.cabinetplus.Consultation;

import java.util.List;

public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationDAO consultationDAO = new ConsultationDAOImpl();

    @Override
    public void ajouter(Consultation c) {
        consultationDAO.ajouter(c);
    }

    @Override
    public void supprimer(Long id) {
        // Optionnel: ajouter méthode supprimer dans ConsultationDAO si nécessaire
    }

    @Override
    public Consultation trouverParId(Long id) {
        return consultationDAO.trouverParId(id);
    }

    @Override
    public List<Consultation> trouverTous() {
        return consultationDAO.trouverTous();
    }

    @Override
    public List<Consultation> trouverParPatient(int patientId) {
        return consultationDAO.trouverParPatient(patientId);
    }
}
