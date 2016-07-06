package com.Beans;

public enum SituacionReparto {

    EN_CURSO("En curso"), FINALIZADO("Finalizado");
    private String description;

    SituacionReparto(String description) {
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
