package com.pao.project.banking.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Tranzactie implements Comparable<Tranzactie> {
    private static long counter = 0;
    private String id;
    private String dinCont;
    private String inCont;
    private double suma;
    private LocalDateTime data;
    private TipTranzactie tip;

    public Tranzactie(String dinCont, String inCont, double suma, TipTranzactie tip) {
        this.id = UUID.randomUUID().toString();
        this.dinCont = dinCont;
        this.inCont = inCont;
        this.suma = suma;
        this.data = LocalDateTime.now();
        this.tip = tip;
    }

    public String getId() { return id; }
    public String getDinCont() { return dinCont; }
    public String getInCont() { return inCont; }
    public double getSuma() { return suma; }
    public LocalDateTime getData() { return data; }
    public TipTranzactie getTip() { return tip; }

    @Override
    public int compareTo(Tranzactie other) {
        int cmp = this.data.compareTo(other.data);
        return cmp != 0 ? cmp : this.id.compareTo(other.id);
    }

    private static String generateId() {
        counter++;
        return String.valueOf(counter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tranzactie)) return false;
        Tranzactie other = (Tranzactie) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "id='" + id + '\'' +
                ", dinCont='" + dinCont + '\'' +
                ", inCont='" + inCont + '\'' +
                ", suma=" + suma +
                ", data=" + data +
                ", tip=" + tip +
                '}';
    }
}