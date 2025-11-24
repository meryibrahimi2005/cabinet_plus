package ma.cabinetplus.dao;

import ma.cabinetplus.Patient;

public interface PatientDAO extends GenericDAO<Patient, Integer> {
    Patient trouverParNumeroDossier(String numeroDossier);
    Patient trouverParUsername(String username);
}
