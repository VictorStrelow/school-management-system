package com.ctw.strelow.school_system.dao;

import com.ctw.strelow.school_system.model.Aluno;
import com.ctw.strelow.school_system.utils.ConnectionFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoDAO {

    private final ResourceLoader resourceLoader;

    public AlunoDAO(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Aluno save(Aluno aluno) {
        String query = "INSERT INTO aluno (nome, email, matricula, data_nascimento) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getMatricula());
            stmt.setDate(4, Date.valueOf(aluno.getData_nascimento()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aluno.setId(rs.getInt(1));
                }
            }

            return aluno;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno no banco de dados ", e);
        }
    }

    public List<Aluno>  findAll() {
        String query = "SELECT id, nome, email, matricula, data_nascimento FROM aluno";
        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                alunos.add(new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("matricula"),
                        rs.getDate("data_nascimento").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar alunos ", e);
        }

        return alunos;
    }

    public Aluno findById(int id) {
        String query = "SELECT id, nome, email, matricula, data_nascimento FROM aluno WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("matricula"),
                            rs.getDate("data_nascimento").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por ID", e);
        }

        return null;
    }

    public Aluno update(Aluno aluno) {
        String query = "UPDATE aluno SET nome = ?, email = ?, matricula = ?, data_nascimento = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getMatricula());
            stmt.setDate(4, Date.valueOf(aluno.getData_nascimento()));
            stmt.setInt(5, aluno.getId());
            stmt.executeUpdate();

            return aluno;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno ", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno ", e);
        }
    }

}