package com.pao.project.banking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private String id;
    private String nume;
    private Adresa adresa;
    private String telefon;
    private String email;
    private List<String> conturi;

    public Client(String id, String nume, Adresa adresa, String telefon, String email) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.conturi = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public Adresa getAdresa() { return adresa; }
    public void setAdresa(Adresa adresa) { this.adresa = adresa; }
    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<String> getConturi() { return conturi; }

    public void adaugaCont(String numarCont) {
        conturi.add(numarCont);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", nume='" + nume + '\'' +
                ", adresa=" + adresa +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", conturi=" + conturi +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client other = (Client) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}