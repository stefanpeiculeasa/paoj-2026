package com.pao.project.banking.model;

public final class IBANCod {
    private final String tara; // ex: "RO"
    private final String cifraControl; // ex: "49"
    private final String codBanca; // ex: "BTRL"
    private final String codCont; // ex: "0000001234567890"

    public IBANCod(String tara, String cifraControl, String codBanca, String codCont) {
        if (tara == null || tara.length() != 2)
            throw new IllegalArgumentException("Codul tarii trebuie sa aiba exact 2 caractere.");
        if (cifraControl == null || cifraControl.length() != 2)
            throw new IllegalArgumentException("Cifra de control trebuie sa aiba exact 2 caractere.");
        if (codBanca == null || codBanca.isBlank())
            throw new IllegalArgumentException("Codul bancii nu poate fi gol.");
        if (codCont == null || codCont.isBlank())
            throw new IllegalArgumentException("Codul contului nu poate fi gol.");

        this.tara = tara.toUpperCase();
        this.cifraControl = cifraControl;
        this.codBanca = codBanca.toUpperCase();
        this.codCont = codCont;
    }

    public static IBANCod din(String iban) {
        if (iban == null || iban.length() < 8)
            throw new IllegalArgumentException("IBAN invalid: " + iban);
        String tara = iban.substring(0, 2);
        String cifra = iban.substring(2, 4);
        String banca = iban.substring(4, 8);
        String cont = iban.substring(8);
        return new IBANCod(tara, cifra, banca, cont);
    }

    public String getTara() { return tara; }
    public String getCifraControl() { return cifraControl; }
    public String getCodBanca() { return codBanca; }
    public String getCodCont() { return codCont; }

    public String getIBANComplet() {
        return tara + cifraControl + codBanca + codCont;
    }

    @Override
    public String toString() {
        return getIBANComplet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IBANCod)) return false;
        IBANCod other = (IBANCod) o;
        return getIBANComplet().equals(other.getIBANComplet());
    }

    @Override
    public int hashCode() {
        return getIBANComplet().hashCode();
    }
}