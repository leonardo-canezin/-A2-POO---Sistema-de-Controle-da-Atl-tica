package Estudo_Dirigido;
import java.time.LocalDate;

public abstract class Usuario {
    private String id; //Utilizaremos para identificar o usuário
    private String nome;
    private String telefone;
    private int quantidadeEmprestados;
    private LocalDate fimBloqueio; //Variavel de tempo para guardarmos quando acaba o bloqueio se houver

    //Construtor de classe
    public Usuario (String id, String nome, String telefone){
        //Exceção para caso o id e nome fiquem vazios
        if (id == null || nome == null){
            throw new IllegalArgumentException("É obrigatorio ter um id e nome");
        }

        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.quantidadeEmprestados = 0; //Valor padrão para o primeiro cadastro
        this.fimBloqueio = null;

    }

    //----------------------------------------------------Getters----------------------------------------------------------
    public String getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getTelefone() {
        return  telefone;
    }
    public int getQuantidadeEmprestados() {
        return  quantidadeEmprestados;
    }
    public LocalDate getFimBloqueio() {
        return  fimBloqueio;
    }


    //---------------------------------------------------Heranças----------------------------------------------------------

    public class Aluno extends Usuario {
        private String curso; //Atributo unico do Aluno

        public Aluno(String id, String nome, String telefone, String curso) {
            super(id, nome, telefone);
            this.curso = curso;
        }

        //Getter unico do Aluno
        public String getCurso() {
            return curso;
        }
    }


    public class Funcionario extends Usuario {
        private String setor; ////Atributo unico do Fncionario

        public Funcionario(String id, String nome, String telefone, String setor) {
            super(id, nome, telefone);
            this.setor = setor;
        }

        ////Getter unico do funcionario
        public String getSetor() {
            return setor;
        }


    }




}
