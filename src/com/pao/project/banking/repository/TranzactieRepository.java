package com.pao.project.banking.repository;

import com.pao.project.banking.model.TipTranzactie;
import com.pao.project.banking.model.Tranzactie;
import com.pao.project.banking.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TranzactieRepository implements Repository<Tranzactie, String> {

    @Override
    public void save(Tranzactie tranzactie) {
        String sql = "INSERT INTO Tranzactii (id, dinCont, inCont, suma, data, tip) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tranzactie.getId());
            stmt.setString(2, tranzactie.getDinCont());
            stmt.setString(3, tranzactie.getInCont());
            stmt.setDouble(4, tranzactie.getSuma());
            stmt.setTimestamp(5, Timestamp.valueOf(tranzactie.getData()));
            stmt.setString(6, tranzactie.getTip().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea tranzactiei", e);
        }
    }

    @Override
    public Optional<Tranzactie> findById(String id) {
        throw new UnsupportedOperationException("FindById nu este suportat");
    }

    @Override
    public List<Tranzactie> findAll() {
        throw new UnsupportedOperationException("FindAll nu este suportat");
    }

    @Override
    public void update(Tranzactie entity) {
        throw new UnsupportedOperationException("Tranzactiile sunt imutabile si nu pot fi actualizate.");
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Tranzactii WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea tranzactiei", e);
        }
    }

    // JOIN Query 3
    public void printTranzactiiDetails() {
        String sql = "SELECT t.id, t.suma, t.tip, " +
                     "c1.nume AS numeExpeditor, c2.nume AS numeDestinatar " +
                     "FROM Tranzactii t " +
                     "LEFT JOIN Conturi co1 ON t.dinCont = co1.numarCont " +
                     "LEFT JOIN Clienti c1 ON co1.clientId = c1.id " +
                     "LEFT JOIN Conturi co2 ON t.inCont = co2.numarCont " +
                     "LEFT JOIN Clienti c2 ON co2.clientId = c2.id";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("--- Detalii Tranzactii ---");
            while (rs.next()) {
                String exp = rs.getString("numeExpeditor");
                String dest = rs.getString("numeDestinatar");
                System.out.println("Tranzactie [" + rs.getString("tip") + "] ID: " + rs.getString("id") +
                                   " | Suma: " + rs.getDouble("suma") +
                                   " | De la: " + (exp != null ? exp : "N/A") +
                                   " | Catre: " + (dest != null ? dest : "N/A"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la executarea query-ului cu JOIN", e);
        }
    }
}
