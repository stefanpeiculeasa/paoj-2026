package com.pao.project.banking.model;

public class Adresa {
    private String strada;
    private String oras;
    private String codPostal;

    public Adresa(String strada, String oras, String codPostal) {
        this.strada = strada;
        this.oras = oras;
        this.codPostal = codPostal;
    }

    public String getStrada() { return strada; }
    public void setStrada(String strada) { this.strada = strada; }
    public String getOras() { return oras; }
    public void setOras(String oras) { this.oras = oras; }
    public String getCodPostal() { return codPostal; }
    public void setCodPostal(String codPostal) { this.codPostal = codPostal; }

    @Override
    public String toString() {
        return strada + ", " + oras + ", " + codPostal;
    }
}