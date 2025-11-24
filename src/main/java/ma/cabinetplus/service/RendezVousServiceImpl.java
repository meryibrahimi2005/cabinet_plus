package ma.cabinetplus.service;

import ma.cabinetplus.dao.RendezVousDAO;
import ma.cabinetplus.dao.RendezVousDAOImpl;
import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;

import java.util.List;

public class RendezVousServiceImpl implements RendezVousService {

    private RendezVousDAO rendezVousDAO = new RendezVousDAOImpl();

    @Override
    public void ajouter(RendezVous rdv) {
        rendezVousDAO.ajouter(rdv);
    }

    @Override
    public void supprimer(Long id) {
        // Optionnel: ajouter méthode supprimer dans DAO si nécessaire
    }

    @Override
    public RendezVous trouverParId(Long id) {
        return rendezVousDAO.trouverParId(id);
    }

    @Override
    public List<RendezVous> trouverTous() {
        return rendezVousDAO.trouverTous();
    }

    @Override
    public void mettreAJourStatut(Long id, StatutRendezVous statut) {
        rendezVousDAO.mettreAJourStatut(id, statut);
    }

    @Override
    public List<RendezVous> trouverParPatient(int patientId) {
        return rendezVousDAO.trouverParPatient(patientId);
    }
}
