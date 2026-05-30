package com.pao.proiectMagazin.repository;

import com.pao.proiectMagazin.modele.ModificareStocId;
import com.pao.proiectMagazin.modele.ModificareStocRecord;
import com.pao.proiectMagazin.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModificareStocRepository implements Repository<ModificareStocRecord, ModificareStocId> {
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @Override
    public void save(ModificareStocRecord entity) {
        String sql = "INSERT INTO modificare_stoc (cod_produs, nume_produs, stoc_anterior, stoc_nou, diferenta, motiv, uid_utilizator, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getCodProdus());
            statement.setString(2, entity.getNumeProdus());
            statement.setInt(3, entity.getStocAnterior());
            statement.setInt(4, entity.getStocNou());
            statement.setInt(5, entity.getDiferenta());
            statement.setString(6, entity.getMotiv());
            statement.setInt(7, entity.getUidUtilizator());
            statement.setTimestamp(8, Timestamp.valueOf(entity.getTimestamp()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save modificare_stoc", e);
        }
    }

    @Override
    public Optional<ModificareStocRecord> findById(ModificareStocId id) {
        String sql = "SELECT * FROM modificare_stoc WHERE cod_produs = ? AND timestamp = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id.getCodProdus());
            statement.setTimestamp(2, Timestamp.valueOf(id.getTimestamp()));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find modificare_stoc", e);
        }
        return Optional.empty();
    }

    @Override
    public List<ModificareStocRecord> findAll() {
        String sql = "SELECT * FROM modificare_stoc";
        List<ModificareStocRecord> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to list modificari stoc", e);
        }
        return result;
    }

    @Override
    public void update(ModificareStocRecord entity) {
        String sql = "UPDATE modificare_stoc SET nume_produs = ?, stoc_anterior = ?, stoc_nou = ?, diferenta = ?, motiv = ?, uid_utilizator = ? WHERE cod_produs = ? AND timestamp = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getNumeProdus());
            statement.setInt(2, entity.getStocAnterior());
            statement.setInt(3, entity.getStocNou());
            statement.setInt(4, entity.getDiferenta());
            statement.setString(5, entity.getMotiv());
            statement.setInt(6, entity.getUidUtilizator());
            statement.setInt(7, entity.getCodProdus());
            statement.setTimestamp(8, Timestamp.valueOf(entity.getTimestamp()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update modificare_stoc", e);
        }
    }

    @Override
    public void delete(ModificareStocId id) {
        String sql = "DELETE FROM modificare_stoc WHERE cod_produs = ? AND timestamp = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id.getCodProdus());
            statement.setTimestamp(2, Timestamp.valueOf(id.getTimestamp()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete modificare_stoc", e);
        }
    }

    public List<Map<String, Object>> findStockChangesWithUser() {
        String sql = "SELECT m.cod_produs, m.nume_produs, m.stoc_anterior, m.stoc_nou, m.diferenta, m.motiv, m.uid_utilizator, u.nume, u.prenume, m.timestamp "
                + "FROM modificare_stoc m JOIN utilizator u ON m.uid_utilizator = u.uid ORDER BY m.timestamp DESC";
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("codProdus", resultSet.getInt("cod_produs"));
                row.put("numeProdus", resultSet.getString("nume_produs"));
                row.put("stocAnterior", resultSet.getInt("stoc_anterior"));
                row.put("stocNou", resultSet.getInt("stoc_nou"));
                row.put("diferenta", resultSet.getInt("diferenta"));
                row.put("motiv", resultSet.getString("motiv"));
                row.put("uidUtilizator", resultSet.getInt("uid_utilizator"));
                row.put("numeUtilizator", resultSet.getString("nume") + " " + resultSet.getString("prenume"));
                row.put("timestamp", resultSet.getTimestamp("timestamp").toLocalDateTime());
                result.add(row);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to run join query for modificari stoc", e);
        }
        return result;
    }

    private ModificareStocRecord mapRow(ResultSet resultSet) throws SQLException {
        return new ModificareStocRecord(
                resultSet.getInt("cod_produs"),
                resultSet.getString("nume_produs"),
                resultSet.getInt("stoc_anterior"),
                resultSet.getInt("stoc_nou"),
                resultSet.getString("motiv"),
                resultSet.getInt("uid_utilizator"),
                resultSet.getTimestamp("timestamp").toLocalDateTime());
    }
}
