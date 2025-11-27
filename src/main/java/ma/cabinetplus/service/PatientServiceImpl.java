package ma.cabinetplus.service;

import ma.cabinetplus.dao.PatientDAO;
import ma.cabinetplus.dao.PatientDAOImpl;
import ma.cabinetplus.model.Patient;
import ma.cabinetplus.service.PatientService;

import java.time.LocalDate;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public void ajouterPatient(Patient patient) {

        if (patientDAO.trouverParUsername(patient.getUsername()) != null)
            throw new RuntimeException("Username déjà utilisé !");

        if (patientDAO.trouverParNumeroDossier(patient.getNumeroDossier()) != null)
            throw new RuntimeException("Numéro de dossier déjà existant !");

        if (patient.getDateNaissance().isAfter(LocalDate.now()))
            throw new RuntimeException("La date de naissance est invalide.");

        patientDAO.ajouter(patient);
    }

    @Override
    public void supprimerPatient(int id) {
        if (patientDAO.trouverParId(id) == null)
            throw new RuntimeException("Patient introuvable");
        patientDAO.supprimer(id);
    }

    @Override
    public Patient trouverParId(int id) {
        return patientDAO.trouverParId(id);
    }

    @Override
    public Patient trouverParUsername(String username) {
        return patientDAO.trouverParUsername(username);
    }

    @Override
    public Patient trouverParNumeroDossier(String numero) {
        return patientDAO.trouverParNumeroDossier(numero);
    }

    @Override
    public List<Patient> trouverTous() {
        return patientDAO.trouverTous();
    }
}
