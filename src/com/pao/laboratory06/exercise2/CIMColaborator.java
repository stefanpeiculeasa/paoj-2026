package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean bonus;

    public CIMColaborator() {
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNetAnual = venitBrutLunar * 12 * 0.55;
        if (areBonus()) {
            venitNetAnual += venitNetAnual * 0.10;
        }
        return venitNetAnual;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        
        String b = in.hasNext() ? in.next() : "NU";
        if (b.equals("DA") || b.equals("NU")) {
            this.bonus = b.equals("DA");
        } else {
            this.bonus = false;
        }
    }

    @Override
    public void afiseaza() {
        System.out.printf("CIM: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return TipColaborator.CIM.name();
    }

    @Override
    public boolean areBonus() {
        return this.bonus;
    }
}
