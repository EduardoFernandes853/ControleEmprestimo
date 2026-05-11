package br.edu.ifpa.laboratorio;

import br.edu.ifpa.laboratorio.dao.AlunoDAO;
import br.edu.ifpa.laboratorio.dao.EmprestimoDAO;
import br.edu.ifpa.laboratorio.dao.EquipamentoDAO;
import br.edu.ifpa.laboratorio.model.Aluno;
import br.edu.ifpa.laboratorio.model.Emprestimo;
import br.edu.ifpa.laboratorio.model.Equipamento;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE CONTROLE DE LABORATÓRIO ===");

        AlunoDAO alunoDAO = new AlunoDAO();
        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

        System.out.println("\n--- TESTE 1: Cadastrar Aluno e Equipamento ---");
        Aluno novoAluno = new Aluno(0, "Carlos Andrade", "2024101");
        alunoDAO.salvar(novoAluno);

        Equipamento novoEquipamento = new Equipamento(0, "Cabo de Rede 5m", "Cabo CAT6", true);
        equipamentoDAO.salvar(novoEquipamento);

        System.out.println("\n--- TESTE 2: Listar Equipamentos Disponíveis ---");
        List<Equipamento> disponiveis = equipamentoDAO.listarDisponiveis();
        for (Equipamento eq : disponiveis) {
            System.out.println("ID: " + eq.getId() + " | Nome: " + eq.getNome() + " | Disponível: " + eq.isDisponivel());
        }

        System.out.println("\n--- TESTE 3: Realizar Empréstimo Válido ---");
        // Vamos buscar o aluno de ID 1 e o equipamento de ID 2 (do script original do banco)
        Aluno alunoEmprestimo = alunoDAO.buscarPorId(1);
        Equipamento equipamentoEmprestimo = equipamentoDAO.buscarPorId(2);

        if (alunoEmprestimo != null && equipamentoEmprestimo != null && equipamentoEmprestimo.isDisponivel()) {
            Emprestimo emp = new Emprestimo();
            emp.setAluno(alunoEmprestimo);
            emp.setEquipamento(equipamentoEmprestimo);
            emp.setDataEmprestimo(LocalDateTime.now());
            emprestimoDAO.registrarEmprestimo(emp);
        }

        System.out.println("\n--- TESTE 4: Tentar Emprestar Equipamento Indisponível ---");
        // O equipamento 2 acabou de ser emprestado, vamos tentar emprestá-lo novamente
        Equipamento eqIndisponivel = equipamentoDAO.buscarPorId(2);
        if (eqIndisponivel != null && !eqIndisponivel.isDisponivel()) {
            System.out.println("BLOQUEADO: O equipamento '" + eqIndisponivel.getNome() + "' não está disponível para empréstimo.");
        }

        System.out.println("\n--- TESTE 5: Registrar Devolução ---");
        // Pega o último empréstimo ativo para devolver (supondo que seja o ID 2 recém-criado)
        List<Emprestimo> ativos = emprestimoDAO.listarAtivos();
        if (!ativos.isEmpty()) {
            int idParaDevolver = ativos.get(ativos.size() - 1).getId();
            emprestimoDAO.registrarDevolucao(idParaDevolver);
        }

        System.out.println("\n=== TESTES FINALIZADOS ===");
    }
}