package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {
    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }
    
    private Angajat[] angajati;
    
    private AngajatService() {
        angajati = new Angajat[0];
    }
    
    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }
    
    public void addAngajat(Angajat a) {
        Angajat[] copy = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, copy, 0, angajati.length);
        copy[angajati.length] = a;
        angajati = copy;
        System.out.println("Angajat adaugat: " + a.getNume());
    }
    
    public void printAll() {
        for (Angajat a : angajati) {
            System.out.println(a);
        }
    }
    
    public void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);
        int i = 1;
        System.out.println("angajati dupa salariu (descrescator)");
        for (Angajat a : copy) {
            System.out.println(i++ + ". " + a);
        }
    }
    
    public void findByDepartament(String numeDept) {
        System.out.println("angajati din " + numeDept);
        boolean gasit = false;
        for (Angajat a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                gasit = true;
            }
        }
        if (!gasit) {
            System.out.println("niciun angajat in departamentul: " + numeDept);
        }
    }
}