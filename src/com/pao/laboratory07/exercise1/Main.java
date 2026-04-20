package com.pao.laboratory07.exercise1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StareComanda stare = StareComanda.valueOf(scanner.nextLine().trim());
        System.out.println(stare);

        Deque<StareComanda> istoric = new ArrayDeque<>();
        boolean finalAtins = false;

        while (scanner.hasNextLine()) {
            String linie = scanner.nextLine().trim();

            if (linie.equals("QUIT")) {
                if (finalAtins) {
                    System.out.println("Comanda este in stare finala.");
                }
                break;
            }

            switch (linie) {
                case "next" -> {
                    if (stare.esteFinala()) {
                        System.out.println("Comanda este in stare finala.");
                    } else {
                        istoric.push(stare);
                        stare = stare.urmatoarea();
                        System.out.println(stare);
                        if (stare.esteFinala()) finalAtins = true;
                    }
                }
                case "cancel" -> {
                    if (stare.esteFinala()) {
                        System.out.println("Comanda este in stare finala.");
                    } else {
                        istoric.push(stare);
                        stare = StareComanda.ANULATA;
                        System.out.println(stare);
                        finalAtins = true;
                    }
                }
                case "undo" -> {
                    if (!istoric.isEmpty()) {
                        stare = istoric.pop();
                    }
                    finalAtins = stare.esteFinala();
                    System.out.println(stare);
                }
            }
        }

        scanner.close();
    }
}