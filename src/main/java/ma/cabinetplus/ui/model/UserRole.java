package ma.cabinetplus.ui.model;

/**
 * Rôle utilisateur pour l'authentification
 */
public enum UserRole {
    MEDECIN("Médecin"),
    PATIENT("Patient");

    private String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
