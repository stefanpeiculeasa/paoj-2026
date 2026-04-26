package com.pao.project.banking.service;

import com.pao.project.banking.exception.ContInexistentException;
import com.pao.project.banking.exception.FonduriInsuficienteException;
import com.pao.project.banking.model.*;

import java.time.LocalDate;
import java.util.*;

public class ContService {

    private static ContService instance;
    private final Map<String, Cont> conturi;
    private final TreeSet<Tranzactie> toateTranzactiile;

    private ContService() {
        this.conturi = new HashMap<>();
        this.toateTranzactiile = new TreeSet<>();
    }

    public static ContService getInstance() {
        if (instance == null) {
            instance = new ContService();
        }
        return instance;
    }

    public Cont adauga(String numarCont, double soldInitial, String clientId, String tipCont, double parametru) {
        IBANCod iban = IBANCod.din(numarCont);
        String validIban = iban.getIBANComplet();

        Cont cont;
        if (tipCont.equalsIgnoreCase("economii")) {
            cont = new ContEconomii(validIban, soldInitial, clientId, parametru, 100);
        } else if (tipCont.equalsIgnoreCase("curent")) {
            cont = new ContCurent(validIban, soldInitial, clientId, parametru);
        } else {
            throw new IllegalArgumentException("Tip cont necunoscut: " + tipCont);
        }

        conturi.put(validIban, cont);
        if (soldInitial > 0) {
            Tranzactie t = new Tranzactie(null, validIban, soldInitial, TipTranzactie.DEPUNERE);
            cont.adaugaTranzactie(t);
            toateTranzactiile.add(t);
        }
        return cont;
    }

    public Cont gasesteDupaNumar(String numarCont) {
        IBANCod iban = IBANCod.din(numarCont);
        Cont cont = conturi.get(iban.getIBANComplet());
        if (cont == null) {
            throw new ContInexistentException(iban.getIBANComplet());
        }
        return cont;
    }

    public List<Cont> listaToate() {
        return new ArrayList<>(conturi.values());
    }

    public List<Cont> listaConturiClient(String clientId) {
        List<Cont> rezultat = new ArrayList<>();
        for (Cont c : conturi.values()) {
            if (clientId.equals(c.getClientId())) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    public void sterge(String numarCont) {
        Cont c = gasesteDupaNumar(numarCont);
        conturi.remove(c.getNumarCont());
    }

    public void depunere(String numarCont, double suma) {
        Cont cont = gasesteDupaNumar(numarCont);
        cont.depune(suma);
        Tranzactie t = new Tranzactie(null, cont.getNumarCont(), suma, TipTranzactie.DEPUNERE);
        cont.adaugaTranzactie(t);
        toateTranzactiile.add(t);
    }

    public void retragere(String numarCont, double suma) {
        Cont cont = gasesteDupaNumar(numarCont);
        boolean succes = cont.retrage(suma);
        if (!succes) {
            throw new FonduriInsuficienteException("Fonduri insuficiente in contul '" + cont.getNumarCont() + "'. Sold curent: " + cont.getSold());
        }
        Tranzactie t = new Tranzactie(cont.getNumarCont(), null, suma, TipTranzactie.RETRAGERE);
        cont.adaugaTranzactie(t);
        toateTranzactiile.add(t);
    }

    public void transfer(String dinCont, String inCont, double suma) {
        Cont sursa = gasesteDupaNumar(dinCont);
        Cont destinatie = gasesteDupaNumar(inCont);
        boolean succes = sursa.retrage(suma);
        if (!succes) {
            throw new FonduriInsuficienteException("Fonduri insuficiente in contul '" + sursa.getNumarCont() + "'. Sold curent: " + sursa.getSold());
        }
        destinatie.depune(suma);
        Tranzactie t = new Tranzactie(sursa.getNumarCont(), destinatie.getNumarCont(), suma, TipTranzactie.TRANSFER);
        sursa.adaugaTranzactie(t);
        destinatie.adaugaTranzactie(t);
        toateTranzactiile.add(t);
    }

    public double veziSold(String numarCont) {
        return gasesteDupaNumar(numarCont).getSold();
    }

    public List<Tranzactie> veziTranzactii(String numarCont) {
        return gasesteDupaNumar(numarCont).getTranzactii();
    }

    public ExtrasDeCont genereazaExtras(String numarCont, LocalDate start, LocalDate end) {
        return new ExtrasDeCont(gasesteDupaNumar(numarCont), start, end);
    }

    public TreeSet<Tranzactie> getToateTranzactiile() {
        return toateTranzactiile;
    }

    public Map<String, Cont> getConturi() {
        return Collections.unmodifiableMap(conturi);
    }
}