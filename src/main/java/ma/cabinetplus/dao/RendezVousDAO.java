package ma.cabinetplus.dao;

import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import java.util.List;

public interface RendezVousDAO {
    void ajouter(RendezVous rdv);
    void mettreAJourStatut(Long id, StatutRendezVous statut);
    List<RendezVous> trouverParPatient(int patientId);
    List<RendezVous> trouverTous();
    RendezVous trouverParId(Long id);
}