package com.ctw.strelow.school_system.dao;

import com.ctw.strelow.school_system.model.Turma;
import com.ctw.strelow.school_system.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TurmaDAO {

    public Turma save(Turma turma) {
        String query = "INSERT INTO turma (nome, curso_id, professor_id) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getCurso_id());
            stmt.setInt(3, turma.getProfessor_id());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    turma.setId(rs.getInt(1));
                }
            }

            return turma;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar turma no banco de dados ", e);
        }
    }

    public List<Turma> findAll() {
        String query = "SELECT id, nome, curso_id, professor_id  FROM turma";
        List<Turma> turmas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                turmas.add(new Turma(rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("curso_id"),
                        rs.getInt("professor_id")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar turmas ", e);
        }

        return turmas;
    }

    public Turma findById(int id) {
        String query = "SELECT * FROM turma WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Turma turma = new Turma();
                    turma.setId(rs.getInt("id"));
                    turma.setNome(rs.getString("nome"));
                    turma.setCurso_id(rs.getInt("curso_id"));
                    turma.setProfessor_id(rs.getInt("professor_id"));

                    return turma;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar turma por ID ", e);
        }

        return null;
    }

    public Turma update(Turma turma) {
        String query = "UPDATE turma SET nome = ?, curso_id = ?, professor_id = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, turma.getNome());
            stmt.setInt(2, turma.getCurso_id());
            stmt.setInt(3, turma.getProfessor_id());
            stmt.setInt(4, turma.getId());
            stmt.executeUpdate();

            return turma;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar turma ", e);
        }
    }

    public void delete(int id) {
        removerTodosAlunosDaTurma(id);

        String query = "DELETE FROM turma WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar turma ", e);
        }
    }

    // Gerir a tabela turma_aluno
    public void matricularAlunos(int id, List<Integer> alunoIds) {
        if (alunoIds == null || alunoIds.isEmpty()) return;
        String query = "INSERT INTO turma_aluno (turma_id, aluno_id) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int alunoId : alunoIds) {
                stmt.setInt(1, id);
                stmt.setInt(2, alunoId);
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao matricular alunos na turma ", e);
        }
    }

    public void removerTodosAlunosDaTurma(int id) {
        String query = "DELETE FROM turma_aluno WHERE turma_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover alunos da turma ", e);
        }
    }

    // Metodo JOIN
    public List<String> findNomesAlunosByTurmaId(int id) {
        String query = """
                SELECT a.nome FROM aluno a
                JOIN turma_aluno ta ON a.id = ta.aluno_id
                WHERE ta.turma_id = ?;
                """;
        List<String> nomes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nomes.add(rs.getString("nome"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar nomes dos alunos via JOIN ", e);
        }

        return nomes;
    }

}