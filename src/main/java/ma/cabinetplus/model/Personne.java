package ma.cabinetplus.model;
import ma.cabinetplus.model.Role;

public class Personne {

    protected String nom;
    protected String prenom;
    protected String username;
    protected String password;
    protected Role role;

    public Personne(String nom, String prenom,
                    String username, String password,
                    Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
