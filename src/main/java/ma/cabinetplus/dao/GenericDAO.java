package ma.cabinetplus.dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    void ajouter(T entity);
    void supprimer(ID id);
    T trouverParId(ID id);
    List<T> trouverTous();
}