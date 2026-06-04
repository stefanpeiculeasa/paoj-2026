package com.pao.project.banking.exception;

public class ContInexistentException extends RuntimeException {
    private final String numarCont;

    public ContInexistentException(String numarCont) {
        super("Contul cu numarul '" + numarCont + "' nu exista in sistem.");
        this.numarCont = numarCont;
    }

    public String getNumarCont() {
        return numarCont;
    }
}