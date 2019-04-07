package br.edu.utfpr.cp.emater.midmipsystem.library;

import org.springframework.ui.Model;

public abstract class AbstractCRUDController<T> {
    
    public abstract String create (T anEntity, Model someData);
    public abstract String readAll(Model someData);    
    public abstract String update (T anEntity);
    public abstract void delete (int aId);
    public abstract String goNew ();
    
    
    protected boolean operationSuccessMessage;
            
    protected void resetOperationSuccessMessage() {
        if (this.operationSuccessMessage)
            this.operationSuccessMessage = false;
    }

    protected void setOperationSuccessMessage() {
        this.operationSuccessMessage = true;
    }
}
