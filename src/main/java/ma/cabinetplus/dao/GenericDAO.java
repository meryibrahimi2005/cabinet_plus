package ma.cabinetplus.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    void ajouter(T entity);
    void supprimer(ID id);
    Optional<T> trouverParId(ID id);
    List<T> trouverTous();
    void mettreAJour(T entity);
}