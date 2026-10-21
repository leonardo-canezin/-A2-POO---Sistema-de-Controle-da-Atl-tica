package Estudo_Dirigido;

public class Emprestimo {
    private int idEmprestimo;
    private Usuario usuario;
    private Equipamento equipamento;
    private int dataRetirada;
    private int dataPrevistaDevolucao;
    private int dataRealDevolucao;
    private boolean ativo;
    private Vistoria vistoriaDevolucao;
    
    //Construtor
    public Emprestimo (int idEmprestimo, Usuario usuario, Equipamento equipamento, int dataRetirada) {
        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.equipamento = equipamento;
        this.dataRetirada = dataRetirada;
        // Função para calcular o tempo previsto com base no tipo do equipamento. 
        // É utilizado polimorfismo inclusive, já que utiliza somente a classe equipamento
        this.dataPrevistaDevolucao = dataRetirada + equipamento.obterPrazoMaximoDias();
        this.dataRealDevolucao = 0;
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

    public int getDataRetirada() {
        return dataRetirada;
    }

    public int getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public int getDataRealDevolucao() {
        return dataRealDevolucao;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public Vistoria getvistoriaDevolucao(){
        return  vistoriaDevolucao;
    }

    //-------------------------------------------Fuções-----------------------------------------------------------
    public void registrarDevolucao(int Devolucao, Vistoria vistoria) throws Exception{
        // Impede que um empréstimo já finalizado seja baixado duas vezes
        if (!this.ativo) { 
            throw new Exception("O emprestimo já está encerrado.\n");
        }
        // Valida se a data é possivél
        if (Devolucao < this.dataRetirada) {
            throw new Exception("A data de devolução tem que ser antes da retirada.\n");
        }
        // Atualiza os atributos com a data real e o relatório da vistoria
        this.dataRealDevolucao = Devolucao;
        this.vistoriaDevolucao = vistoria;
        // Altera o emprestimo para concluído
        this.ativo = false;
    }

    public int calcularAtraso() {
        // Se ainda não foi entregue OU se foi devolvido dentro do prazo ou antes
        if (this.dataRealDevolucao == 0 || !(this.dataRealDevolucao > this.dataPrevistaDevolucao)) {
            return 0;
        }
        // Se estiver atrasado, calcula a diferença exata em dias entre as duas datas
        return this.dataRealDevolucao - this.dataPrevistaDevolucao;
    }

    public int calcularSuspensao(){
        return calcularAtraso() * 2; // 2 dias de suspensão por atraso 
    }

    public boolean estaAtrasado(int dataAtual) {
        // Avalia se o emprestimo está aberto e se a data já passou do limite
        return this.ativo && dataAtual > this.dataPrevistaDevolucao;
    }
}