package com.pao.laboratory07.exercise1;

public enum StareComanda {
    PLASATA, PROCESATA, EXPEDIATA, LIVRATA, ANULATA;

    public boolean esteFinala() {
        return this == LIVRATA || this == ANULATA;
    }

    public StareComanda urmatoarea() {
        return switch (this) {
            case PLASATA   -> PROCESATA;
            case PROCESATA -> EXPEDIATA;
            case EXPEDIATA -> LIVRATA;
            default        -> this;
        };
    }
}