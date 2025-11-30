package ma.cabinetplus.service;

import ma.cabinetplus.model.Medecin;
import java.util.List;

public interface MedecinService {
    void ajouterMedecin(Medecin medecin);
    void supprimerMedecin(Long id);
    Medecin trouverParId(Long id);
    Medecin trouverParUsername(String username);
    List<Medecin> trouverTous();
}
