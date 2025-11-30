package ma.cabinetplus.dao;

import ma.cabinetplus.model.Medecin;
import java.util.List;
import java.util.Optional;

public interface MedecinDAO extends GenericDAO<Medecin, Long> {
    Optional<Medecin> trouverParUsername(String username);
}