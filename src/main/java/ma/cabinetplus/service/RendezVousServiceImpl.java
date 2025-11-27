package ma.cabinetplus.service;
import ma.cabinetplus.dao.PatientDAOImpl;
import ma.cabinetplus.dao.RendezVousDAO;
import ma.cabinetplus.dao.RendezVousDAOImpl;
import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import ma.cabinetplus.service.RendezVousService;

import java.time.LocalDate;
import java.util.List;

public class RendezVousServiceImpl implements RendezVousService {

    private RendezVousDAO rdvDAO = new RendezVousDAOImpl();
    private PatientDAOImpl patientDAO = new PatientDAOImpl();

    @Override
    public void ajouterRendezVous(RendezVous rdv) {

        if (patientDAO.trouverParId(rdv.getPatient().getId()) == null)
            throw new RuntimeException("Patient introuvable !");

        if (rdv.getDate().isBefore(LocalDate.now()))
            throw new RuntimeException("Impossible d’ajouter un RDV dans le passé !");

        // vérifier si le patient a un rdv au même moment
        List<RendezVous> existants = rdvDAO.trouverParPatient(rdv.getPatient().getId());
        for (RendezVous r : existants) {
            if (r.getDate().equals(rdv.getDate()) && r.getHeure().equals(rdv.getHeure())) {
                throw new RuntimeException("Le patient a déjà un rdv à la même heure !");
            }
        }

        rdvDAO.ajouter(rdv);
    }

    @Override
    public void changerStatut(Long id, StatutRendezVous statut) {
        RendezVous r = rdvDAO.trouverParId(id);

        if (r == null)
            throw new RuntimeException("RDV introuvable !");
        if (r.getStatut() == StatutRendezVous.TERMINE)
            throw new RuntimeException("Impossible de modifier un RDV terminé !");

        rdvDAO.mettreAJourStatut(id, statut);
    }

    @Override
    public RendezVous trouverParId(Long id) {
        return rdvDAO.trouverParId(id);
    }

    @Override
    public List<RendezVous> trouverParPatient(int id) {
        return rdvDAO.trouverParPatient(id);
    }

    @Override
    public List<RendezVous> trouverTous() {
        return rdvDAO.trouverTous();
    }
}
