package com.ctw.strelow.school_system.dao;

import com.ctw.strelow.school_system.model.Nota;
import com.ctw.strelow.school_system.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class NotaDAO {

    public Nota save(Nota nota) {
        String query = "INSERT INTO nota (aluno_id, aula_id, valor) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, nota.getAluno_id());
            stmt.setInt(2, nota.getAula_id());
            stmt.setDouble(3, nota.getValor());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    nota.setId(rs.getInt(1));
                }
            }

            return nota;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar nota ", e);
        }
    }

    public List<Nota> findAll() {
        String query = "SELECT id, aluno_id, aula_id, valor FROM nota";
        List<Nota> notas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setAluno_id(rs.getInt("aluno_id"));
                nota.setAula_id(rs.getInt("aula_id"));
                nota.setValor(rs.getDouble("valor"));
                notas.add(nota);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar notas ", e);
        }

        return notas;
    }

    public Nota findById(int id) {
        String query = "SELECT id, aluno_id, aula_id, valor FROM nota WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Nota nota = new Nota();
                    nota.setId(rs.getInt("id"));
                    nota.setAluno_id(rs.getInt("aluno_id"));
                    nota.setAula_id(rs.getInt("aula_id"));
                    nota.setValor(rs.getDouble("valor"));

                    return nota;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar nota ", e);
        }

        return null;
    }

    public Nota update(Nota nota) {
        String query = "UPDATE nota SET aluno_id = ?, aula_id = ?, valor = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, nota.getAluno_id());
            stmt.setInt(2, nota.getAula_id());
            stmt.setDouble(3, nota.getValor());
            stmt.setInt(4, nota.getId());
            stmt.executeUpdate();

            return nota;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar nota ", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM nota WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar nota", e);
        }
    }

    // Endpoint
    public List<Nota> findByAlunoId(int alunoId) {
        String query = "SELECT id, aluno_id, aula_id, valor FROM nota WHERE aluno_id = ?";
        List<Nota> notas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, alunoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Nota nota = new Nota();
                    nota.setId(rs.getInt("id"));
                    nota.setAluno_id(rs.getInt("aluno_id"));
                    nota.setAula_id(rs.getInt("aula_id"));
                    nota.setValor(rs.getDouble("valor"));
                    notas.add(nota);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar notas do aluno", e);
        }

        return notas;
    }

}