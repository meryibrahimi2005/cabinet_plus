package ma.cabinetplus;
import java.time.LocalDate;

public class Consultation {

    private Long id;
    private Patient patient;
    private String numeroDossier;
    private LocalDate date;
    private double prix;
    private String note;
    private StatutRendezVous statut;

    public Consultation(Long id, Patient patient, String numeroDossier, LocalDate date, double prix, String note) {
        this.id = id;
        this.patient = patient;
        this.numeroDossier = numeroDossier;
        this.date = date;
        this.prix = prix;
        this.note = note;
    }


    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getPrix() {
        return prix;
    }

    public String getNote() {
        return note;
    }
    public StatutRendezVous getStatut() {
        return statut;
    }

    public void setStatut(StatutRendezVous statut) {
        this.statut = statut;
    }


    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", date=" + date +
                ", prix=" + prix +
                ", patient=" + patient.getNom() + " " + patient.getPrenom() +
                ", numeroDossier='" + numeroDossier + '\'' +
                ", note='" + note + '\'' +
                ", statut=" + statut +
                '}';
    }

}
