package com.Beans;

public enum CondicionImpositiva {

    RESP_INSCRIPTO("Resp. Inscripto"), MONOTRIBUTISTA("Monotibutista"), CONS_FINAL("Consumidor final");
    private String description;

    CondicionImpositiva(String description) {
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
