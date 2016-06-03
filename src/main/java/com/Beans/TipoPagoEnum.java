/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Beans;


public enum TipoPagoEnum {
    
    TRANSFERENCIA("Transferencia"),CHEQUE("Cheque"),EFECTIVO("Efectivo");
    private String description;
    
    TipoPagoEnum(String description) {
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
