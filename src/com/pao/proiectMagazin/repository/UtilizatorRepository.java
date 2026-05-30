package com.pao.proiectMagazin.repository;

import com.pao.proiectMagazin.modele.Angajat;
import com.pao.proiectMagazin.modele.Manager;
import com.pao.proiectMagazin.modele.Utilizator;
import com.pao.proiectMagazin.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilizatorRepository implements Repository<Utilizator, Integer> {
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @Override
    public void save(Utilizator entity) {
        String sql = "INSERT INTO utilizator (uid, username, nume, prenume, salariu, cnp, adresa, telefon, email, parola, data_nasterii, data_angajare, data_concediere, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getUid());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getNume());
            statement.setString(4, entity.getPrenume());
            statement.setInt(5, entity.getSalariu());
            statement.setString(6, entity.getCnp());
            statement.setString(7, entity.getAdresa());
            statement.setString(8, entity.getTelefon());
            statement.setString(9, entity.getEmail());
            statement.setString(10, entity.getParola());
            statement.setString(11, entity.getDataNasterii());
            statement.setString(12, entity.getDataAngajare());
            statement.setString(13, entity.getDataConcediere());
            statement.setString(14, entity.getRol());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save utilizator", e);
        }
    }

    @Override
    public Optional<Utilizator> findById(Integer uid) {
        String sql = "SELECT * FROM utilizator WHERE uid = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, uid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to find utilizator", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Utilizator> findAll() {
        String sql = "SELECT * FROM utilizator";
        List<Utilizator> result = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to list utilizatori", e);
        }
        return result;
    }

    @Override
    public void update(Utilizator entity) {
        String sql = "UPDATE utilizator SET username = ?, nume = ?, prenume = ?, salariu = ?, cnp = ?, adresa = ?, telefon = ?, email = ?, parola = ?, data_nasterii = ?, data_angajare = ?, data_concediere = ?, rol = ? WHERE uid = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getNume());
            statement.setString(3, entity.getPrenume());
            statement.setInt(4, entity.getSalariu());
            statement.setString(5, entity.getCnp());
            statement.setString(6, entity.getAdresa());
            statement.setString(7, entity.getTelefon());
            statement.setString(8, entity.getEmail());
            statement.setString(9, entity.getParola());
            statement.setString(10, entity.getDataNasterii());
            statement.setString(11, entity.getDataAngajare());
            statement.setString(12, entity.getDataConcediere());
            statement.setString(13, entity.getRol());
            statement.setInt(14, entity.getUid());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update utilizator", e);
        }
    }

    @Override
    public void delete(Integer uid) {
        String sql = "DELETE FROM utilizator WHERE uid = ?";
        try (Connection connection = databaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, uid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete utilizator", e);
        }
    }

    private Utilizator mapRow(ResultSet resultSet) throws SQLException {
        String rol = resultSet.getString("rol");
        if ("Manager".equalsIgnoreCase(rol)) {
            return new Manager(
                    resultSet.getString("username"),
                    resultSet.getString("nume"),
                    resultSet.getString("prenume"),
                    resultSet.getInt("salariu"),
                    resultSet.getString("cnp"),
                    resultSet.getString("adresa"),
                    resultSet.getString("telefon"),
                    resultSet.getString("email"),
                    resultSet.getString("parola"),
                    resultSet.getString("data_nasterii"),
                    resultSet.getString("data_angajare"),
                    resultSet.getInt("uid"));
        }
        Angajat angajat = new Angajat(
                resultSet.getString("username"),
                resultSet.getString("nume"),
                resultSet.getString("prenume"),
                resultSet.getInt("salariu"),
                resultSet.getString("cnp"),
                resultSet.getString("adresa"),
                resultSet.getString("telefon"),
                resultSet.getString("email"),
                resultSet.getString("parola"),
                resultSet.getString("data_nasterii"),
                resultSet.getString("data_angajare"),
                resultSet.getInt("uid"));
        String dataConcediere = resultSet.getString("data_concediere");
        if (dataConcediere != null && !dataConcediere.isBlank()) {
            angajat.concediere(dataConcediere);
        }
        return angajat;
    }
}
