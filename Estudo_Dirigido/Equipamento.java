package Estudo_Dirigido;
import java.util.ArrayList;
import java.util.List;


public abstract class Equipamento {
    
    // gauarda o tipo de cada equipamento
    private int codigo;
    private boolean disponivel;
    private String estadoConservacao;
    private String descricao;
    private  List<Emprestimo> historicoEmprestimo;

    //Construtor
    public Equipamento (int codigo, String descricao, String estadoConservacao) {
        if (codigo <= 0){ //Caso tentem cadastrar um código negativo
            throw new IllegalArgumentException("O código precisa ser positivo");
        }
        // Atribui a descrição recebida no parâmetro
        this.codigo = codigo;
        this.disponivel = true;
        this.estadoConservacao = estadoConservacao;
        this.descricao = descricao;
        this.historicoEmprestimo = new ArrayList<>(); //Inicia uma Array para termos o histórico
    }

    //---------------------------------------------Getters---------------------------------------------------------

    // Permite consultar as opções
    public int getCodigo() {
        return codigo;
    }
    public boolean getDisponivel() {
        return disponivel;
    }
    public String getEstadoConservacao() {
        return estadoConservacao;
    }
    public String getDescricao(){
        return descricao;
    }
    public List<Emprestimo> getHistoricoEmprestimo() {
        return this.historicoEmprestimo;
    }

    //---------------------------------------------Setters---------------------------------------------------------
    // seta como disponível
    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }

    //Pra verificar se voltou estragado
    public void setEstadoConservacao(String estadoConservacao) {
        if (estadoConservacao != null && !estadoConservacao.trim().isEmpty()) {
            this.estadoConservacao = estadoConservacao.trim();
        }
    }

    //-------------------------------------------Funções-----------------------------------------------------------
    //Cada dia tem um prazo diferente, então é abstrata e damos override nas subclasses.
    //Utilizaremos para calcular os prazos na classe "Emprestimo"
    // Troquei para obterPrazoMaximoDias pois usei na classe Emprestimo
    public abstract int obterPrazoMaximoDias();

    //Coloquei essas duas próximas funções pra não ser necessário dar instanceOf depois
    public abstract String obterCategoria();

    public boolean precisaVistoria() {
        return false;
    }

    // Atualiza a lista depois que um emprestimo é concluido
    public void adicionarHistorico(Emprestimo emprestimo){
        this.historicoEmprestimo.add(emprestimo);
    }

    //Estou fazendo uma toString em todas as classes pra evitar instance of depois
    @Override
    public String toString() {
    String prazoTexto;

    if (obterPrazoMaximoDias() == 0) {
        prazoTexto = "Mesmo dia";
    } else {
        prazoTexto = obterPrazoMaximoDias() + " dias";
    }

    return String.format("Cód: %d | [%s] %s | Conservação: %s | Prazo máx: %s",
            codigo, obterCategoria(), descricao, estadoConservacao, prazoTexto);
}

    //------------------------------------------Heranças------------------------------------------------------------

    // Static permite instanciar as subclasses diretamente no Gerenciador sem criar um Equipamento genérico 
    public static class Bola extends Equipamento {
        private String modalidade; //Atributo unico da Bola, já que o enunciado não especificou nenhum

        //Construtor
        public Bola (int codigo, String descricao, String estadoConservacao, String modalidade) {
            super (codigo, descricao, estadoConservacao);
            this.modalidade = modalidade;
        }
        
        //Getter unico da Bola
        public String getModalidade() {
            return modalidade;
        }

        //Funções e overrides da bola
        @Override
        public int obterPrazoMaximoDias() {
            return 0; // Até o fim do mesmo dia
        }

        @Override
        public String obterCategoria() {
            return "Bola (" + modalidade + ")";
        }

    }

    public static class Colete extends Equipamento {
        // Atributos unicos do colete. 
        // O enunciado não deu nenhum, mas como disse que cada um devia ter regras diferentes, 
        // criamos essas para ter algo diferente entre ele e a bola.
        private  String tamanho;
        private  String cor;

        //Construtor
        public Colete(int codigo, String descricao, String estadoConservacao, String tamanho, String cor) {
            super(codigo, descricao, estadoConservacao);
            this.tamanho = tamanho;
            this.cor = cor;
        }

        //Getters unicos do Colete
        public String getTamanho() {
            return tamanho;
        }
        public String getCor() {
            return cor;
        }

        //Funções e overrides do Colete
        @Override
        public int obterPrazoMaximoDias() {
            return 0; // Até o fim do dia
        }

        @Override
        public String obterCategoria() {
            return "Colete (" + cor + " - Tam: " + tamanho + ")";
        }

    }

    public static class Raquete extends Equipamento {
        public Raquete(int codigo, String descricao, String estadoConservacao){
            super(codigo, descricao, estadoConservacao);
        }

        //Funções e overrides da Raquete
        @Override
        public int obterPrazoMaximoDias() {
            return 2; // até 2 dias
        }

        @Override
        public String obterCategoria() {
            return "Raquete de Tênis de Mesa";
        }
    }

    public static class Bicicleta extends Equipamento {
        private  Vistoria ultimaVistoria; //Para guardar o estado dos componentes de cada bicicleta 

        //Construtor
        public Bicicleta (int codigo, String descricao, String estadoConservacao){
            super (codigo, descricao, estadoConservacao);
            this.ultimaVistoria = null;
        }

        //Getter da bicicleta
        public Vistoria getUltimaVistoria() {
            return ultimaVistoria;
        }

        // Atualiza a ultima vistoria feita
        public void setUltimaVistoria(Vistoria ultimaVistoria){
            this.ultimaVistoria = ultimaVistoria;
        }

        //Funções e overrides da Bicicleta
        @Override
        public int obterPrazoMaximoDias() {
            return 7; // Até 1 semana (7 dias)
        }

        //Unico caso que vai ser true, apenas a bicicleta precisa
        @Override
        public boolean precisaVistoria() {
            return true;
        }

        @Override
        public String obterCategoria() {
            return "Bicicleta";
        }
    }
}
