Sistema de Controle de Emprestimo de Equipamentos.
Integrante
Eduardo Antonio Fernandes de Sousa

Descricao do problema:
O projeto resolve a necessidade de organizar o fluxo de entrada e saida de materiais em um laboratorio academico. O foco e garantir que um equipamento nao seja emprestado para dois alunos ao mesmo tempo e manter um registro historico de todas as operacoes para fins de auditoria e inventario.
Requisitos implementados:
Cadastro de alunos e equipamentos no banco de dados.
Listagem de itens disponiveis para consulta.
Registro de emprestimo com validacao de disponibilidade.
Atualizacao automatica de status do equipamento (disponivel/indisponivel).
Registro de devolucao com marcacao de data e hora final.
Consulta de historico e emprestimos ativos.

Diagramas UML:
Os artefatos estao inclusos na raiz do projeto:
Casos de Uso: Define as interacoes do administrador com o sistema.
Diagrama de Classes: Estrutura das entidades e camadas DAO.
Diagrama de Sequencia: Detalhamento do processo de realizacao de um emprestimo.
Modelo do banco de dados:
O banco de dados foi estruturado em MySQL com as tabelas aluno, equipamento e emprestimo. O modelo relacional foi gerado via MySQL Workbench e respeita as chaves primarias e estrangeiras necessarias para a integridade dos dados.

Como criar o banco MySQL:
Execute o arquivo script_banco.sql no seu servidor local MySQL.
O script criara o schema controle_laboratorio e inserira dados iniciais para facilitar os testes.
Como executar o projeto Java
Importe o projeto em uma IDE (IntelliJ ou Eclipse).
O projeto utiliza Maven, entao o driver do MySQL sera baixado automaticamente.
Configure a senha do banco em ConexaoMySQL.java.
Execute a classe Main.java para rodar a rotina de testes.

Testes realizados:
O sistema passou pelos seguintes cenarios:
Cadastro de novo aluno e novo equipamento: OK.
Listagem de itens: Exibe apenas os que possuem status disponivel.
Emprestimo: Registra o log no banco e altera o status do item para indisponivel.
Bloqueio de item: Tentativa de emprestar item ja ocupado retorna erro de negocio.
Devolucao: Finaliza o registro e libera o item para novo uso imediatamente.

Decisoes de projeto:
Padrao DAO (Data Access Object) para isolar o codigo SQL das regras de negocio.
Uso de java.time para manipulacao precisa de datas e horarios.
Tratamento de excecoes SQL para evitar interrupcoes bruscas no programa.

Melhorias futuras:
Desenvolvimento de interface grafica (Swing ou JavaFX).
Implementacao de autenticacao de usuario.
Relatorios de equipamentos mais utilizados.
