package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;

    public PFAColaborator() {
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double salMinBrutAnual = 4050 * 12; // 48600
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;

        double impozit = 0.10 * venitNet;

        double cass = 0;
        if (venitNet < 6 * salMinBrutAnual) {
            cass = 0.10 * (6 * salMinBrutAnual);
        } else if (venitNet <= 72 * salMinBrutAnual) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * salMinBrutAnual);
        }

        double cas = 0;
        if (venitNet >= 12 * salMinBrutAnual && venitNet <= 24 * salMinBrutAnual) {
            cas = 0.25 * (12 * salMinBrutAnual);
        } else if (venitNet > 24 * salMinBrutAnual) {
            cas = 0.25 * (24 * salMinBrutAnual);
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public void afiseaza() {
        System.out.printf("PFA: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return TipColaborator.PFA.name();
    }
}
