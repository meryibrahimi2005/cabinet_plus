package ma.cabinetplus.dao;

import ma.cabinetplus.model.Medecin;
import java.util.List;

public interface MedecinDAO extends GenericDAO<Medecin, Long> {
    Medecin trouverParUsername(String username);
}