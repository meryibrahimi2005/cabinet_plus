package ma.cabinetplus;
import java.time.LocalDate;

public class Patient extends Personne {
    private int id;
    private LocalDate dateNaissance;
    private String telephone;
    private String email;
    private String adresse;
    private String numeroDossier;

    public Patient(String nom, String prenom, String username, String password,
                   LocalDate dateNaissance, String telephone, String email,
                   String adresse, String numeroDossier) {

        super(nom, prenom, username, password, Role.PATIENT);
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.numeroDossier = numeroDossier;
    }

    public int getId() {
        return id;
    }

    public  LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getNumeroDossier() {
        return numeroDossier;
    }
    public String getAdresse() {

        return adresse;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Patient { " +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", username='" + username + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numeroDossier='" + numeroDossier + '\'' +
                '}';
    }
}
