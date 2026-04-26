package com.pao.project.banking;

import com.pao.project.banking.exception.ContInexistentException;
import com.pao.project.banking.exception.FonduriInsuficienteException;
import com.pao.project.banking.exception.ClientInexistentException;
import com.pao.project.banking.model.*;
import com.pao.project.banking.service.CardService;
import com.pao.project.banking.service.ClientService;
import com.pao.project.banking.service.ContService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final ClientService clientService = ClientService.getInstance();
    private static final ContService contService = ContService.getInstance();
    private static final CardService cardService = CardService.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();

            if (input.equals("1")) {
                creeazaClient();
            } else if (input.equals("2")) {
                deschideCont();
            } else if (input.equals("3")) {
                depunere();
            } else if (input.equals("4")) {
                retragere();
            } else if (input.equals("5")) {
                transfer();
            } else if (input.equals("6")) {
                veziSold();
            } else if (input.equals("7")) {
                emiteCard();
            } else if (input.equals("8")) {
                toggleCard();
            } else if (input.equals("9")) {
                genereazaExtras();
            } else if (input.equals("10")) {
                listaConturiClient();
            } else if (input.equals("11")) {
                cautaClient();
            } else if (input.equals("12")) {
                stergeContMenu();
            } else if (input.equals("0")) {
                System.out.println("La revedere!");
                running = false;
            } else {
                System.out.println("[!] Optiune invalida. Incercati din nou.\n");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("""
            1. Creeaza un client nou
            2. Deschide un cont
            3. Depunere
            4. Retragere
            5. Transfer
            6. Vizualizeaza soldul
            7. Emite card
            8. Blocheaza/Activeaza card
            9. Extras de cont
            10. Listeaza conturile clientului
            11. Cauta client
            12. Sterge/Inchide cont
            0. Iesire
            
            Alegeti o optiune:
            """);
    }

    // 1. creeaza client nou

    private static void creeazaClient() {
        System.out.println("\n Creeaza Client Nou ");
        System.out.print("ID client: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nume complet: ");
        String nume = scanner.nextLine().trim();
        System.out.print("Strada: ");
        String strada = scanner.nextLine().trim();
        System.out.print("Oras: ");
        String oras = scanner.nextLine().trim();
        System.out.print("Cod postal: ");
        String codPostal = scanner.nextLine().trim();
        System.out.print("Telefon: ");
        String telefon = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Adresa adresa = new Adresa(strada, oras, codPostal);
        Client client = clientService.adauga(id, nume, adresa, telefon, email);
        System.out.println("[OK] Client inregistrat: " + client + "\n");
    }

    // 2. deschide cont nou pentru un client existent

    private static void deschideCont() {
        System.out.println("\n Deschide Cont ");
        System.out.print("IBAN (ex: RO49BTRL1234): ");
        String iban = scanner.nextLine().trim();
        System.out.print("Sold initial (RON): ");
        double sold = readDouble();
        System.out.print("ID client: ");
        String idClient = scanner.nextLine().trim();
        System.out.print("Tip cont (curent / economii): ");
        String tip = scanner.nextLine().trim().toLowerCase();
        double param;
        if (tip.equals("economii")) {
            System.out.print("Rata dobanda (%): ");
        } else {
            System.out.print("Limita overdraft (RON): ");
        }
        param = readDouble();

        try {
            Client client = clientService.gasesteDupaId(idClient);
            Cont cont = contService.adauga(iban, sold, idClient, tip, param);
            client.adaugaCont(cont.getNumarCont());
            System.out.println("[OK] Cont deschis: " + cont + "\n");
        } catch (ClientInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    // 3. depunere in cont

    private static void depunere() {
        System.out.println("\n Depunere ");
        System.out.print("IBAN cont: ");
        String iban = scanner.nextLine().trim();
        System.out.print("Suma de depus (RON): ");
        double suma = readDouble();
        try {
            contService.depunere(iban, suma);
            System.out.println("[OK] Depunere efectuata. Sold nou: " + contService.veziSold(iban) + " RON\n");
        } catch (ContInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    // 4. retragere din cont
    private static void retragere() {
        System.out.println("\n Retragere ");
        System.out.print("IBAN cont: ");
        String iban = scanner.nextLine().trim();
        System.out.print("Suma de retras (RON): ");
        double suma = readDouble();
        try {
            contService.retragere(iban, suma);
            System.out.println("[OK] Retragere efectuata. Sold nou: " + contService.veziSold(iban) + " RON\n");
        } catch (FonduriInsuficienteException | ContInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    // 5. transfer intre conturi
    private static void transfer() {
        System.out.println("\n Transfer ");
        System.out.print("IBAN sursa: ");
        String ibanSursa = scanner.nextLine().trim();
        System.out.print("IBAN destinatie: ");
        String ibanDest = scanner.nextLine().trim();
        System.out.print("Suma (RON): ");
        double suma = readDouble();
        try {
            contService.transfer(ibanSursa, ibanDest, suma);
            System.out.println("[OK] Transfer efectuat.");
            System.out.println(" Sold " + ibanSursa + ": " + contService.veziSold(ibanSursa) + " RON");
            System.out.println(" Sold " + ibanDest  + ": " + contService.veziSold(ibanDest)  + " RON\n");
        } catch (FonduriInsuficienteException | ContInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    // 6. vezi sold
    private static void veziSold() {
        System.out.println("\n Vizualizeaza Sold ");
        System.out.print("IBAN cont: ");
        String iban = scanner.nextLine().trim();
        try {
            System.out.println("[OK] Sold " + iban + ": " + contService.veziSold(iban) + " RON\n");
        } catch (ContInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    // 7. emite card pentru un cont existent
    private static void emiteCard() {
        System.out.println("\n Emite Card ");
        System.out.print("Numar card (16 cifre): ");
        String numar = scanner.nextLine().trim();
        System.out.print("Tip card (debit / credit): ");
        String tip = scanner.nextLine().trim();
        System.out.print("Data expirare (YYYY-MM-DD): ");
        LocalDate expirare = readDate();
        if (expirare == null) return;
        System.out.print("CVV (3 cifre): ");
        String cvv = scanner.nextLine().trim();
        System.out.print("IBAN cont asociat: ");
        String iban = scanner.nextLine().trim();

        try {
            Card card = cardService.adauga(numar, tip, expirare, cvv, iban);
            System.out.println("[OK] Card emis: " + card.getNumarCard() + "\n");
        } catch (ContInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    // 8. blocheaza / activeaza card

    private static void toggleCard() {
        System.out.println("\n Blocheaza / Activeaza Card ");
        System.out.print("Numar card: ");
        String numar = scanner.nextLine().trim();

        Card card = cardService.gasesteDupaNumar(numar);

        if (card == null) {
            System.out.println("[EROARE] Cardul nu a fost gasit.\n");
        } else if (card.getStatus() == StatusCard.ACTIV) {
            cardService.blocheaza(numar);
            System.out.println("[OK] Cardul a fost blocat.\n");
        } else {
            cardService.activeaza(numar);
            System.out.println("[OK] Cardul a fost activat.\n");
        }
    }

    // 9. lista conturi client

    private static void listaConturiClient() {
        System.out.println("\n Conturile unui Client ");
        System.out.print("ID client: ");
        String id = scanner.nextLine().trim();
        List<Cont> lista = contService.listaConturiClient(id);
        if (lista.isEmpty()) {
            System.out.println("Clientul nu are conturi deschise sau nu exista.\n");
        } else {
            for (Cont c : lista) {
                System.out.println(c);
            }
            System.out.println();
        }
    }

    // 10. cauta client dupa id sau nume

    private static void cautaClient() {
        System.out.println("\n Cauta Client ");
        System.out.print("Introduceti ID sau nume: ");
        String query = scanner.nextLine().trim();

        try {
            Client c = clientService.gasesteDupaId(query);
            System.out.println("[OK] " + c + "\n");
            return;
        } catch (ClientInexistentException ignored) {}

        List<Client> rezultate = clientService.gasesteDupaNume(query);

        if (rezultate.isEmpty()) {
            System.out.println("[INFO] Niciun client gasit pentru: \"" + query + "\"\n");
        } else {
            System.out.println("[OK] Clienti gasiti (" + rezultate.size() + "):");
            for (Client c : rezultate) {
                System.out.println(c);
            }
            System.out.println();
        }
    }

    // 11. Genereaza extras de cont pentru o perioada data
    private static void genereazaExtras() {
        System.out.println("\n Extras de Cont ");
        System.out.print("IBAN cont: ");
        String iban = scanner.nextLine().trim();
        System.out.print("Data start (YYYY-MM-DD): ");
        LocalDate start = readDate();
        if (start == null) return;
        System.out.print("Data sfarsit (YYYY-MM-DD): ");
        LocalDate sfarsit = readDate();
        if (sfarsit == null) return;

        try {
            ExtrasDeCont extras = contService.genereazaExtras(iban, start, sfarsit);
            System.out.println(extras + "\n");
        } catch (ContInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    // 12. Sterge / Inchide cont
    private static void stergeContMenu() {
        System.out.println("\n Sterge / Inchide Cont ");
        System.out.print("IBAN cont de inchis: ");
        String iban = scanner.nextLine().trim();
        try {
            contService.sterge(iban);
            System.out.println("[OK] Contul a fost inchis.\n");
        } catch (ContInexistentException | IllegalArgumentException e) {
            System.out.println("[EROARE] " + e.getMessage() + "\n");
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("[!] Valoare invalida. Introduceti un numar: ");
            }
        }
    }

    private static LocalDate readDate() {
        while (true) {
            String raw = scanner.nextLine().trim();
            if (raw.equalsIgnoreCase("q")) {
                System.out.println("Operatie anulata.\n");
                return null;
            }
            try {
                return LocalDate.parse(raw);
            } catch (DateTimeParseException e) {
                System.out.print("[!] Format invalid. Folositi YYYY-MM-DD (sau 'q' pentru anulare): ");
            }
        }
    }
}