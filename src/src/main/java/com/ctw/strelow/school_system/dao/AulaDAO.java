package com.ctw.strelow.school_system.dao;

import com.ctw.strelow.school_system.model.Aula;
import com.ctw.strelow.school_system.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AulaDAO {

    public Aula save(Aula aula) {
        String query = "INSERT INTO aula (turma_id, data_hora, assunto) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, aula.getTurma_id());
            stmt.setTimestamp(2, Timestamp.valueOf(aula.getData_hora()));
            stmt.setString(3, aula.getAssunto());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aula.setId(generatedKeys.getInt(1));
                }
            }

            return aula;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aula ", e);
        }
    }

    public List<Aula> findAll() {
        String query = "SELECT id, turma_id, data_hora, assunto FROM aula";
        List<Aula> aulas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                aulas.add(new Aula(
                        rs.getInt("id"),
                        rs.getInt("turma_id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        rs.getString("assunto")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aulas", e);
        }

        return aulas;
    }

    public Aula findById(int id) {
        String query = "SELECT id, turma_id, data_hora, assunto FROM aula WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aula(
                            rs.getInt("id"),
                            rs.getInt("turma_id"),
                            rs.getTimestamp("data_hora").toLocalDateTime(),
                            rs.getString("assunto")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aula por ID ", e);
        }

        return null;
    }

    public Aula update(Aula aula) {
        String query = "UPDATE aula SET turma_id = ?, data_hora = ?, assunto = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, aula.getTurma_id());
            stmt.setTimestamp(2, Timestamp.valueOf(aula.getData_hora()));
            stmt.setString(3, aula.getAssunto());
            stmt.setInt(4, aula.getId());
            stmt.executeUpdate();

            return aula;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aula ", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM aula WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aula ", e);
        }
    }

}