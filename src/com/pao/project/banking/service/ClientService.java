package com.pao.project.banking.service;

import com.pao.project.banking.exception.ClientInexistentException;
import com.pao.project.banking.model.Adresa;
import com.pao.project.banking.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static ClientService instance;
    private final List<Client> clienti;

    private ClientService() {
        this.clienti = new ArrayList<>();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public Client adauga(String id, String nume, Adresa adresa, String telefon, String email) {
        Client client = new Client(id, nume, adresa, telefon, email);
        clienti.add(client);
        return client;
    }

    public Client gasesteDupaId(String id) {
        for (Client c : clienti) {
            if (id.equals(c.getId())) {
                return c;
            }
        }
        throw new ClientInexistentException(id);
    }

    public List<Client> gasesteDupaNume(String nume) {
        List<Client> rezultat = new ArrayList<>();
        for (Client c : clienti) {
            if (c.getNume() != null && c.getNume().toLowerCase().contains(nume.toLowerCase())) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    public List<Client> listaToate() {
        return new ArrayList<>(clienti);
    }

    public void actualizeaza(String id, String telefon, String email, Adresa adresaNoua) {
        Client c = gasesteDupaId(id);
        c.setTelefon(telefon);
        c.setEmail(email);
        c.setAdresa(adresaNoua);
    }

    public boolean sterge(String id) {
        Client c = gasesteDupaId(id);
        return clienti.remove(c);
    }
}