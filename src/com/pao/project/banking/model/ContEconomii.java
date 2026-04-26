package com.pao.project.banking.model;

public class ContEconomii extends Cont {
    private double dobandaAnuala;
    private double soldMinim;

    public ContEconomii(String numarCont, double soldInitial, String clientId, double dobandaAnuala, double soldMinim) {
        super(numarCont, soldInitial, clientId);
        this.dobandaAnuala = dobandaAnuala;
        this.soldMinim = soldMinim;
    }

    public double getDobandaAnuala() { return dobandaAnuala; }
    public void setDobandaAnuala(double dobandaAnuala) { this.dobandaAnuala = dobandaAnuala; }
    public double getSoldMinim() { return soldMinim; }
    public void setSoldMinim(double soldMinim) { this.soldMinim = soldMinim; }

    @Override
    public boolean retrage(double suma) {
        if (suma > 0 && sold - suma >= soldMinim) {
            sold -= suma;
            return true;
        }
        return false;
    }

    public void aplicaDobanda() {
        sold += sold * (dobandaAnuala / 100);
    }

    @Override
    public String toString() {
        return "ContEconomii{" +
                "numarCont='" + numarCont + '\'' +
                ", sold=" + sold +
                ", clientId='" + clientId + '\'' +
                ", dobandaAnuala=" + dobandaAnuala +
                ", soldMinim=" + soldMinim +
                '}';
    }
}