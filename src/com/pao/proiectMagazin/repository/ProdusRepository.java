package com.pao.proiectMagazin.repository;

import com.pao.proiectMagazin.modele.Produs;
import com.pao.proiectMagazin.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProdusRepository implements Repository<Produs, Integer> {
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @Override
    public void save(Produs entity) {
        String sql = "INSERT INTO produs (cod_inventar, nume, pret_cumparare, pret_vanzare, categorie, cui_furnizor, stoc_minim, stoc, procent_reducere) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getCodInventar());
            statement.setString(2, entity.getNume());
            statement.setInt(3, entity.getPretCumparare());
            statement.setInt(4, entity.getPretVanzare());
            statement.setString(5, entity.getCategorie());
            statement.setString(6, entity.getCuiFurnizor());
            statement.setInt(7, entity.getStocMinim());
            statement.setInt(8, entity.getStoc());
            statement.setInt(9, entity.getProcentReducere());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save produs", e);
        }
        insertSpecificatii(entity.getCodInventar(), entity.getSpecificatii());
    }

    @Override
    public Optional<Produs> findById(Integer codInventar) {
        String sql = "SELECT cod_inventar, nume, pret_cumparare, pret_vanzare, categorie, cui_furnizor, stoc_minim, stoc, procent_reducere FROM produs WHERE cod_inventar = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codInventar);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet, connection));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find produs", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Produs> findAll() {
        String sql = "SELECT cod_inventar, nume, pret_cumparare, pret_vanzare, categorie, cui_furnizor, stoc_minim, stoc, procent_reducere FROM produs";
        List<Produs> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet, connection));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to list produse", e);
        }
        return result;
    }

    @Override
    public void update(Produs entity) {
        String sql = "UPDATE produs SET nume = ?, pret_cumparare = ?, pret_vanzare = ?, categorie = ?, cui_furnizor = ?, stoc_minim = ?, stoc = ?, procent_reducere = ? WHERE cod_inventar = ?";
        try (Connection connection = databaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, entity.getNume());
                    statement.setInt(2, entity.getPretCumparare());
                    statement.setInt(3, entity.getPretVanzare());
                    statement.setString(4, entity.getCategorie());
                    statement.setString(5, entity.getCuiFurnizor());
                    statement.setInt(6, entity.getStocMinim());
                    statement.setInt(7, entity.getStoc());
                    statement.setInt(8, entity.getProcentReducere());
                    statement.setInt(9, entity.getCodInventar());
                    statement.executeUpdate();
                }
                deleteSpecificatii(connection, entity.getCodInventar());
                insertSpecificatii(connection, entity.getCodInventar(), entity.getSpecificatii());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update produs", e);
        }
    }

    @Override
    public void delete(Integer codInventar) {
        String deleteSpecs = "DELETE FROM produs_specificatie WHERE cod_inventar = ?";
        String deleteProdus = "DELETE FROM produs WHERE cod_inventar = ?";
        try (Connection connection = databaseConnection.getConnection()) {
            try (PreparedStatement specStatement = connection.prepareStatement(deleteSpecs)) {
                specStatement.setInt(1, codInventar);
                specStatement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(deleteProdus)) {
                statement.setInt(1, codInventar);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete produs", e);
        }
    }

    public List<Map<String, Object>> findProductsWithSupplier() {
        String sql = "SELECT p.cod_inventar, p.nume, p.categorie, p.stoc, p.pret_vanzare, f.nume AS furnizor_nume "
                + "FROM produs p JOIN furnizor f ON p.cui_furnizor = f.cui ORDER BY p.nume";
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("codInventar", resultSet.getInt("cod_inventar"));
                row.put("nume", resultSet.getString("nume"));
                row.put("categorie", resultSet.getString("categorie"));
                row.put("stoc", resultSet.getInt("stoc"));
                row.put("pretVanzare", resultSet.getInt("pret_vanzare"));
                row.put("numeFurnizor", resultSet.getString("furnizor_nume"));
                result.add(row);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to run join query for produse", e);
        }
        return result;
    }

    private Produs mapRow(ResultSet resultSet, Connection connection) throws SQLException {
        int codInventar = resultSet.getInt("cod_inventar");
        List<String> specificatii = loadSpecificatii(connection, codInventar);
        return new Produs(
                codInventar,
                resultSet.getString("nume"),
                resultSet.getInt("pret_cumparare"),
                resultSet.getInt("pret_vanzare"),
                resultSet.getString("categorie"),
                resultSet.getString("cui_furnizor"),
                resultSet.getInt("stoc_minim"),
                resultSet.getInt("stoc"),
                resultSet.getInt("procent_reducere"),
                specificatii);
    }

    private List<String> loadSpecificatii(Connection connection, int codInventar) throws SQLException {
        String sql = "SELECT specificatie FROM produs_specificatie WHERE cod_inventar = ?";
        List<String> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codInventar);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getString("specificatie"));
                }
            }
        }
        return result;
    }

    private void insertSpecificatii(int codInventar, List<String> specificatii) {
        try (Connection connection = databaseConnection.getConnection()) {
            insertSpecificatii(connection, codInventar, specificatii);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save specificatii", e);
        }
    }

    private void insertSpecificatii(Connection connection, int codInventar, List<String> specificatii) throws SQLException {
        if (specificatii == null || specificatii.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO produs_specificatie (cod_inventar, specificatie) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String spec : specificatii) {
                statement.setInt(1, codInventar);
                statement.setString(2, spec);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void deleteSpecificatii(Connection connection, int codInventar) throws SQLException {
        String sql = "DELETE FROM produs_specificatie WHERE cod_inventar = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codInventar);
            statement.executeUpdate();
        }
    }
}
