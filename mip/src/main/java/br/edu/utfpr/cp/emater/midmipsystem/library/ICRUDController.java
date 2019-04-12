package br.edu.utfpr.cp.emater.midmipsystem.library;

import java.util.List;

public interface ICRUDController<T> {
    
    public List<T> readAll();
    public String create();
    public String prepareUpdate (Long id);
    public String update();
    
}
