package ma.cabinetplus.dao;

import ma.cabinetplus.model.Medecin;
import java.util.List;

public interface MedecinDAO {
    void ajouter(Medecin medecin);
    Medecin trouverParUsername(String username);
    List<Medecin> trouverTous();
}