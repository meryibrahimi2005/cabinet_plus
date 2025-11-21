package ma.cabinetplus.service;

import ma.cabinetplus.dao.PatientDAO;
import ma.cabinetplus.dao.PatientDAOImpl;
import ma.cabinetplus.model.Patient;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public void ajouter(Patient patient) {
        patientDAO.ajouter(patient);
    }

    @Override
    public void supprimer(Integer id) {
        patientDAO.supprimer(id);
    }

    @Override
    public Patient trouverParId(Integer id) {
        return patientDAO.trouverParId(id);
    }

    @Override
    public List<Patient> trouverTous() {
        return patientDAO.trouverTous();
    }

    @Override
    public Patient trouverParNumeroDossier(String numeroDossier) {
        return patientDAO.trouverParNumeroDossier(numeroDossier);
    }

    @Override
    public Patient trouverParUsername(String username) {
        return patientDAO.trouverParUsername(username);
    }
}
