package com.pao.project.banking.repository;

import com.pao.project.banking.model.Adresa;
import com.pao.project.banking.model.Client;
import com.pao.project.banking.model.Cont;
import com.pao.project.banking.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, String> {

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO Clienti (id, nume, strada, oras, codPostal, telefon, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getId());
            stmt.setString(2, client.getNume());
            stmt.setString(3, client.getAdresa().getStrada());
            stmt.setString(4, client.getAdresa().getOras());
            stmt.setString(5, client.getAdresa().getCodPostal());
            stmt.setString(6, client.getTelefon());
            stmt.setString(7, client.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea clientului", e);
        }
    }

    @Override
    public Optional<Client> findById(String id) {
        String sql = "SELECT * FROM Clienti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Adresa adresa = new Adresa(rs.getString("strada"), rs.getString("oras"), rs.getString("codPostal"));
                Client client = new Client(rs.getString("id"), rs.getString("nume"), adresa, rs.getString("telefon"), rs.getString("email"));
                return Optional.of(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea clientului", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clienti = new ArrayList<>();
        String sql = "SELECT * FROM Clienti";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Adresa adresa = new Adresa(rs.getString("strada"), rs.getString("oras"), rs.getString("codPostal"));
                Client client = new Client(rs.getString("id"), rs.getString("nume"), adresa, rs.getString("telefon"), rs.getString("email"));
                clienti.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la gasirea tuturor clientilor", e);
        }
        return clienti;
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE Clienti SET nume = ?, strada = ?, oras = ?, codPostal = ?, telefon = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNume());
            stmt.setString(2, client.getAdresa().getStrada());
            stmt.setString(3, client.getAdresa().getOras());
            stmt.setString(4, client.getAdresa().getCodPostal());
            stmt.setString(5, client.getTelefon());
            stmt.setString(6, client.getEmail());
            stmt.setString(7, client.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea clientului", e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Clienti WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea clientului", e);
        }
    }

    // JOIN Query 1
    public void printClientsWithAccounts() {
        String sql = "SELECT cl.nume, co.numarCont, co.sold " +
                     "FROM Clienti cl " +
                     "LEFT JOIN Conturi co ON cl.id = co.clientId";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("--- Clienti si conturile lor ---");
            while (rs.next()) {
                String cont = rs.getString("numarCont");
                if (cont == null) {
                    System.out.println("Client: " + rs.getString("nume") + " | Cont: [Niciun cont deschis]");
                } else {
                    System.out.println("Client: " + rs.getString("nume") + " | Cont: " + cont + " | Sold: " + rs.getDouble("sold"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la executarea query-ului cu JOIN", e);
        }
    }
}
