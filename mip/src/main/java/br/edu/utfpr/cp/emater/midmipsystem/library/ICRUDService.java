package br.edu.utfpr.cp.emater.midmipsystem.library;


import java.util.List;

public interface ICRUDService<T> {
    
    public List<T> readAll();
    
}
