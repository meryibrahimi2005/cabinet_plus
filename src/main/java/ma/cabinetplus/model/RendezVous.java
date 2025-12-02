package ma.cabinetplus.model;

import java.time.LocalDateTime;

public class RendezVous {

    private Long id;
    private LocalDateTime dateHeureRendezVous;
    private String motif;
    private Patient patient;
    private StatutRendezVous statut;

    public RendezVous(Long id, LocalDateTime dateHeureRendezVous, String motif,
                      Patient patient, StatutRendezVous statut) {
        this.id = id;
        this.dateHeureRendezVous = dateHeureRendezVous;
        this.motif = motif;
        this.patient = patient;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateHeureRendezVous() {
        return dateHeureRendezVous;
    }

    public String getMotif() {
        return motif;
    }

    public Patient getPatient() {
        return patient;
    }

    public StatutRendezVous getStatut() {
        return statut;
    }

    public void setStatut(StatutRendezVous statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", dateHeureRendezVous=" + dateHeureRendezVous +
                ", motif='" + motif + '\'' +
                ", patient=" + patient.getNom() + " " + patient.getPrenom() +
                ", statut=" + statut +
                '}';
    }
}
