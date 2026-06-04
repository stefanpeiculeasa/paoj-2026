package com.pao.project.banking.model;

public class ContCurent extends Cont {
    private double limitaDescoperire;

    public ContCurent(String numarCont, double soldInitial, String clientId, double limitaDescoperire) {
        super(numarCont, soldInitial, clientId);
        this.limitaDescoperire = limitaDescoperire;
    }

    public double getLimitaDescoperire() { return limitaDescoperire; }
    public void setLimitaDescoperire(double limitaDescoperire) { this.limitaDescoperire = limitaDescoperire; }

    @Override
    public boolean retrage(double suma) {
        if (suma > 0 && sold - suma >= -limitaDescoperire) {
            sold -= suma;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ContCurent{" +
                "numarCont='" + numarCont + '\'' +
                ", sold=" + sold +
                ", clientId='" + clientId + '\'' +
                ", limitaDescoperire=" + limitaDescoperire +
                '}';
    }
}