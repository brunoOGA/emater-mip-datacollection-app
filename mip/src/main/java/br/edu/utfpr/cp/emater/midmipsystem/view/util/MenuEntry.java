package br.edu.utfpr.cp.emater.midmipsystem.view.util;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuEntry implements Serializable {
    
    private String title;
    private String url;

    private MenuEntry(){}
    
    @Builder
    public static MenuEntry create(String title, String url){
        
        var menuEntry = new MenuEntry();
        menuEntry.title = title;
        menuEntry.url = url;
        
        return menuEntry;
    }
}
