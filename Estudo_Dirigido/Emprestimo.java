package Estudo_Dirigido;
import java.time.LocalDate;

public class Emprestimo {
    private int idEmprestimo;
    private Usuario usuario;
    private Equipamento equipamento;
    private LocalDate dataRetirada;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataRealDevolucao;
    private boolean ativo;
    private Vistoria vistoriaDevolucao;
    
    //Construtor
    public Emprestimo (int idEmprestimo, Usuario usuario, Equipamento equipamento, LocalDate dataRetirada) {
        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.equipamento = equipamento;
        this.dataRetirada = dataRetirada;
        // Função para calcular o tempo previsto com base no tipo do equipamento. 
        // É utilizado polimorfismo inclusive, já que utiliza somente a classe equipamento
        this.dataPrevistaDevolucao = dataRetirada.plusDays(equipamento.obterPrazoMaximoDias());
        this.dataRealDevolucao = null;
        this.ativo = true;
        this.vistoriaDevolucao = null;
    }

    //-------------------------------------------Getters-----------------------------------------------------------
    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataRealDevolucao() {
        return dataRealDevolucao;
    }

    public boolean getAtivo() {
        return ativo;
    }
}
