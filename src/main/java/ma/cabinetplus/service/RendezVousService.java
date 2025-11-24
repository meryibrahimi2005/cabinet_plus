package ma.cabinetplus.service;

import ma.cabinetplus.RendezVous;
import ma.cabinetplus.StatutRendezVous;
import java.util.List;

public interface RendezVousService extends GenericService<RendezVous, Long> {
    void mettreAJourStatut(Long id, StatutRendezVous statut);
    List<RendezVous> trouverParPatient(int patientId);
}
