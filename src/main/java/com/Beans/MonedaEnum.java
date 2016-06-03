
package com.Beans;


public enum MonedaEnum {

    PESOS("Pesos"),DOLARES("Dólares"),REALES("Reales");
    private String description;
    
    MonedaEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
