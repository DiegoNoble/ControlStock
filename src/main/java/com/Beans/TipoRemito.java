
package com.Beans;


public enum TipoRemito {

    REMITO("Remito"),CONTRA_REMITO("Contra remito");
    private String description;
    
    TipoRemito(String description) {
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
