package Estudo_Dirigido;
import java.util.ArrayList;
import java.util.List;


public abstract class Equipamento {
    
    private int codigo;
    private boolean disponivel;
    private String estadoConservacao;
    private  List<Emprestimo> historicoEmprestimo;

    //Construtor
    public Equipamento (int codigo, String descricao, String estadoConservacao) {
        if (codigo <= 0){ //Caso tentem cadastrar um código negativo
            throw new IllegalArgumentException("O código precisa ser positivo");
        }
        this.codigo = codigo;
        this.disponivel = true;
        this.estadoConservacao = estadoConservacao;
        this.historicoEmprestimo = new ArrayList<>(); //Inicia uma Array para termos o histórico
    }

    //---------------------------------------------Getters---------------------------------------------------------

    public int getCodigo() {
        return codigo;
    }
    public boolean getDisponivel() {
        return disponivel;
    }
    public String getEstadoConservacao() {
        return estadoConservacao;
    }
    public List<Emprestimo> getHistoricoEmprestimo() {
        return this.historicoEmprestimo;
    }

    //-------------------------------------------Funções-----------------------------------------------------------
    //Cada dia tem um prazo diferente, então é abstrata e damos override nas subclasses.
    //Utilizaremos para calcular os prazos na classe "Emprestimo"
    public abstract int obterPrazoMaximoDias();


    //------------------------------------------Heranças------------------------------------------------------------

    public class Bola extends Equipamento {
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

    }

    public class Colete extends Equipamento {
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

    }

    public class Raquete extends Equipamento {
        public Raquete(int codigo, String descricao, String estadoConservacao){
            super(codigo, descricao, estadoConservacao);
        }

        //Funções e overrides da Raquete
        @Override
        public int obterPrazoMaximoDias() {
            return 2; // até 2 dias
        }
    }

    public class Bicicleta extends Equipamento {
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

        //Funções e overrides da Bicicleta
        @Override
        public int obterPrazoMaximoDias() {
            return 7; // Até 1 semana (7 dias)
        }
    }

    

}



