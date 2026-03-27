package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }
    
    private Carte[] carti;
    
    private BibliotecaService() {
        carti = new Carte[0];
    }
    
    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }
    
    public void addCarte(Carte carte) {
        Carte[] copy = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, copy, 0, carti.length);
        copy[carti.length] = carte;
        carti = copy;
        System.out.println("carte adaugata: " + carte.getTitlu());
    }
    
    public void listSortedByRating() {
        Carte[] copy = carti.clone();
        Arrays.sort(copy);
        int i = 1;
        for (Carte c : copy) {
            System.out.println(i++ + ". " + c);
        }
    }
    
    public void listSortedBy(Comparator<Carte> comparator) {
        Carte[] copy = carti.clone();
        Arrays.sort(copy, comparator);
        int i = 1;
        for (Carte c : copy) {
            System.out.println(i++ + ". " + c);
        }
    }
}