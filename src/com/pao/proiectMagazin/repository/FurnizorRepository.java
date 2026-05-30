package com.pao.proiectMagazin.repository;

import com.pao.proiectMagazin.modele.Furnizor;
import com.pao.proiectMagazin.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FurnizorRepository implements Repository<Furnizor, String> {
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @Override
    public void save(Furnizor entity) {
        String sql = "INSERT INTO furnizor (cui, nume, adresa, telefon, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getCui());
            statement.setString(2, entity.getNume());
            statement.setString(3, entity.getAdresa());
            statement.setString(4, entity.getTelefon());
            statement.setString(5, entity.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save furnizor", e);
        }
    }

    @Override
    public Optional<Furnizor> findById(String cui) {
        String sql = "SELECT cui, nume, adresa, telefon, email FROM furnizor WHERE cui = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cui);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find furnizor", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Furnizor> findAll() {
        String sql = "SELECT cui, nume, adresa, telefon, email FROM furnizor";
        List<Furnizor> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to list furnizori", e);
        }
        return result;
    }

    @Override
    public void update(Furnizor entity) {
        String sql = "UPDATE furnizor SET nume = ?, adresa = ?, telefon = ?, email = ? WHERE cui = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getAdresa());
            statement.setString(3, entity.getTelefon());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getCui());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update furnizor", e);
        }
    }

    @Override
    public void delete(String cui) {
        String sql = "DELETE FROM furnizor WHERE cui = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cui);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete furnizor", e);
        }
    }

    private Furnizor mapRow(ResultSet resultSet) throws SQLException {
        return new Furnizor(
                resultSet.getString("nume"),
                resultSet.getString("adresa"),
                resultSet.getString("telefon"),
                resultSet.getString("email"),
                resultSet.getString("cui"));
    }
}
