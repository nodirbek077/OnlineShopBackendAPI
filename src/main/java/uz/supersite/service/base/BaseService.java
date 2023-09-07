package uz.supersite.service.base;

import uz.supersite.ResponseEntity;

import java.util.List;

public interface BaseService<T, B> {
     T add(T t);
     List<T> getAll();

     T getById(B id);

     T update(T t, B id);

     void delete(B id);
}
