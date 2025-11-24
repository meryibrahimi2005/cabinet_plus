package ma.cabinetplus.service;

import ma.cabinetplus.Patient;

public interface PatientService extends GenericService<Patient, Integer> {
    Patient trouverParNumeroDossier(String numeroDossier);
    Patient trouverParUsername(String username);
}
