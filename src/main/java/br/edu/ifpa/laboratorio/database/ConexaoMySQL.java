package br.edu.ifpa.laboratorio.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    // Caminho do banco (localhost, porta 3306, nome do banco)
    private static final String URL = "jdbc:mysql://localhost:3306/controle_laboratorio";
    private static final String USER = "root";

    // ATENÇÃO: Coloque a senha que você configurou no MySQL aqui! Se for vazia, deixe ""
    private static final String PASSWORD = "";

    public static Connection obterConexao() {
        try {
            // Tenta estabelecer a conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            return null;
        }
    }
}