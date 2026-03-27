package com.pao.laboratory05.audit;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        AngajatService service = AngajatService.getInstance();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.println("1. Adauga angajat");
            System.out.println("2. Listare dupa salariu");
            System.out.println("3. Cauta dupa departament");
            System.out.println("4. Afiseaza audit log");
            System.out.println("0. Iesire");
            System.out.print("Optiune: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            if (option == 0) {
                System.out.println("La revedere!");
                break;
            } else if (option == 1) {
                System.out.print("Nume: ");
                String nume = scanner.nextLine();
                System.out.print("Departament (nume): ");
                String numeDept = scanner.nextLine();
                System.out.print("Departament (locatie): ");
                String locatieDept = scanner.nextLine();
                System.out.print("Salariu: ");
                double salariu = scanner.nextDouble();
                scanner.nextLine();
                service.addAngajat(new Angajat(nume, new Departament(numeDept, locatieDept), salariu));
            } else if (option == 2) {
                service.listBySalary();
            } else if (option == 3) {
                System.out.print("Departament: ");
                String dept = scanner.nextLine();
                service.findByDepartament(dept);
            } else if (option == 4) {
                service.printAuditLog();
            }
        }
    }
}
