/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Beans;


public enum SituacionArticuloEnum {
    
    ACTIVO("Activo"),INACTIVO("Inactivo");
    private String description;
    
    SituacionArticuloEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
    
}
