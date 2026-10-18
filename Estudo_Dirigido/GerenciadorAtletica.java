package Estudo_Dirigido;
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
}
