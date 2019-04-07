package br.edu.utfpr.cp.emater.midmipsystem.library.controllers;

import org.springframework.ui.Model;

interface Read<T> {
    
    public String readAll(Model someData);
    
}
