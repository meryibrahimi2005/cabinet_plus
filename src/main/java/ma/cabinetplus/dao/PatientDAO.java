package ma.cabinetplus.dao;

import ma.cabinetplus.model.Patient;

public interface PatientDAO extends GenericDAO<Patient, Integer> {
    Patient trouverParNumeroDossier(String numeroDossier);
    Patient trouverParUsername(String username);
<<<<<<< HEAD
=======
    List<Patient> trouverTous();
    Patient trouverParNumeroDossier(String numeroDossier);
>>>>>>> 0aeb5b6 (couche service)
}