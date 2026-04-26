package com.pao.project.banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExtrasDeCont {
    private String numarCont;
    private LocalDate dataStart;
    private LocalDate dataEnd;
    private List<Tranzactie> tranzactiiPerioada;
    private double soldInitial;
    private double soldFinal;

    public ExtrasDeCont(Cont cont, LocalDate start, LocalDate end) {
        this.numarCont = cont.getNumarCont();
        this.dataStart = start;
        this.dataEnd = end;
        this.soldFinal = cont.getSold();

        this.tranzactiiPerioada = new ArrayList<>();
        for (Tranzactie t : cont.getTranzactii()) {
            LocalDate dataTranzactie = t.getData().toLocalDate();
            if (!dataTranzactie.isBefore(start) && !dataTranzactie.isAfter(end)) {
                tranzactiiPerioada.add(t);
            }
        }

        double diferenta = 0;
        for (Tranzactie t : tranzactiiPerioada) {
            if (t.getTip() == TipTranzactie.DEPUNERE) {
                diferenta -= t.getSuma();
            } else {
                diferenta += t.getSuma();
            }
        }
        this.soldInitial = soldFinal + diferenta;
    }

    public String getNumarCont() { return numarCont; }
    public LocalDate getDataStart() { return dataStart; }
    public LocalDate getDataEnd() { return dataEnd; }
    public List<Tranzactie> getTranzactiiPerioada() { return tranzactiiPerioada; }
    public double getSoldInitial() { return soldInitial; }
    public double getSoldFinal() { return soldFinal; }

    @Override
    public String toString() {
        String result = "Extras de cont pentru " + numarCont + "\n" +
                "Perioada: " + dataStart + " - " + dataEnd + "\n" +
                "Sold initial: " + soldInitial + "\n" +
                "Tranzactii:\n";

        for (Tranzactie t : tranzactiiPerioada) {
            result += "  " + t + "\n";
        }

        result += "Sold final: " + soldFinal;

        return result;
    }
}