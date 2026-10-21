package Estudo_Dirigido;

public abstract class Usuario {
    private String id; //Utilizaremos para identificar o usuário
    private String nome;
    private String telefone;
    private int quantidadeEmprestados;
    private int fimBloqueio; //Variavel de tempo para guardarmos quando acaba o bloqueio se houver

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
        this.fimBloqueio = 0;

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
    public int getFimBloqueio() {
        return  fimBloqueio;
    }

    //-------------------------------------------------Funções------------------------------------------------------------
    //Declaradas como abstratas porque cada subclasse vai ter sua declaração
    public abstract int getLimiteEmprestimos();
    public abstract String getTipoPerfil();

    public boolean estaBloqueado (int dataReferencia) {
        //Escrevendo desse jeito fazemos como se fosse um IF, onde ele retorna apenas se as condições forem verdadeiras
        //Resumidamente, retorna true se existir data de fim, e se a data de referencia não passou da data fim (Bloqueio)
        return fimBloqueio != 0 && !(dataReferencia > fimBloqueio);
    }

    public boolean podePegarEmprestado(int dataReferencia) {
        //Se está bloqueado, não pode pegar emrestado
        if (estaBloqueado(dataReferencia)) {
            return false;
        }
        return quantidadeEmprestados < getLimiteEmprestimos();
    }

    // Recebe os dias de suspensão já calculados por emp.calcularSuspensao()
    public void aplicarSuspensao(int diasSuspensao, int dataDevolucao) {
        if (diasSuspensao > 0) {
            //Temos que declarar isso aqui como LocalDate porque o fim de bloqueio está como local date
            int base;

            if (fimBloqueio != 0 && fimBloqueio > dataDevolucao) {
                base = fimBloqueio;
            } else {
                base = dataDevolucao;
            }

            this.fimBloqueio = base + diasSuspensao;
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
        String statusBloqueio;
        if (fimBloqueio != 0){
            statusBloqueio = " | Bloqueado até: " + fimBloqueio;
        } else {
            statusBloqueio = "| Sem bloqueio";
        }
        return String.format("[%s] ID: %s | Nome: %s | Tel: %s | Itens em posse: %d/%d%s",
            getTipoPerfil(), id, nome, telefone, quantidadeEmprestados, getLimiteEmprestimos(), statusBloqueio);
    }


    //---------------------------------------------------Heranças----------------------------------------------------------

    public static class Aluno extends Usuario {
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


    public static class Funcionario extends Usuario {
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