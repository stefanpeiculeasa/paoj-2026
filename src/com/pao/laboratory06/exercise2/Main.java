package com.pao.laboratory06.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        List<Colaborator> colaboratori = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!scanner.hasNext()) break;
            String tip = scanner.next();
            Colaborator c = null;
            if (tip.equals("CIM")) {
                c = new CIMColaborator();
            } else if (tip.equals("PFA")) {
                c = new PFAColaborator();
            } else if (tip.equals("SRL")) {
                c = new SRLColaborator();
            }
            if (c != null) {
                c.citeste(scanner);
                colaboratori.add(c);
            }
        }

        colaboratori.sort((c1, c2) -> Double.compare(c2.calculeazaVenitNetAnual(), c1.calculeazaVenitNetAnual()));

        for (Colaborator c : colaboratori) {
            c.afiseaza();
        }
        System.out.println();

        if (!colaboratori.isEmpty()) {
            System.out.print("Colaborator cu venit net maxim: ");
            colaboratori.get(0).afiseaza();
            System.out.println();
        }

        System.out.println("Colaboratori persoane juridice:");
        for (Colaborator c : colaboratori) {
            if (c instanceof PersoanaJuridica) {
                c.afiseaza();
            }
        }
        System.out.println();

        System.out.println("Sume și numar colaboratori pe tip:");
        for (TipColaborator tip : TipColaborator.values()) {
            double suma = 0;
            int count = 0;
            for (Colaborator c : colaboratori) {
                if (c.tipContract().equals(tip.name())) {
                    suma += c.calculeazaVenitNetAnual();
                    count++;
                }
            }
            if (count > 0 || tip == TipColaborator.CIM || tip == TipColaborator.PFA || tip == TipColaborator.SRL) {
                System.out.printf("%s: suma = %.2f lei, numar = %d\n", tip.name(), suma, count);
            }
        }
        scanner.close();
    }
}
