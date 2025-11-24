package ma.cabinetplus.dao;

import ma.cabinetplus.Medecin;

public interface MedecinDAO extends GenericDAO<Medecin, Integer> {
    Medecin trouverParUsername(String username);
}