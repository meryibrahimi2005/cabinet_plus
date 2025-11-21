package ma.cabinetplus.dao;

import ma.cabinetplus.model.Patient;
import java.util.List;

public interface PatientDAO {
    void ajouter(Patient patient);
    void mettreAJour(Patient patient);
    void supprimer(int id);
    Patient trouverParId(int id);
    Patient trouverParUsername(String username);
    List<Patient> trouverTous();
}