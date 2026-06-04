package com.pao.project.banking.repository;

import com.pao.project.banking.model.Cont;
import com.pao.project.banking.model.ContCurent;
import com.pao.project.banking.model.ContEconomii;
import com.pao.project.banking.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContRepository implements Repository<Cont, String> {

    @Override
    public void save(Cont cont) {
        String sql = "INSERT INTO Conturi (numarCont, sold, clientId, dataDeschidere, tipCont, limitaDescoperire, dobandaAnuala, soldMinim) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cont.getNumarCont());
            stmt.setDouble(2, cont.getSold());
            stmt.setString(3, cont.getClientId());
            stmt.setDate(4, Date.valueOf(cont.getDataDeschidere()));
            
            if (cont instanceof ContCurent) {
                stmt.setString(5, "Curent");
                stmt.setDouble(6, ((ContCurent) cont).getLimitaDescoperire());
                stmt.setNull(7, Types.DOUBLE);
                stmt.setNull(8, Types.DOUBLE);
            } else if (cont instanceof ContEconomii) {
                stmt.setString(5, "Economii");
                stmt.setNull(6, Types.DOUBLE);
                stmt.setDouble(7, ((ContEconomii) cont).getDobandaAnuala());
                stmt.setDouble(8, ((ContEconomii) cont).getSoldMinim());
            }
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea contului", e);
        }
    }

    @Override
    public Optional<Cont> findById(String id) {
        String sql = "SELECT * FROM Conturi WHERE numarCont = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToCont(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea contului", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Cont> findAll() {
        List<Cont> conturi = new ArrayList<>();
        String sql = "SELECT * FROM Conturi";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                conturi.add(mapRowToCont(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la gasirea tuturor conturilor", e);
        }
        return conturi;
    }

    @Override
    public void update(Cont cont) {
        String sql = "UPDATE Conturi SET sold = ?, limitaDescoperire = ?, dobandaAnuala = ?, soldMinim = ? WHERE numarCont = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, cont.getSold());
            
            if (cont instanceof ContCurent) {
                stmt.setDouble(2, ((ContCurent) cont).getLimitaDescoperire());
                stmt.setNull(3, Types.DOUBLE);
                stmt.setNull(4, Types.DOUBLE);
            } else if (cont instanceof ContEconomii) {
                stmt.setNull(2, Types.DOUBLE);
                stmt.setDouble(3, ((ContEconomii) cont).getDobandaAnuala());
                stmt.setDouble(4, ((ContEconomii) cont).getSoldMinim());
            }
            
            stmt.setString(5, cont.getNumarCont());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea contului", e);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Conturi WHERE numarCont = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea contului", e);
        }
    }

    private Cont mapRowToCont(ResultSet rs) throws SQLException {
        String tipCont = rs.getString("tipCont");
        if ("Curent".equals(tipCont)) {
            return new ContCurent(
                    rs.getString("numarCont"),
                    rs.getDouble("sold"),
                    rs.getString("clientId"),
                    rs.getDouble("limitaDescoperire")
            );
        } else {
            return new ContEconomii(
                    rs.getString("numarCont"),
                    rs.getDouble("sold"),
                    rs.getString("clientId"),
                    rs.getDouble("dobandaAnuala"),
                    rs.getDouble("soldMinim")
            );
        }
    }

    // JOIN Query 2
    public void printConturiWithCardCount() {
        String sql = "SELECT co.numarCont, COUNT(c.numarCard) as nr_carduri " +
                     "FROM Conturi co " +
                     "LEFT JOIN Carduri c ON co.numarCont = c.numarContAsociat " +
                     "GROUP BY co.numarCont";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("--- Conturi si numarul de carduri asociate ---");
            while (rs.next()) {
                System.out.println("Cont: " + rs.getString("numarCont") + " | Numar carduri: " + rs.getInt("nr_carduri"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la executarea query-ului cu JOIN", e);
        }
    }
}
