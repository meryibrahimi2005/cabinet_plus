package ma.cabinetplus.service;

import java.util.List;

public interface GenericService<T, ID> {
    void ajouter(T entity);
    void supprimer(ID id);
    T trouverParId(ID id);
    List<T> trouverTous();
}
