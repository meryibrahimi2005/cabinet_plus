package ma.cabinetplus.service;

import ma.cabinetplus.dao.MedecinDAO;
import ma.cabinetplus.dao.MedecinDAOImpl;
import ma.cabinetplus.model.Medecin;
import java.util.List;

public class MedecinServiceImpl implements MedecinService {

    private MedecinDAO medecinDAO = new MedecinDAOImpl();

    @Override
    public void ajouter(Medecin medecin) {
        medecinDAO.ajouter(medecin);
    }

    @Override
    public void supprimer(Integer id) {
        // Optionnel: ajouter méthode supprimer dans MedecinDAO si nécessaire
    }

    @Override
    public Medecin trouverParId(Integer id) {
        // Optionnel: implémenter dans DAO si nécessaire
        return null;
    }

    @Override
    public List<Medecin> trouverTous() {
        return medecinDAO.trouverTous();
    }

    @Override
    public Medecin trouverParUsername(String username) {
        return medecinDAO.trouverParUsername(username);
    }
}
