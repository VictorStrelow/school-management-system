package com.ctw.strelow.school_system.dao;

import com.ctw.strelow.school_system.model.Curso;
import com.ctw.strelow.school_system.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoDAO {

    public Curso save(Curso curso) {
        String query = "INSERT INTO curso (nome, codigo) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCodigo());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    curso.setId(rs.getInt(1));
                }
            }

            return curso;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar curso no banco de dados ", e);
        }
    }

    public List<Curso> findAll() {
        String query = "SELECT id, nome, codigo FROM curso";
        List<Curso> cursos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cursos.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("codigo")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cursos ", e);
        }

        return cursos;
    }

    public Curso findById(int id) {
        String query = "SELECT id, nome, codigo FROM curso WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("codigo")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso por ID ", e);
        }

        return null;
    }

    // Metodo do JOIN
    public List<String> findNomesProfessoresByCursoId(int id) {
        String query = """
                SELECT DISTINCT p.nome FROM professor p
                JOIN turma t ON p.id = t.professor_id
                WHERE t.curso_id = ?;
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
            throw new  RuntimeException("Erro ao buscar professores do curso ", e);
        }

        return  nomes;
    }

    public Curso update(Curso curso) {
        String query = "UPDATE curso SET nome = ?, codigo = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getCodigo());
            stmt.setInt(3, curso.getId());
            stmt.executeUpdate();

            return curso;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso ", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM curso WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso ", e);
        }
    }

}