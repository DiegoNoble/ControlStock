
package com.Beans;


public enum TipoDocumentoEnum {

    CONTADO("Contado"),CREDITO("Crédito"),NOTA_CREDITO("Nota de Crédito");
    private String description;
    
    TipoDocumentoEnum(String description) {
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
