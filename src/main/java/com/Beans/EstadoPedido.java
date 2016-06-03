package com.Beans;

public enum EstadoPedido {

    NUEVO("Nuevo"), ATENDIDO("Atendido"), ATENTIDO_PARCIAL("Atentido parcialmente"), FACTURADO("Facturado");
    private String description;

    EstadoPedido(String description) {
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
