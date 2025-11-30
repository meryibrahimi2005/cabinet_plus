package ma.cabinetplus.model;

public class Medecin extends Personne {
    private Long id;
    public Medecin(Long id ,String nom, String prenom,
                   String username, String password) {
        super(nom, prenom, username, password, Role.MEDECIN);
        this.id=id;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id ){
        this.id=id;
    }

    @Override
    public String toString() {
        return "Medecin{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
