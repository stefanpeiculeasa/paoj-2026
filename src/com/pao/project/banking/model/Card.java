package com.pao.project.banking.model;

import java.time.LocalDate;

public class Card {
    private String numarCard;
    private String tip;
    private LocalDate dataExpirare;
    private String cvv;
    private String numarContAsociat;
    private StatusCard status;

    public Card(String numarCard, String tip, LocalDate dataExpirare, String cvv, String numarContAsociat) {
        this.numarCard = numarCard;
        this.tip = tip;
        this.dataExpirare = dataExpirare;
        this.cvv = cvv;
        this.numarContAsociat = numarContAsociat;
        this.status = StatusCard.ACTIV;
    }

    public String getNumarCard() { return numarCard; }
    public String getTip() { return tip; }
    public LocalDate getDataExpirare() { return dataExpirare; }
    public String getCvv() { return cvv; }
    public String getNumarContAsociat() { return numarContAsociat; }
    public StatusCard getStatus() { return status; }
    public void setStatus(StatusCard status) { this.status = status; }

    @Override
    public String toString() {
        return "Card{" +
                "numarCard='" + numarCard + '\'' +
                ", tip='" + tip + '\'' +
                ", dataExpirare=" + dataExpirare +
                ", numarContAsociat='" + numarContAsociat + '\'' +
                ", status=" + status +
                '}';
    }
}