package com.pao.project.banking.repository;

import com.pao.project.banking.model.Card;
import com.pao.project.banking.model.StatusCard;
import com.pao.project.banking.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepository implements Repository<Card, String> {

    @Override
    public void save(Card card) {
        String sql = "INSERT INTO Carduri (numarCard, tip, dataExpirare, cvv, numarContAsociat, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.getNumarCard());
            stmt.setString(2, card.getTip());
            stmt.setDate(3, Date.valueOf(card.getDataExpirare()));
            stmt.setString(4, card.getCvv());
            stmt.setString(5, card.getNumarContAsociat());
            stmt.setString(6, card.getStatus().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea cardului", e);
        }
    }

    @Override
    public Optional<Card> findById(String id) {
        String sql = "SELECT * FROM Carduri WHERE numarCard = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Card card = new Card(
                        rs.getString("numarCard"),
                        rs.getString("tip"),
                        rs.getDate("dataExpirare").toLocalDate(),
                        rs.getString("cvv"),
                        rs.getString("numarContAsociat")
                );
                card.setStatus(StatusCard.valueOf(rs.getString("status")));
                return Optional.of(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea cardului", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Card> findAll() {
        List<Card> carduri = new ArrayList<>();
        String sql = "SELECT * FROM Carduri";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Card card = new Card(
                        rs.getString("numarCard"),
                        rs.getString("tip"),
                        rs.getDate("dataExpirare").toLocalDate(),
                        rs.getString("cvv"),
                        rs.getString("numarContAsociat")
                );
                card.setStatus(StatusCard.valueOf(rs.getString("status")));
                carduri.add(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la gasirea tuturor cardurilor", e);
        }
        return carduri;
    }

    @Override
    public void update(Card card) {
        String sql = "UPDATE Carduri SET status = ? WHERE numarCard = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, card.getStatus().name());
            stmt.setString(2, card.getNumarCard());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea cardului", e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Carduri WHERE numarCard = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea cardului", e);
        }
    }
}
