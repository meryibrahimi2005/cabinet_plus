package ma.cabinetplus.dao;

import ma.cabinetplus.model.Patient;
import java.util.Optional;

public interface PatientDAO extends GenericDAO<Patient, Long> {
    Optional<Patient> trouverParNumeroDossier(String numeroDossier);
    Optional<Patient> trouverParUsername(String username);
}
