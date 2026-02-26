package com.ctw.strelow.school_system.dao;

import com.ctw.strelow.school_system.model.Professor;
import com.ctw.strelow.school_system.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfessorDAO {

    public Professor save(Professor professor) {
        String query = "INSERT INTO professor(nome, email, disciplina) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getDisciplina());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    professor.setId(rs.getInt(1));
                }
            }

            return professor;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar professor no banco de dados ", e);
        }
    }

    public List<Professor> findAll() {
        String query = "SELECT id, nome, email, disciplina FROM professor";
        List<Professor> professors = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                professors.add(new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("disciplina")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professores ", e);
        }

        return professors;
    }

    public Professor findById(int id) {
        String query = "SELECT id, nome, email, disciplina FROM professor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Professor(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("disciplina")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor por ID ", e);
        }

        return null;
    }

    public Professor update(Professor professor) {
        String query = "UPDATE professor SET nome = ?, email = ?, disciplina = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getDisciplina());
            stmt.setInt(4, professor.getId());
            stmt.executeUpdate();

            return professor;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor ", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM professor WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar professor ", e);
        }
    }

}