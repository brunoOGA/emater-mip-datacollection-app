package br.edu.utfpr.cp.emater.midmipsystem.library.controllers;

import org.springframework.ui.Model;

public abstract class AbstractCRUDController<T> {
    
    protected Create create;
    protected Read read;
    protected Update update;
    protected Delete delete;
    
    public abstract String create (T anEntity);
    public abstract String readAll(Model someData);    
    public abstract String update (T anEntity);
    public abstract void delete (int aId);
    
    
    protected boolean operationSuccessMessage;
            
    protected void resetOperationSuccessMessage() {
        if (this.operationSuccessMessage)
            this.operationSuccessMessage = false;
    }

    protected void setOperationSuccessMessage() {
        this.operationSuccessMessage = true;
    }
}
