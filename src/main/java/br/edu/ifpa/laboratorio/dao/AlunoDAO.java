package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.ConexaoMySQL;
import br.edu.ifpa.laboratorio.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

    // Método para salvar um novo aluno no banco de dados (RF01)
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, matricula) VALUES (?, ?)";

        // O try-with-resources já fecha a conexão automaticamente no final
        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // Trocando os '?' pelos valores do objeto aluno
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());

            stmt.executeUpdate();
            System.out.println("Aluno " + aluno.getNome() + " cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
        }
    }

    // Método extra útil para buscar um aluno pelo ID na hora do empréstimo
    public Aluno buscarPorId(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        Aluno aluno = null;

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setMatricula(rs.getString("matricula"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
        }
        return aluno;
    }
}
