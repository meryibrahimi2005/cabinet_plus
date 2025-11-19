package ma.cabinetplus.model;

import java.time.LocalDate;

public class RendezVous {

    private Long id;
    private LocalDate date;
    private String heure;          // ex : "14:30"
    private String motif;
    private Patient patient;
    private StatutRendezVous statut;

    public RendezVous(Long id, LocalDate date, String heure,
                      String motif, Patient patient, StatutRendezVous statut) {
        this.id = id;
        this.date = date;
        this.heure = heure;
        this.motif = motif;
        this.patient = patient;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
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
                ", date=" + date +
                ", heure='" + heure + '\'' +
                ", motif='" + motif + '\'' +
                ", patient=" + patient.getNom() + " " + patient.getPrenom() +
                ", statut=" + statut +
                '}';
    }
}
