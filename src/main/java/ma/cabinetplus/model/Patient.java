package ma.cabinetplus.model;
import java.time.LocalDate;

public class Patient extends Personne {
    private Long id;
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

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
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
