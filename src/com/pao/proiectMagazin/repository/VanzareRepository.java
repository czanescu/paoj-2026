package com.pao.proiectMagazin.repository;

import com.pao.proiectMagazin.modele.VanzareId;
import com.pao.proiectMagazin.modele.VanzareRecord;
import com.pao.proiectMagazin.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VanzareRepository implements Repository<VanzareRecord, VanzareId> {
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @Override
    public void save(VanzareRecord entity) {
        String sql = "INSERT INTO vanzare (cod_produs, nume_produs, cantitate, pret_unitar, total, categorie, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getCodProdus());
            statement.setString(2, entity.getNumeProdus());
            statement.setInt(3, entity.getCantitate());
            statement.setInt(4, entity.getPretUnitar());
            statement.setInt(5, entity.getTotal());
            statement.setString(6, entity.getCategorieProdus());
            statement.setTimestamp(7, Timestamp.valueOf(entity.getTimestamp()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save vanzare", e);
        }
    }

    @Override
    public Optional<VanzareRecord> findById(VanzareId id) {
        String sql = "SELECT * FROM vanzare WHERE cod_produs = ? AND timestamp = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id.getCodProdus());
            statement.setTimestamp(2, Timestamp.valueOf(id.getTimestamp()));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find vanzare", e);
        }
        return Optional.empty();
    }

    @Override
    public List<VanzareRecord> findAll() {
        String sql = "SELECT * FROM vanzare";
        List<VanzareRecord> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to list vanzari", e);
        }
        return result;
    }

    @Override
    public void update(VanzareRecord entity) {
        String sql = "UPDATE vanzare SET nume_produs = ?, cantitate = ?, pret_unitar = ?, total = ?, categorie = ? WHERE cod_produs = ? AND timestamp = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getNumeProdus());
            statement.setInt(2, entity.getCantitate());
            statement.setInt(3, entity.getPretUnitar());
            statement.setInt(4, entity.getTotal());
            statement.setString(5, entity.getCategorieProdus());
            statement.setInt(6, entity.getCodProdus());
            statement.setTimestamp(7, Timestamp.valueOf(entity.getTimestamp()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update vanzare", e);
        }
    }

    @Override
    public void delete(VanzareId id) {
        String sql = "DELETE FROM vanzare WHERE cod_produs = ? AND timestamp = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id.getCodProdus());
            statement.setTimestamp(2, Timestamp.valueOf(id.getTimestamp()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete vanzare", e);
        }
    }

    public void recordSaleTransactional(int codProdus, int cantitate) throws SQLException {
        if (cantitate <= 0) {
            throw new IllegalArgumentException("cantitate trebuie sa fie pozitiva");
        }
        try (Connection connection = databaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int stocCurent;
                String numeProdus;
                int pretUnitar;
                String categorie;
                String selectSql = "SELECT nume, pret_vanzare, categorie, stoc FROM produs WHERE cod_inventar = ? FOR UPDATE";
                try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                    selectStatement.setInt(1, codProdus);
                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (!resultSet.next()) {
                            throw new IllegalStateException("Produs inexistent: " + codProdus);
                        }
                        numeProdus = resultSet.getString("nume");
                        pretUnitar = resultSet.getInt("pret_vanzare");
                        categorie = resultSet.getString("categorie");
                        stocCurent = resultSet.getInt("stoc");
                    }
                }
                if (cantitate > stocCurent) {
                    throw new IllegalStateException("Stoc insuficient pentru produs: " + codProdus);
                }
                String updateSql = "UPDATE produs SET stoc = ? WHERE cod_inventar = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setInt(1, stocCurent - cantitate);
                    updateStatement.setInt(2, codProdus);
                    updateStatement.executeUpdate();
                }
                String insertSql = "INSERT INTO vanzare (cod_produs, nume_produs, cantitate, pret_unitar, total, categorie, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
                LocalDateTime timestamp = LocalDateTime.now();
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                    insertStatement.setInt(1, codProdus);
                    insertStatement.setString(2, numeProdus);
                    insertStatement.setInt(3, cantitate);
                    insertStatement.setInt(4, pretUnitar);
                    insertStatement.setInt(5, cantitate * pretUnitar);
                    insertStatement.setString(6, categorie);
                    insertStatement.setTimestamp(7, Timestamp.valueOf(timestamp));
                    insertStatement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public List<Map<String, Object>> findTopSellingProducts(int limit) {
        String sql = "SELECT p.cod_inventar, p.nume, p.categorie, SUM(v.cantitate) AS total_unitati, SUM(v.total) AS total_lei "
                + "FROM vanzare v JOIN produs p ON v.cod_produs = p.cod_inventar GROUP BY p.cod_inventar, p.nume, p.categorie "
                + "ORDER BY total_unitati DESC LIMIT ?";
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("codInventar", resultSet.getInt("cod_inventar"));
                    row.put("nume", resultSet.getString("nume"));
                    row.put("categorie", resultSet.getString("categorie"));
                    row.put("totalUnitati", resultSet.getInt("total_unitati"));
                    row.put("totalLei", resultSet.getInt("total_lei"));
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to run join query for vanzari", e);
        }
        return result;
    }

    private VanzareRecord mapRow(ResultSet resultSet) throws SQLException {
        return new VanzareRecord(
                resultSet.getInt("cod_produs"),
                resultSet.getString("nume_produs"),
                resultSet.getInt("cantitate"),
                resultSet.getInt("pret_unitar"),
                resultSet.getString("categorie"),
                resultSet.getTimestamp("timestamp").toLocalDateTime());
    }
}
