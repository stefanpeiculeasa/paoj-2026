package com.pao.laboratory07.exercise2;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim());

        List<Comanda> comenzi = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] parts = scanner.nextLine().trim().split(" ");
            Comanda c = switch (parts[0]) {
                case "STANDARD"   -> new ComandaStandard(parts[1], Double.parseDouble(parts[2]));
                case "DISCOUNTED" -> new ComandaRedusa(parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]));
                case "GIFT"       -> new ComandaGratuita(parts[1]);
                default -> throw new IllegalArgumentException("Tip necunoscut: " + parts[0]);
            };
            comenzi.add(c);
        }

        for (Comanda c : comenzi) {
            System.out.println(c.descriere());
        }

        System.out.println();
        System.out.println("Statistici:");

        String[] tipuri = {"STANDARD", "DISCOUNTED", "GIFT"};
        double total = 0;
        for (String tip : tipuri) {
            double suma = 0;
            int numar = 0;
            for (Comanda c : comenzi) {
                String t = (c instanceof ComandaStandard) ? "STANDARD"
                        : (c instanceof ComandaRedusa)   ? "DISCOUNTED"
                        : "GIFT";
                if (t.equals(tip)) {
                    suma += c.pretFinal();
                    numar++;
                }
            }
            if (numar > 0) {
                System.out.printf("%s: suma = %.2f lei, numar = %d%n", tip, suma, numar);
                total += suma;
            }
        }
        System.out.printf("Total platit: %.2f lei%n", total);
    }
}