package ma.cabinetplus.service;

import ma.cabinetplus.dao.MedecinDAO;
import ma.cabinetplus.dao.MedecinDAOImpl;
import ma.cabinetplus.model.Medecin;
import ma.cabinetplus.service.MedecinService;

import java.util.List;

public class MedecinServiceImpl implements MedecinService {

    private MedecinDAO medecinDAO = new MedecinDAOImpl();

    @Override
    public void ajouterMedecin(Medecin medecin) {
        // Vérifier l'unicité du username
        if (medecinDAO.trouverParUsername(medecin.getUsername()) != null) {
            throw new RuntimeException("Username déjà utilisé !");
        }

        medecinDAO.ajouter(medecin);
    }

    @Override
    public void supprimerMedecin(Long id) {
        if (medecinDAO.trouverParId(id) == null) {
            throw new RuntimeException("Médecin inexistant !");
        }
        medecinDAO.supprimer(id);
    }

    @Override
    public Medecin trouverParId(Long id) {
        return medecinDAO.trouverParId(id);
    }

    @Override
    public Medecin trouverParUsername(String username) {
        return medecinDAO.trouverParUsername(username);
    }

    @Override
    public List<Medecin> trouverTous() {
        return medecinDAO.trouverTous();
    }
}
