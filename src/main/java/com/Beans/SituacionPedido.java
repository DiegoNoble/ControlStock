package com.Beans;

public enum SituacionPedido {

    NUEVO("Nuevo"), ATENDIDO("Atendido"), ATENTIDO_PARCIAL("Atentido parcialmente"), FACTURADO("Facturado"), CON_REMITO_ANULADO("Posee remito anulado");
    private String description;

    SituacionPedido(String description) {
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
