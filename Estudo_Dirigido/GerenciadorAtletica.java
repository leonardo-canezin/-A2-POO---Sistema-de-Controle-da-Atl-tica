package Estudo_Dirigido;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    

    //Quase terminando aqui, falta apenas algumas funções de Equipamentos e já era

}
