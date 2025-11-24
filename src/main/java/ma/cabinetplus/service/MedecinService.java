package ma.cabinetplus.service;

import ma.cabinetplus.Medecin;

public interface MedecinService extends GenericService<Medecin, Integer> {
    Medecin trouverParUsername(String username);
}
