package Estudo_Dirigido;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    //-------------------------------------------------Funções------------------------------------------------------------
    //Declaradas como abstratas porque cada subclasse vai ter sua declaração
    public abstract int getLimiteEmprestimos();
    public abstract String getTipoPerfil();

    public boolean estaBloqueado (LocalDate dataReferencia) {
        //Escrevendo desse jeito fazemos como se fosse um IF, onde ele retorna apenas se as condições forem verdadeiras
        //Resumidamente, retorna true se existir data de fim, e se a data de referencia não passou da data fim (Bloqueio)
        return fimBloqueio != null && !dataReferencia.isAfter(fimBloqueio);
    }

    public boolean podePegarEmprestado(LocalDate dataReferencia) {
        //Se está bloqueado, não pode pegar emrestado
        if (estaBloqueado(dataReferencia)) {
            return false;
        }
        return quantidadeEmprestados < getLimiteEmprestimos();
    }

    // Recebe os dias de suspensão já calculados por emp.calcularSuspensao()
    public void aplicarSuspensao(long diasSuspensao, LocalDate dataDevolucao) {
        if (diasSuspensao > 0) {
            //Temos que declarar isso aqui como LocalDate porque o fim de bloqueio está como local date
            LocalDate base;

            if (fimBloqueio != null && fimBloqueio.isAfter(dataDevolucao)) {
                base = fimBloqueio;
            } else {
                base = dataDevolucao;
            }

            this.fimBloqueio = base.plusDays(diasSuspensao);
        }
    }

    public void incrementarItens() {
        this.quantidadeEmprestados++;
    }

    public void decrementarItens() {
        if (this.quantidadeEmprestados > 0) {
            this.quantidadeEmprestados--;
        }
    }

    //É uma funbção de biblioteca que imprime os valores. O String.format funciona igual o printf de "C" inclusive
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String statusBloqueio;
        if (fimBloqueio != null){
            statusBloqueio = " | Bloqueado até: " + fimBloqueio.format(fmt);
        } else {
            statusBloqueio = "| Sem bloqueio";
        }
        return String.format("[%s] ID: %s | Nome: %s | Tel: %s | Itens em posse: %d/%d%s",
            getTipoPerfil(), id, nome, telefone, quantidadeEmprestados, getLimiteEmprestimos(), statusBloqueio);
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

        //Valores unicos do Cliente
        @Override 
        public int getLimiteEmprestimos() {
            return 2;
        }
        @Override 
        public String getTipoPerfil() {
            return  "Aluno - " + curso;
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

        //Valores unicos do Funcionario
        @Override 
        public int getLimiteEmprestimos() {
            return 4;
        }
        @Override 
        public String getTipoPerfil() {
            return  "Funcionario - " + setor;
        }

    }




}
