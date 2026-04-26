package com.pao.project.banking.exception;

public class ClientInexistentException extends RuntimeException {
    private final String clientId;

    public ClientInexistentException(String clientId) {
        super("Clientul cu id-ul '" + clientId + "' nu exista in sistem.");
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}