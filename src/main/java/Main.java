import ma.cabinetplus.model.Medecin;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.model.RendezVous;
import ma.cabinetplus.model.StatutRendezVous;
import ma.cabinetplus.model.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ma.cabinetplus.model.Consultation;


public class Main {

    // 🔹 Liste de tous les patients du système
    private static List<Patient> patients = new ArrayList<>();
    private static List<RendezVous> rendezVousList = new ArrayList<>();
    // 🔹 Toutes les consultations effectuées
    private static List<Consultation> consultations = new ArrayList<>();

    //    Login  : medecin
    //    Mot de passe : 1234
    private static Medecin medecin = new Medecin(
            "Ahmed",        // nom
            "Fahmi",        // prénom
            "medecin",      // username / login
            "1234"          // password
    );

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== CABINET MEDICAL =====");
            System.out.println("1. Connexion médecin");
            System.out.println("2. Créer un compte patient");
            System.out.println("3. Connexion patient");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            String choixStr = scanner.nextLine();
            int choix;

            try {
                choix = Integer.parseInt(choixStr);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.\n");
                continue;
            }

            switch (choix) {
                case 1:
                    connexionMedecin(scanner);
                    break;
                case 2:
                    creerComptePatient(scanner);
                    break;
                case 3:
                    connexionPatient(scanner);
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    return; // quitter le programme
                default:
                    System.out.println("Choix invalide.\n");
            }
        }
    }

    // ================== PARTIE MEDECIN ==================

    private static void connexionMedecin(Scanner scanner) {
        System.out.println("\n--- Connexion médecin ---");
        System.out.print("Login : ");
        String login = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        if (medecin.getUsername().equals(login)
                && medecin.getPassword().equals(password)) {
            System.out.println("✅ Connexion médecin réussie. Bienvenue Dr "
                    + medecin.getNom() + " " + medecin.getPrenom() + " !");
            menuMedecin(scanner);
        } else {
            System.out.println("❌ Login ou mot de passe incorrect.\n");
        }
    }

    private static void menuMedecin(Scanner scanner) {
        while (true) {
            System.out.println("\n===== MENU MEDECIN =====");
            System.out.println("1. Voir tous les patients");
            System.out.println("2. Voir tous les rendez-vous");
            System.out.println("3. Ajouter une consultation");
            System.out.println("4. Voir toutes les consultations");
            System.out.println("0. Déconnexion");
            System.out.print("Votre choix : ");


            String choixStr = scanner.nextLine();
            int choix;
            try {
                choix = Integer.parseInt(choixStr);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                continue;
            }

            switch (choix) {
                case 1:
                    afficherPatients();
                    break;
                case 2:
                    afficherRendezVous();
                    break;
                case 3:
                    ajouterConsultation(scanner);
                    break;
                case 4:
                    afficherConsultations();
                    break;
                case 0:
                    System.out.println("Déconnexion du médecin.\n");
                    return;
                default:
                    System.out.println("Choix invalide.");
            }

        }
    }
    private static void ajouterConsultation(Scanner scanner) {
        System.out.println("\n--- Nouvelle consultation ---");

        System.out.print("Numéro de dossier du patient : ");
        String numDossier = scanner.nextLine();

        // chercher le patient par numéro de dossier
        Patient patientTrouve = null;
        for (Patient p : patients) {
            if (p.getNumeroDossier().equals(numDossier)) {
                patientTrouve = p;
                break;
            }
        }

        if (patientTrouve == null) {
            System.out.println("❌ Aucun patient trouvé avec ce numéro de dossier.\n");
            return;
        }

        System.out.println("Patient trouvé : " +
                patientTrouve.getNom() + " " + patientTrouve.getPrenom());

        System.out.print("Prix de la consultation : ");
        String prixStr = scanner.nextLine();
        double prix;
        try {
            prix = Double.parseDouble(prixStr);
        } catch (NumberFormatException e) {
            System.out.println("Prix invalide.");
            return;
        }

        System.out.print("Note / rapport de consultation : ");
        String note = scanner.nextLine();

        Long id = (long) (consultations.size() + 1);
        Consultation c = new Consultation(
                id,
                patientTrouve,
                numDossier,
                LocalDate.now(), // date du jour
                prix,
                note
        );

        consultations.add(c);

        System.out.println("✅ Consultation enregistrée avec succès.\n");
    }
    private static void afficherConsultations() {
        System.out.println("\n--- Liste des consultations ---");

        if (consultations.isEmpty()) {
            System.out.println("Aucune consultation enregistrée.");
        } else {
            for (Consultation c : consultations) {
                System.out.println(c);
            }
        }
    }

    // ================== PARTIE PATIENT ==================

    private static void creerComptePatient(Scanner scanner) {
        System.out.println("\n--- Création d'un compte patient ---");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();

        System.out.print("Username (login) : ");
        String username = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        System.out.print("Email : ");
        String email = scanner.nextLine();

        System.out.print("Téléphone : ");
        String telephone = scanner.nextLine();

        System.out.print("Adresse : ");
        String adresse = scanner.nextLine();

        System.out.print("Date de naissance (AAAA-MM-JJ) : ");
        String dateStr = scanner.nextLine();
        LocalDate dateNaissance = LocalDate.parse(dateStr);

        // numéro de dossier auto
        String numeroDossier = "DOS-" + (patients.size() + 1);

        Patient p = new Patient(
                nom,
                prenom,
                username,
                password,
                dateNaissance,
                telephone,
                email,
                adresse,
                numeroDossier
        );

        patients.add(p);

        System.out.println("✅ Compte patient créé avec succès !");
        System.out.println("Votre numéro de dossier : " + numeroDossier + "\n");
    }

    private static void connexionPatient(Scanner scanner) {
        System.out.println("\n--- Connexion patient ---");

        System.out.print("Username : ");
        String username = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        Patient trouve = null;
        for (Patient p : patients) {
            if (p.getUsername().equals(username)
                    && p.getPassword().equals(password)) {
                trouve = p;
                break;
            }
        }

        if (trouve != null) {
            System.out.println("✅ Connexion réussie. Bienvenue "
                    + trouve.getPrenom() + " " + trouve.getNom() + " !");
            menuPatient(scanner, trouve);
        } else {
            System.out.println("❌ Login ou mot de passe invalide.\n");
        }
    }

    private static void menuPatient(Scanner scanner, Patient patient) {
        while (true) {
            System.out.println("\n===== MENU PATIENT =====");
            System.out.println("1. Voir mes informations");
            System.out.println("2. Prendre un rendez-vous");
            System.out.println("0. Déconnexion");
            System.out.print("Votre choix : ");

            String choixStr = scanner.nextLine();
            int choix;
            try {
                choix = Integer.parseInt(choixStr);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                continue;
            }

            switch (choix) {
                case 1:
                    System.out.println("\n--- Vos informations ---");
                    System.out.println(patient);
                    break;
                case 2:
                    prendreRendezVous(scanner, patient);
                    break;
                case 0:
                    System.out.println("Déconnexion du patient.\n");
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
    private static void prendreRendezVous(Scanner scanner, Patient patient) {
        System.out.println("\n--- Prise de rendez-vous ---");

        System.out.print("Date du rendez-vous (AAAA-MM-JJ) : ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.print("Heure du rendez-vous (ex : 14:30) : ");
        String heure = scanner.nextLine();

        System.out.print("Motif du rendez-vous : ");
        String motif = scanner.nextLine();

        Long id = (long) (rendezVousList.size() + 1);

        RendezVous rdv = new RendezVous(
                id,
                date,
                heure,
                motif,
                patient,
                StatutRendezVous.PREVU   // par défaut, prévu
        );

        rendezVousList.add(rdv);

        System.out.println("✅ Rendez-vous enregistré avec succès !");
        System.out.println("Votre numéro de rendez-vous : " + id + "\n");
    }

    // ================== OUTILS COMMUNS ==================

    private static void afficherPatients() {
        System.out.println("\n--- Liste des patients ---");
        if (patients.isEmpty()) {
            System.out.println("Aucun patient enregistré.");
        } else {
            for (Patient p : patients) {
                System.out.println(p);
            }
        }
    }
    private static void afficherRendezVous() {
        System.out.println("\n--- Liste des rendez-vous ---");

        if (rendezVousList.isEmpty()) {
            System.out.println("Aucun rendez-vous enregistré.");
        } else {
            for (RendezVous rdv : rendezVousList) {
                System.out.println(rdv);
            }
        }
    }

}
