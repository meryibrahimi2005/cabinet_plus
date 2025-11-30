package ma.cabinetplus.service;

import ma.cabinetplus.dao.ConsultationDAO;
import ma.cabinetplus.dao.ConsultationDAOImpl;
import ma.cabinetplus.dao.PatientDAOImpl;
import ma.cabinetplus.model.Consultation;
import ma.cabinetplus.service.ConsultationService;

import java.time.LocalDate;
import java.util.List;

public class ConsultationServiceImpl implements ConsultationService {

    private ConsultationDAO consultationDAO = new ConsultationDAOImpl();
    private PatientDAOImpl patientDAO = new PatientDAOImpl();

    @Override
    public void ajouterConsultation(Consultation c) {

        if (!patientDAO.trouverParId(c.getPatient().getId()).isPresent())
            throw new RuntimeException("Patient introuvable !");

        if (c.getPrix() < 0)
            throw new RuntimeException("Le prix de la consultation doit être positif !");

        if (c.getDate().isAfter(LocalDate.now()))
            throw new RuntimeException("La consultation ne peut pas être dans le futur !");

        consultationDAO.ajouter(c);
    }

    @Override
    public Consultation trouverParId(Long id) {
        return consultationDAO.trouverParId(id).orElse(null);
    }

    @Override
    public List<Consultation> trouverParPatient(Long id) {
        return consultationDAO.trouverParPatient(id);
    }

    @Override
    public List<Consultation> trouverTous() {
        return consultationDAO.trouverTous();
    }

    @Override
    public void supprimer(Long id) {
        consultationDAO.supprimer(id);
    }
}
