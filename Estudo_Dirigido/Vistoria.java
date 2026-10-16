package Estudo_Dirigido;

public class Vistoria {
    //Booleanos para validar se as partes estão Ok ou não
    private boolean pneu;
    private  boolean freio;
    private boolean corrente;

    //Construtor com todas as informações pois todas são necessárias na vistoria
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
}
