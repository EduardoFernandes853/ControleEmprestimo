package br.edu.ifpa.laboratorio.dao;

import br.edu.ifpa.laboratorio.database.ConexaoMySQL;
import br.edu.ifpa.laboratorio.model.Aluno;
import br.edu.ifpa.laboratorio.model.Emprestimo;
import br.edu.ifpa.laboratorio.model.Equipamento;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    private EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();

    // RF04 e RN03: Realizar empréstimo e atualizar equipamento
    public void registrarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id_aluno, id_equipamento, data_emprestimo, status) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, emprestimo.getAluno().getId());
            stmt.setInt(2, emprestimo.getEquipamento().getId());
            // Converte LocalDateTime para Timestamp do SQL
            stmt.setTimestamp(3, Timestamp.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setString(4, "ATIVO");

            stmt.executeUpdate();

            // RN03: Atualiza o equipamento para indisponível (FALSE)
            equipamentoDAO.atualizarDisponibilidade(emprestimo.getEquipamento().getId(), false);

            System.out.println("Empréstimo registrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao registrar empréstimo: " + e.getMessage());
        }
    }

    // RF07 e RN04: Registrar devolução e liberar equipamento
    public void registrarDevolucao(int idEmprestimo) {
        String sqlBusca = "SELECT id_equipamento FROM emprestimo WHERE id = ?";
        String sqlUpdate = "UPDATE emprestimo SET data_devolucao = ?, status = 'FINALIZADO' WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmtBusca = conexao.prepareStatement(sqlBusca);
             PreparedStatement stmtUpdate = conexao.prepareStatement(sqlUpdate)) {

            // 1. Busca qual equipamento estava emprestado
            stmtBusca.setInt(1, idEmprestimo);
            ResultSet rs = stmtBusca.executeQuery();
            int idEquipamento = -1;
            if (rs.next()) {
                idEquipamento = rs.getInt("id_equipamento");
            }

            if (idEquipamento != -1) {
                // 2. Registra a data de devolução atual e finaliza o status
                stmtUpdate.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                stmtUpdate.setInt(2, idEmprestimo);
                stmtUpdate.executeUpdate();

                // 3. RN04: Libera o equipamento novamente (TRUE)
                equipamentoDAO.atualizarDisponibilidade(idEquipamento, true);
                System.out.println("Devolução registrada com sucesso! Equipamento liberado.");
            } else {
                System.out.println("Empréstimo não encontrado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
        }
    }

    // RF09: Consultar empréstimos ativos
    public List<Emprestimo> listarAtivos() {
        return buscarEmprestimos("SELECT * FROM emprestimo WHERE status = 'ATIVO'");
    }

    // RF10: Consultar histórico
    public List<Emprestimo> listarHistorico() {
        return buscarEmprestimos("SELECT * FROM emprestimo");
    }

    // Método auxiliar privado para não repetir código de busca
    private List<Emprestimo> buscarEmprestimos(String sql) {
        List<Emprestimo> lista = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emprestimo emp = new Emprestimo();
                emp.setId(rs.getInt("id"));
                emp.setStatus(rs.getString("status"));

                if (rs.getTimestamp("data_emprestimo") != null) {
                    emp.setDataEmprestimo(rs.getTimestamp("data_emprestimo").toLocalDateTime());
                }
                if (rs.getTimestamp("data_devolucao") != null) {
                    emp.setDataDevolucao(rs.getTimestamp("data_devolucao").toLocalDateTime());
                }

                // Vai no banco buscar os dados do aluno e do equipamento referentes a este empréstimo
                Aluno aluno = alunoDAO.buscarPorId(rs.getInt("id_aluno"));
                Equipamento equipamento = equipamentoDAO.buscarPorId(rs.getInt("id_equipamento"));

                emp.setAluno(aluno);
                emp.setEquipamento(equipamento);

                lista.add(emp);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos: " + e.getMessage());
        }
        return lista;
    }
}