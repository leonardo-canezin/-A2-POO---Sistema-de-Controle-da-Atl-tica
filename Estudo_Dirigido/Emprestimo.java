package Estudo_Dirigido;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
        this.dataPrevistaDevolucao = dataRetirada.plusDays(equipamento.getPrazoMaximoDias());
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

    public Vistoria getvistoriaDevolucao(){
        return  vistoriaDevolucao;
    }

    //-------------------------------------------Fuções-----------------------------------------------------------
    public void registrarDevolucao(LocalDate Devolucao, Vistoria vistoria) throws Exception{
        // Impede que um empréstimo já finalizado seja baixado duas vezes
        if (!this.ativo) { 
            throw new Exception("O emprestimo já está encerrado.\n");
        }
        // Valida se a data é possivél
        if (Devolucao.isBefore(this.dataRetirada)) {
            throw new Exception("A data de devolução tem que ser antes da retirada.\n");
        }
        // Atualiza os atributos com a data real e o relatório da vistoria
        this.dataRealDevolucao = Devolucao;
        this.vistoriaDevolucao = vistoria;
        // Altera o emprestimo para concluído
        this.ativo = false;
    }

    public long calcularAtraso() {
        // Se ainda não foi entregue OU se foi devolvido dentro do prazo ou antes
        if (this.dataRealDevolucao == null || !(this.dataRealDevolucao.isAfter(this.dataPrevistaDevolucao))) {
            return 0;
        }
        // Se estiver atrasado, calcula a diferença exata em dias entre as duas datas
        return ChronoUnit.DAYS.between(this.dataPrevistaDevolucao, this.dataRealDevolucao);
    }

    public long calcularSuspensao(){
        return calcularAtraso() * 2; // 2 dias de suspensão por atraso 
    }

    public boolean estaAtrasado(LocalDate dataAtual) {
        // Avalia se o emprestimo está aberto e se a data já passou do limite
        return this.ativo && dataAtual.isAfter(this.dataPrevistaDevolucao);
    }
}
