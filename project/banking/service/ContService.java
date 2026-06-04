package com.pao.project.banking.service;

import com.pao.project.banking.exception.ContInexistentException;
import com.pao.project.banking.exception.FonduriInsuficienteException;
import com.pao.project.banking.model.*;
import com.pao.project.banking.repository.ContRepository;
import com.pao.project.banking.repository.TranzactieRepository;
import com.pao.project.banking.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContService {

    private static ContService instance;
    private final ContRepository contRepository;
    private final TranzactieRepository tranzactieRepository;

    private ContService() {
        this.contRepository = new ContRepository();
        this.tranzactieRepository = new TranzactieRepository();
    }

    public static ContService getInstance() {
        if (instance == null) {
            instance = new ContService();
        }
        return instance;
    }

    public Cont adauga(String numarCont, double soldInitial, String clientId, String tipCont, double parametru) {
        AuditService.getInstance().log("adauga_cont");
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

        contRepository.save(cont);
        
        if (soldInitial > 0) {
            Tranzactie t = new Tranzactie(null, validIban, soldInitial, TipTranzactie.DEPUNERE);
            tranzactieRepository.save(t);
        }
        return cont;
    }

    public Cont gasesteDupaNumar(String numarCont) {
        AuditService.getInstance().log("gaseste_cont_dupa_numar");
        IBANCod iban = IBANCod.din(numarCont);
        return contRepository.findById(iban.getIBANComplet())
                .orElseThrow(() -> new ContInexistentException(iban.getIBANComplet()));
    }

    public List<Cont> listaToate() {
        AuditService.getInstance().log("lista_toate_conturile");
        return contRepository.findAll();
    }

    public List<Cont> listaConturiClient(String clientId) {
        AuditService.getInstance().log("lista_conturi_client");
        List<Cont> rezultat = new ArrayList<>();
        for (Cont c : contRepository.findAll()) {
            if (clientId.equals(c.getClientId())) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    public void sterge(String numarCont) {
        AuditService.getInstance().log("sterge_cont");
        Cont c = gasesteDupaNumar(numarCont);
        contRepository.delete(c.getNumarCont());
    }

    public void depunere(String numarCont, double suma) {
        AuditService.getInstance().log("depunere");
        Cont cont = gasesteDupaNumar(numarCont);
        cont.depune(suma);
        contRepository.update(cont);
        
        Tranzactie t = new Tranzactie(null, cont.getNumarCont(), suma, TipTranzactie.DEPUNERE);
        tranzactieRepository.save(t);
    }

    public void retragere(String numarCont, double suma) {
        AuditService.getInstance().log("retragere");
        Cont cont = gasesteDupaNumar(numarCont);
        boolean succes = cont.retrage(suma);
        if (!succes) {
            throw new FonduriInsuficienteException("Fonduri insuficiente in contul '" + cont.getNumarCont() + "'. Sold curent: " + cont.getSold());
        }
        contRepository.update(cont);
        
        Tranzactie t = new Tranzactie(cont.getNumarCont(), null, suma, TipTranzactie.RETRAGERE);
        tranzactieRepository.save(t);
    }

    public void transfer(String dinCont, String inCont, double suma) {
        AuditService.getInstance().log("transfer");
        Cont sursa = gasesteDupaNumar(dinCont);
        Cont destinatie = gasesteDupaNumar(inCont);
        
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            
            boolean succes = sursa.retrage(suma);
            if (!succes) {
                throw new FonduriInsuficienteException("Fonduri insuficiente in contul '" + sursa.getNumarCont() + "'. Sold curent: " + sursa.getSold());
            }
            destinatie.depune(suma);
            
            contRepository.update(sursa);
            contRepository.update(destinatie);
            
            Tranzactie t = new Tranzactie(sursa.getNumarCont(), destinatie.getNumarCont(), suma, TipTranzactie.TRANSFER);
            tranzactieRepository.save(t);
            
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Eroare critica la rollback", ex);
            }
            throw new RuntimeException("Eroare la transfer: " + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la resetarea auto-commit", e);
            }
        }
    }

    public double veziSold(String numarCont) {
        AuditService.getInstance().log("vezi_sold");
        return gasesteDupaNumar(numarCont).getSold();
    }
    
    public void printeazaConturiSiCarduri() {
        AuditService.getInstance().log("printeaza_conturi_si_carduri");
        contRepository.printConturiWithCardCount();
    }
    
    public void printeazaDetaliiTranzactii() {
        AuditService.getInstance().log("printeaza_detalii_tranzactii");
        tranzactieRepository.printTranzactiiDetails();
    }

    public ExtrasDeCont genereazaExtras(String numarCont, LocalDate start, LocalDate end) {
        AuditService.getInstance().log("genereaza_extras");
        return new ExtrasDeCont(gasesteDupaNumar(numarCont), start, end);
    }
}