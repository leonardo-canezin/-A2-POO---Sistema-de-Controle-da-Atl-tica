package Estudo_Dirigido;

//Não funcionou só no package não sei por que, dai tive que colocar esses dois imports
import Estudo_Dirigido.Equipamento.*;
import Estudo_Dirigido.Usuario.*;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GerenciadorAtletica sistema = new GerenciadorAtletica();

        // Alguns cadastros iniciais para testarmos
        sistema.cadastrarUsuario(new Aluno("1", "Lucas", "(43)12312-1231", "Computacao"));
        sistema.cadastrarUsuario(new Funcionario("2", "Carla", "(43)32132-3213", "Secretaria"));
        sistema.cadastrarEquipamento(new Bola(1, "Bom", "Futsal"));
        sistema.cadastrarEquipamento(new Raquete(2, "Novo"));
        sistema.cadastrarEquipamento(new Bicicleta(3, "Bom"));

        //Dei um valor diferente do que vamos usar apenas para inicializar e não ter chance de dar algum erro
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\nMENU:");
            System.out.println("1 - Cadastrar usuario");
            System.out.println("2 - Cadastrar equipamento");
            System.out.println("3 - Fazer emprestimo");
            System.out.println("4 - Devolver Equipamento");
            System.out.println("5 - Ver equipamentos disponiveis");
            System.out.println("6 - Ver equipamentos emprestados");
            System.out.println("7 - Ver historico de um equipamenot");
            System.out.println("8 - Ver usuarios");
            System.out.println("0 - sair");
            System.out.print("Digite: ");

            // Todos os nossos exceptions (Exceto por um que aparece ali no catch final) são textos informando o que deu de errado,
            // e esses textos já são definidos na propria classe em si, então no catch final temos apenas esse catch geral
            try {
                //Essa função é o que interpreta para o sistema o que o usuario escreveu, tipo um scanf
                opcao = Integer.parseInt(sc.nextLine());

                if (opcao == 1) {
                    System.out.print("Digite 1 para Aluno ou 2 para Servidor: ");
                    int tipo = Integer.parseInt(sc.nextLine());

                    //Variaveis padrão para os dois
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();
                
                    //Especificos do aluno no if e funcionario no else
                    if (tipo == 1) {
                        System.out.print("Curso: ");
                        String curso = sc.nextLine();
                        sistema.cadastrarUsuario(new Aluno(id, nome, tel, curso));
                    } else {
                        System.out.print("Setor: ");
                        String setor = sc.nextLine();
                        sistema.cadastrarUsuario(new Funcionario(id, nome, tel, setor));
                    }
                    System.out.println("Cadastro de usuario feito");

                } else if (opcao == 2) {
                    System.out.print("Tipo (1-Bola, 2-Colete, 3-Raquete, 4-Bicicleta): ");
                    int tipo = Integer.parseInt(sc.nextLine());

                    //padrão de todos os equipamentos
                    System.out.print("Codigo: ");
                    int cod = Integer.parseInt(sc.nextLine());
                    System.out.print("Estado de conservacao: ");
                    String estado = sc.nextLine();

                    //Especificas de cada tipo
                    if (tipo == 1) {
                        System.out.print("Modalidade: ");
                        String mod = sc.nextLine();
                        sistema.cadastrarEquipamento(new Bola(cod, estado, mod));
                    } else if (tipo == 2) {
                        System.out.print("Tamanho: ");
                        String tam = sc.nextLine();
                        System.out.print("Cor: ");
                        String cor = sc.nextLine();
                        sistema.cadastrarEquipamento(new Colete(cod, estado, tam, cor));
                    } else if (tipo == 3) {
                        sistema.cadastrarEquipamento(new Raquete(cod, estado));
                    } else if (tipo == 4) {
                        sistema.cadastrarEquipamento(new Bicicleta(cod, estado));
                    }
                    System.out.println("Cadastro de equipamento feito");

                } else if (opcao == 3) {
                    System.out.print("ID do usuario: ");
                    String id = sc.nextLine();
                    System.out.print("Codigo do equipamento: ");
                    int cod = Integer.parseInt(sc.nextLine());
                    System.out.print("Data de retirada (xx/xx/xxxx): ");
                    int dataRet = Integer.parseInt(sc.nextLine());
                    //Realiza o emprestimo em si
                    sistema.realizarEmprestimo(id, cod, dataRet);

                } else if (opcao == 4) {
                    System.out.print("Codigo do Equipamento: ");
                    int cod = Integer.parseInt(sc.nextLine());

                    //Puxa o emprestimo pelo codigo do equipamento e depois pega qual é o equipamento desse emprestimo
                    Emprestimo empAtivo = sistema.buscarEmprestimoAtivoPorEquipamento(cod);
                    Equipamento eq = empAtivo.getEquipamento();

                    System.out.print("Data da devolucao (xx/xx/xxxx): ");
                    int dataDev = Integer.parseInt(sc.nextLine());
                    System.out.print("Estado do equipamento na entrega: ");
                    //Aqui não precisa do integrer porque é String
                    String estado = sc.nextLine();

                    Vistoria v = null;
                    if (eq.precisaVistoria()) { // Só é ativo se for bicicleta
                        System.out.println("Vistoria da Bicicleta");
                        System.out.print("Pneu esta bom? (s/n): ");
                        //Booleanos só são verdadeiros se o texto foi "s" ou "S"
                        boolean pneu = sc.nextLine().equalsIgnoreCase("s");
                        System.out.print("Freio esta bom? (s/n): ");
                        boolean freio = sc.nextLine().equalsIgnoreCase("s");
                        System.out.print("Corrente esta boa? (s/n): ");
                        boolean corrente = sc.nextLine().equalsIgnoreCase("s");
                        System.out.print("Observacao: ");

                        //Cadastra a vistoria na classe
                        v = new Vistoria(pneu, freio, corrente);
                    }

                    //Realiza a devolução em si e informa os dias de bloqueio se necessário. O realizar devolução foi definido
                    //para retornar int, que é a quantidade de dias de atraso
                    int atraso = sistema.realizarDevolucao(cod, dataDev, estado, v);
                    System.out.println("Devolvido com sucesso");
                    if (atraso > 0) {
                        System.out.println("Atrasou " + atraso + " dias. Usuario bloqueado ate: "
                                + empAtivo.getUsuario().getFimBloqueio());
                    }

                } else if (opcao == 5) {
                    System.out.println("Disponiveis:");
                    //Percorre a lista toda e printa
                    for (Equipamento e : sistema.listarEquipamentosDisponiveis()) {
                        System.out.println(e);
                    }

                } else if (opcao == 6) {
                    System.out.println("Emprestados:");
                    //Percorre a lista e diz com quem está e qual a data
                    for (Emprestimo emp : sistema.listarEmprestimosAtivos()) {
                        System.out.println("Cod: " + emp.getEquipamento().getCodigo() + " coom: " + emp.getUsuario().getNome()
                                + " ate: " + emp.getDataPrevistaDevolucao());
                    }

                } else if (opcao == 7) {
                    System.out.print("Codigo do equipamento: ");
                    int codE = Integer.parseInt(sc.nextLine());
                    //nosso consultar historico retorna um array com todo o historico do equipamento
                    List<Emprestimo> hist = sistema.consultarHistoricoEquipamento(codE);

                    System.out.println("Historico:");
                    for (Emprestimo emp : hist) {
                        String resultado;
                        //Pega a data de devolução se existir e da um printf das informações
                        if (emp.getDataRealDevolucao() != 0) {
                            resultado = String.valueOf(emp.getDataRealDevolucao());
                        } else {
                            resultado = "Ainda emprestado";
                        }
                        System.out.println("Emprestimo " + emp.getIdEmprestimo() + " com o usuario: " + emp.getUsuario().getNome()
                                + " e retirada: " + emp.getDataRetirada() + " com devolucao: " + resultado);
                    }

                } else if (opcao == 8) {
                    System.out.println("Usuarios:");
                    for (Usuario u : sistema.getUsuarios()) {
                        System.out.println(u);
                    }

                } else if (opcao == 0) {
                    System.out.println("Programa fechado");
                } else {
                    System.out.println("Opcao invalida");
                }

            //Caso tenha digitado uym número que não existe
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um numero valido");
            //Todas as outras exceções de texto
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        sc.close();
    }
}