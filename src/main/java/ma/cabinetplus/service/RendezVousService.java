package ma.cabinetplus.service;

import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;

import java.util.List;

public interface RendezVousService {
    void ajouterRendezVous(RendezVous rdv);
    void changerStatut(Long id, StatutRendezVous statut);
    RendezVous trouverParId(Long id);
    List<RendezVous> trouverParPatient(Long id);
    List<RendezVous> trouverTous();
}
