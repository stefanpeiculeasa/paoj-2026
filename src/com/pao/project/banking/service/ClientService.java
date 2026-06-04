package com.pao.project.banking.service;

import com.pao.project.banking.exception.ClientInexistentException;
import com.pao.project.banking.model.Adresa;
import com.pao.project.banking.model.Client;
import com.pao.project.banking.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientService {

    private static ClientService instance;
    private final ClientRepository clientRepository;

    private ClientService() {
        this.clientRepository = new ClientRepository();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public Client adauga(String id, String nume, Adresa adresa, String telefon, String email) {
        AuditService.getInstance().log("adauga_client");
        Client client = new Client(id, nume, adresa, telefon, email);
        clientRepository.save(client);
        return client;
    }

    public Client gasesteDupaId(String id) {
        AuditService.getInstance().log("gaseste_client_dupa_id");
        return clientRepository.findById(id).orElseThrow(() -> new ClientInexistentException(id));
    }

    public List<Client> gasesteDupaNume(String nume) {
        AuditService.getInstance().log("gaseste_client_dupa_nume");
        List<Client> rezultat = new ArrayList<>();
        for (Client c : clientRepository.findAll()) {
            if (c.getNume() != null && c.getNume().toLowerCase().contains(nume.toLowerCase())) {
                rezultat.add(c);
            }
        }
        return rezultat;
    }

    public List<Client> listaToate() {
        AuditService.getInstance().log("lista_tot_clienti");
        return clientRepository.findAll();
    }

    public void actualizeaza(String id, String telefon, String email, Adresa adresaNoua) {
        AuditService.getInstance().log("actualizeaza_client");
        Client c = gasesteDupaId(id);
        c.setTelefon(telefon);
        c.setEmail(email);
        c.setAdresa(adresaNoua);
        clientRepository.update(c);
    }

    public boolean sterge(String id) {
        AuditService.getInstance().log("sterge_client");
        try {
            gasesteDupaId(id);
            clientRepository.delete(id);
            return true;
        } catch (ClientInexistentException e) {
            return false;
        }
    }
    
    public void printeazaClientiSiConturi() {
        AuditService.getInstance().log("printeaza_clienti_si_conturi");
        clientRepository.printClientsWithAccounts();
    }
}