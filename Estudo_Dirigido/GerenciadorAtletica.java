package Estudo_Dirigido;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorAtletica {
    //Variaveis para armazenarmos todas as listas de todas as outras classes
    private List<Usuario> usuarios;
    private List<Equipamento> equipamentos;
    private List<Emprestimo> emprestimos;
    private int proximoIdEmprestimo;

    //Construtor
    public GerenciadorAtletica(){
        this.usuarios = new ArrayList<>();
        this.equipamentos = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.proximoIdEmprestimo = 1;
    }

    //getter
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    //--------------------------------------------------------Funções----------------------------------------------------------

    public void cadastrarUsuario(Usuario novoUsuario) {
        //Esse primeiro for é só pra ter certeza que já nção está cadastrado
        for (Usuario u : usuarios) {
            if (u.getId().equalsIgnoreCase(novoUsuario.getId())) {
                throw new IllegalArgumentException("Já existe um usuário cadastrado com esse ID");
            }
        }
        usuarios.add(novoUsuario);
    }

    public void cadastrarEquipamento(Equipamento novoEquipamento) {
        //Mesmo for do cadastrarUsuario
        for (Equipamento e : equipamentos) {
            if (e.getCodigo() == novoEquipamento.getCodigo()) {
                throw new IllegalArgumentException("Já existe um equipamento com esse codigo");
            }
        }
        equipamentos.add(novoEquipamento);
    }

    public Usuario buscarUsuarioPorId(String id) {
        //Tive que colocar esse id Trim para não dar problema caso o usuario escreva espaço no meio.
        //É o indicado na aba de uso desse "equalsIgnoreCase"
        for (Usuario u : usuarios) {
            if (u.getId().equalsIgnoreCase(id.trim())) {
                return u;
            }
        }
        throw new IllegalArgumentException("Usuário não encontrado com o ID fornecido");
    }

    public Equipamento buscarEquipamentoPorCodigo(int codigo) {
        for (Equipamento e : equipamentos) {
            if (e.getCodigo() == codigo) {
                return e;
            }
        }
        throw new IllegalArgumentException("Equipamento não encontrado com o código fornecido");
    }

    
    //Função mais complexa que realiza todo o emprestimo com as outras funçõpes que criamos
    public Emprestimo realizarEmprestimo(String idUsuario, int codigoEquipamento, LocalDate dataRetirada) {
        //Encontra o usuario pelo ID
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        //Encontra o equipamento pelo codigo
        Equipamento equipamento = buscarEquipamentoPorCodigo(codigoEquipamento);

        if (!equipamento.getDisponivel()) {
            throw new IllegalStateException("Este equipamento já está emprestado");
        }
        //Verifica se está bloqueado
        if (usuario.estaBloqueado(dataRetirada)) {
            throw new IllegalStateException("Usuário bloqueado por atraso");
        }
        //Verifica se ele já tem mais que o permitido
        if (!usuario.podePegarEmprestado(dataRetirada)) {
            throw new IllegalStateException("Limite máximo de itens atingido");
        }

        //Cria o emprestimo em si depois de verificar tudo
        Emprestimo novo = new Emprestimo(proximoIdEmprestimo++, usuario, equipamento, dataRetirada);

        //Aduciona o emprestimo na array, para termos os emprestimos ativos
        emprestimos.add(novo);

        //O equipámento deixa de estar disponivel
        equipamento.setDisponivel(false);
        //Coloca no historico do equipamento que ele está ou ja esteve com certo usuario
        equipamento.adicionarHistorico(novo);
        //Soma um na quantidade de itens que o usuario tem
        usuario.incrementarItens();

        return novo;
    }

    //Passa a lista de emprestimos buscando um equipamento especifico
    public Emprestimo buscarEmprestimoAtivoPorEquipamento(int codigoEquipamento) {
        for (Emprestimo emp : emprestimos) {
            if (emp.getAtivo() && emp.getEquipamento().getCodigo() == codigoEquipamento) {
                return emp;
            }
        }
        throw new IllegalStateException("Não há nenhum empréstimo ativo para esse equipamento");
    }

    public long realizarDevolucao(int codigoEquipamento, LocalDate dataEntrega, String estadoConservacao, Vistoria vistoria) 

        throws Exception {
        Emprestimo emp = buscarEmprestimoAtivoPorEquipamento(codigoEquipamento);
        Equipamento eq = emp.getEquipamento();
        Usuario usuario = emp.getUsuario();

        //chama precisaVistoria() com polimorfismo e sem getinstance, porque precisaVistoria só é valido em bicicletas
        if (eq.precisaVistoria() && vistoria == null) {
            throw new IllegalArgumentException("Este equipamento exige o preenchimento da vistoria na devolução.");
        }

        emp.registrarDevolucao(dataEntrega, vistoria);

        //Se for bicicleta, seta a ultima vistoria dela como essa
        if (eq instanceof Equipamento.Bicicleta && vistoria != null) {
            ((Equipamento.Bicicleta) eq).setUltimaVistoria(vistoria);
        }

        //Coloca o equipamento como disponivel novamente
        eq.setDisponivel(true);
        //Guarda o estado que ele retornou
        eq.setEstadoConservacao(estadoConservacao);
        //Diminui um item do usuario
        usuario.decrementarItens();

        //Aplica suspensão se necessário com base nos dias de atraso
        long diasSuspensao = emp.calcularSuspensao();
        if (diasSuspensao > 0) {
            usuario.aplicarSuspensao(diasSuspensao, dataEntrega);
        }

        return emp.calcularAtraso();
    }

    //Retorno do array com nossos equipamentos
    public List<Equipamento> listarEquipamentosDisponiveis() {
        List<Equipamento> disponiveis = new ArrayList<>();
        for (Equipamento e : equipamentos) {
            if (e.getDisponivel()) {
                disponiveis.add(e);
            }
        }
        return disponiveis;
    }
    //Retorno do array com nossos emprestimos
    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo emp : emprestimos) {
            if (emp.getAtivo()) {
                ativos.add(emp);
            }
        }
        return ativos;
    }
    //Retorno do histporico completo de algum equipamento
    public List<Emprestimo> consultarHistoricoEquipamento(int codigoEquipamento) {
        Equipamento eq = buscarEquipamentoPorCodigo(codigoEquipamento);
        return eq.getHistoricoEmprestimo();
    }

}
