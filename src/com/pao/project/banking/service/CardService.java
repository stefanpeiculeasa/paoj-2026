package com.pao.project.banking.service;

import com.pao.project.banking.model.Card;
import com.pao.project.banking.model.StatusCard;
import com.pao.project.banking.model.IBANCod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardService {

    private static CardService instance;
    private final List<Card> carduri;

    private CardService() {
        this.carduri = new ArrayList<>();
    }

    public static CardService getInstance() {
        if (instance == null) {
            instance = new CardService();
        }
        return instance;
    }

    public Card adauga(String numarCard, String tip, LocalDate expirare, String cvv, String numarCont) {
        IBANCod iban = IBANCod.din(numarCont);
        Card card = new Card(numarCard, tip, expirare, cvv, iban.getIBANComplet());
        carduri.add(card);
        return card;
    }

    public Card gasesteDupaNumar(String numarCard) {
        for (Card c : carduri) {
            if (c.getNumarCard().equals(numarCard)) {
                return c;
            }
        }
        return null;
    }

    public List<Card> listaToate() {
        return new ArrayList<>(carduri);
    }

    public List<Card> listaCarduriCont(String numarCont) {
        List<Card> rezultat = new ArrayList<>();
        for (Card c : carduri) {
            if (c.getNumarContAsociat().equals(numarCont)) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    public boolean blocheaza(String numarCard) {
        Card card = gasesteDupaNumar(numarCard);
        if (card == null) {
            return false;
        }
        card.setStatus(StatusCard.BLOCAT);
        return true;
    }

    public boolean activeaza(String numarCard) {
        Card card = gasesteDupaNumar(numarCard);
        if (card == null) {
            return false;
        }
        card.setStatus(StatusCard.ACTIV);
        return true;
    }

    public boolean sterge(String numarCard) {
        for (int i = 0; i < carduri.size(); i++) {
            Card c = carduri.get(i);
            if (c.getNumarCard().equals(numarCard)) {
                carduri.remove(i);
                return true;
            }
        }
        return false;
    }
}