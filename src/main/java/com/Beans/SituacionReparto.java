package com.Beans;

public enum SituacionReparto {

    EN_CURSO("En curso"), FINALIZAdo("Finalizado");
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
