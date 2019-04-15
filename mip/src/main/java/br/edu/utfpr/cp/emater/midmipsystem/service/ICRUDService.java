package br.edu.utfpr.cp.emater.midmipsystem.service;


import java.util.List;

public interface ICRUDService<T> {
    
    public List<T> readAll();
    
}
