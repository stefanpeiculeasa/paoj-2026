package com.pao.project.banking.service;

import com.pao.project.banking.model.Card;
import com.pao.project.banking.model.StatusCard;
import com.pao.project.banking.model.IBANCod;
import com.pao.project.banking.repository.CardRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardService {

    private static CardService instance;
    private final CardRepository cardRepository;

    private CardService() {
        this.cardRepository = new CardRepository();
    }

    public static CardService getInstance() {
        if (instance == null) {
            instance = new CardService();
        }
        return instance;
    }

    public Card adauga(String numarCard, String tip, LocalDate expirare, String cvv, String numarCont) {
        AuditService.getInstance().log("adauga_card");
        IBANCod iban = IBANCod.din(numarCont);
        Card card = new Card(numarCard, tip, expirare, cvv, iban.getIBANComplet());
        cardRepository.save(card);
        return card;
    }

    public Card gasesteDupaNumar(String numarCard) {
        AuditService.getInstance().log("gaseste_card_dupa_numar");
        return cardRepository.findById(numarCard).orElse(null);
    }

    public List<Card> listaToate() {
        AuditService.getInstance().log("lista_toate_cardurile");
        return cardRepository.findAll();
    }

    public List<Card> listaCarduriCont(String numarCont) {
        AuditService.getInstance().log("lista_carduri_cont");
        List<Card> rezultat = new ArrayList<>();
        for (Card c : cardRepository.findAll()) {
            if (c.getNumarContAsociat().equals(numarCont)) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    public boolean blocheaza(String numarCard) {
        AuditService.getInstance().log("blocheaza_card");
        Card card = gasesteDupaNumar(numarCard);
        if (card == null) {
            return false;
        }
        card.setStatus(StatusCard.BLOCAT);
        cardRepository.update(card);
        return true;
    }

    public boolean activeaza(String numarCard) {
        AuditService.getInstance().log("activeaza_card");
        Card card = gasesteDupaNumar(numarCard);
        if (card == null) {
            return false;
        }
        card.setStatus(StatusCard.ACTIV);
        cardRepository.update(card);
        return true;
    }

    public boolean sterge(String numarCard) {
        AuditService.getInstance().log("sterge_card");
        Card card = gasesteDupaNumar(numarCard);
        if (card != null) {
            cardRepository.delete(numarCard);
            return true;
        }
        return false;
    }
}