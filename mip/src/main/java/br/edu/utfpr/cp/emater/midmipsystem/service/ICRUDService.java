package br.edu.utfpr.cp.emater.midmipsystem.service;


import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import java.util.List;

public interface ICRUDService<T> {
    
    public List<T> readAll();
    public void create(T entity) throws EntityAlreadyExistsException, AnyPersistenceException, EntityNotFoundException;
    
}
