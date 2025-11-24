package ma.cabinetplus.dao;

import ma.cabinetplus.model.Medecin;
import java.util.List;

public interface MedecinDAO extends GenericDAO<Medecin, Integer> {
    Medecin trouverParUsername(String username);
}