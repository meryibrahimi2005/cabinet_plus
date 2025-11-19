package ma.cabinetplus.model;

public class Medecin extends Personne {

    public Medecin(String nom, String prenom,
                   String username, String password) {
        super(nom, prenom, username, password, Role.MEDECIN);
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
