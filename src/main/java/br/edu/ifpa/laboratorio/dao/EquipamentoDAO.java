package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.ConexaoMySQL;
import br.edu.ifpa.laboratorio.model.Equipamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDAO {

    // Método para cadastrar um novo equipamento (RF02)
    public void salvar(Equipamento equipamento) {
        String sql = "INSERT INTO equipamento (nome, descricao, disponivel) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, equipamento.getNome());
            stmt.setString(2, equipamento.getDescricao());
            stmt.setBoolean(3, equipamento.isDisponivel());

            stmt.executeUpdate();
            System.out.println("Equipamento " + equipamento.getNome() + " cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar equipamento: " + e.getMessage());
        }
    }

    // Método para listar apenas os equipamentos disponíveis (RF03 / RN01)
    public List<Equipamento> listarDisponiveis() {
        String sql = "SELECT * FROM equipamento WHERE disponivel = TRUE";
        List<Equipamento> equipamentos = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipamento eq = new Equipamento();
                eq.setId(rs.getInt("id"));
                eq.setNome(rs.getString("nome"));
                eq.setDescricao(rs.getString("descricao"));
                eq.setDisponivel(rs.getBoolean("disponivel"));

                equipamentos.add(eq);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar equipamentos disponíveis: " + e.getMessage());
        }
        return equipamentos;
    }

    // Método para alterar a disponibilidade (TRUE ou FALSE) após empréstimo ou devolução (RF06 / RF08)
    public void atualizarDisponibilidade(int id, boolean disponivel) {
        String sql = "UPDATE equipamento SET disponivel = ? WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disponibilidade do equipamento: " + e.getMessage());
        }
    }

    // Método extra para buscar um equipamento específico pelo ID
    public Equipamento buscarPorId(int id) {
        String sql = "SELECT * FROM equipamento WHERE id = ?";
        Equipamento equipamento = null;

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                equipamento = new Equipamento();
                equipamento.setId(rs.getInt("id"));
                equipamento.setNome(rs.getString("nome"));
                equipamento.setDescricao(rs.getString("descricao"));
                equipamento.setDisponivel(rs.getBoolean("disponivel"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar equipamento: " + e.getMessage());
        }
        return equipamento;
    }
}