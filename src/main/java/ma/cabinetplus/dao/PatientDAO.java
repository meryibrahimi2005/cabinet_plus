package ma.cabinetplus.dao;

import ma.cabinetplus.model.Patient;

public interface PatientDAO extends GenericDAO<Patient, Long> {
    Patient trouverParNumeroDossier(String numeroDossier);
    Patient trouverParUsername(String username);
}
