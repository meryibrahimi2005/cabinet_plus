package ma.cabinetplus.service;

import ma.cabinetplus.model.Patient;
import java.util.List;

public interface PatientService {
    void ajouterPatient(Patient patient);
    void supprimerPatient(int id);
    Patient trouverParId(int id);
    Patient trouverParUsername(String username);
    Patient trouverParNumeroDossier(String numero);
    List<Patient> trouverTous();
}
