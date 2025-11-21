package ma.cabinetplus.dao;

import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import java.util.List;

public interface RendezVousDAO extends GenericDAO<RendezVous, Long> {
    void mettreAJourStatut(Long id, StatutRendezVous statut);
    List<RendezVous> trouverParPatient(int patientId);
}