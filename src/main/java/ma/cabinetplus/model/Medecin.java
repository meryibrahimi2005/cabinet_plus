package ma.cabinetplus.model;

public class Medecin extends Personne {
    private int id;
    public Medecin(int id ,String nom, String prenom,
                   String username, String password) {
        super(nom, prenom, username, password, Role.MEDECIN);
        this.id=id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id ){
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
