
package com.Beans;


public enum EstadoEnum {

    PENDIENTE("Pendiente"),PAGO("Pago"),PARCIAL("Parcialmente pago");
    private String description;
    
    EstadoEnum(String description) {
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
