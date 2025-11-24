package ma.cabinetplus.service;

import ma.cabinetplus.model.Medecin;

public interface MedecinService extends GenericService<Medecin, Integer> {
    Medecin trouverParUsername(String username);
}
