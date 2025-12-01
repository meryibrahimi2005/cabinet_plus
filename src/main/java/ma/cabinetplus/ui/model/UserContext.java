package ma.cabinetplus.ui.model;

import ma.cabinetplus.model.Personne;

/**
 * Contexte utilisateur pour tracker l'utilisateur connecté
 */
public class UserContext {
    private static UserContext instance;

    private Personne currentUser;
    private UserRole role;

    private UserContext() {}

    public static UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }

    public void setCurrentUser(Personne user, UserRole role) {
        this.currentUser = user;
        this.role = role;
    }

    public Personne getCurrentUser() {
        return currentUser;
    }

    public UserRole getRole() {
        return role;
    }

    //deconnecter user
    public void logout() {
        this.currentUser = null;
        this.role = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
