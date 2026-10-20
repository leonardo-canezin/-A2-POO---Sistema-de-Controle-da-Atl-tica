package Estudo_Dirigido;

public class Vistoria {
    //Booleanos para validar se as partes estão Ok ou não
    private boolean pneu;
    private  boolean freio;
    private boolean corrente;

    //Construtor
    public Vistoria(boolean pneu, boolean freio, boolean corrente){
        this.pneu = pneu;
        this.freio = freio;
        this.corrente = corrente;
    }


    //-----------------------------------------------------Getters-----------------------------------------------------
    public boolean getPneu() {
        return pneu;
    }
    public boolean getFreio() {
        return freio;
    }
    public boolean getCorrente() {
        return corrente;
    }

    //----------------------------------------------------Funções-------------------------------------------------------------
    
    public boolean temProblema() {
        // A pergunta é se tem problema, dai tem que retornar o inverso do que realmente é
        return !pneu || !freio || !corrente;
    }

    public String gerarResumo() {
        //Jeito mais rápido de escrever IF e Else, porque aqui ia ter que repetir 3 vezes e a comparação é bem simples.
        //Basicamente ele verifica se pneu = true. Se for, retorna OK(if), se não, retorna PROBLEMA(else)
        //Nesse String.Format também utilizamos esse formato parecido com o printF do "C".
        return String.format("Vistoria [Pneu: %s | Freio: %s | Corrente: %s | Obs: %s]",
                pneu ? "OK" : "PROBLEMA",
                freio ? "OK" : "PROBLEMA",
                corrente ? "OK" : "PROBLEMA");
    }
}
