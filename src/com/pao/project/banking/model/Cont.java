package com.pao.project.banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Cont {
    protected String numarCont;
    protected IBANCod ibanCod;
    protected double sold;
    protected String clientId;
    protected LocalDate dataDeschidere;
    protected List<Tranzactie> tranzactii;

    public Cont(String numarCont, double soldInitial, String clientId) {
        this.ibanCod = IBANCod.din(numarCont);
        this.numarCont = this.ibanCod.getIBANComplet();
        this.sold = soldInitial;
        this.clientId = clientId;
        this.dataDeschidere = LocalDate.now();
        this.tranzactii = new ArrayList<>();
    }

    public String getNumarCont() { return numarCont; }
    public IBANCod getIbanCod() { return ibanCod; }
    public double getSold() { return sold; }
    public String getClientId() { return clientId; }
    public LocalDate getDataDeschidere() { return dataDeschidere; }
    public List<Tranzactie> getTranzactii() { return tranzactii; }

    public void adaugaTranzactie(Tranzactie t) {
        tranzactii.add(t);
    }

    public abstract boolean retrage(double suma);

    public void depune(double suma) {
        if (suma > 0) {
            sold += suma;
        }
    }

    @Override
    public String toString() {
        return "Cont{" +
                "numarCont='" + numarCont + '\'' +
                ", sold=" + sold +
                ", clientId='" + clientId + '\'' +
                ", dataDeschidere=" + dataDeschidere +
                '}';
    }
}