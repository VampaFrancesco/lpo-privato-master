package engine.giocatore;

import engine.servizi.PartitaService;
import gui.CasellaScacchiera;
import pezzi.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Giocatore implements Serializable {

    private String nome;

    private boolean colore;

    private List<Pezzo> pezziGiocatore = new ArrayList<>();

    private List<Pezzo> pezziMangiati = new ArrayList<>();

    private int valoreTotaleGiocatore = 0;



    public Giocatore(String nome, boolean colore) {

        this.nome = nome;

        this.colore = colore;

        if (colore) {
            // Pezzo Pedone Bianco
            pezziGiocatore.add(new Pedone("Pedone", "b_p1", 1, true, 2, 1, 0x2659));
            pezziGiocatore.add(new Pedone("Pedone", "b_p2", 1, true, 2, 2, 0x2659));
            pezziGiocatore.add(new Pedone("Pedone", "b_p3", 1, true, 2, 3, 0x2659));
            pezziGiocatore.add(new Pedone("Pedone", "b_p4", 1, true, 2, 4, 0x2659));
            pezziGiocatore.add(new Pedone("Pedone", "b_p5", 1, true, 2, 5, 0x2659));
            pezziGiocatore.add(new Pedone("Pedone", "b_p6", 1, true, 2, 6, 0x2659));
            pezziGiocatore.add(new Pedone("Pedone", "b_p7", 1, true, 2, 7, 0x2659));
            pezziGiocatore.add(new Pedone("Pedone", "b_p8", 1, true, 2, 8, 0x2659));
            //Pezzo Torre Bianco
            pezziGiocatore.add(new Torre("Torre", "b_t1", 5, true, 1, 1, 0x2656));
            pezziGiocatore.add(new Torre("Torre", "b_t2", 5, true, 1, 8, 0x2656));
            //Pezzo Cavallo Bianco
            pezziGiocatore.add(new Cavallo("Cavallo", "b_c1", 3, true, 1, 2, 0x2658));
            pezziGiocatore.add(new Cavallo("Cavallo", "b_c2", 3, true, 1, 7, 0x2658));
            //Pezzo Alfiere Bianc
            pezziGiocatore.add(new Alfiere("Alfiere", "b_a1", 3, true, 1, 3, 0x2657));
            pezziGiocatore.add(new Alfiere("Alfiere", "b_a2", 3, true, 1, 6, 0x2657));
            //Pezzo Regina Bianco
            pezziGiocatore.add(new Regina("Regina", "b_q1", 9, true, 1, 4, 0x2655));
            //Pezzo Re Bianco
            pezziGiocatore.add(new Re("Re", "b_k1", 10, true, 1, 5, 0x2654));


        } else {
            // Pezzo Pedone Nero
            pezziGiocatore.add(new Pedone("Pedone", "n_p1", 1, false, 7, 1, 0x265F));
            pezziGiocatore.add(new Pedone("Pedone", "n_p2", 1, false, 7, 2, 0x265F));
            pezziGiocatore.add(new Pedone("Pedone", "n_p3", 1, false, 7, 3, 0x265F));
            pezziGiocatore.add(new Pedone("Pedone", "n_p4", 1, false, 7, 4, 0x265F));
            pezziGiocatore.add(new Pedone("Pedone", "n_p5", 1, false, 7, 5, 0x265F));
            pezziGiocatore.add(new Pedone("Pedone", "n_p6", 1, false, 7, 6, 0x265F));
            pezziGiocatore.add(new Pedone("Pedone", "n_p7", 1, false, 7, 7, 0x265F));
            pezziGiocatore.add(new Pedone("Pedone", "n_p8", 1, false, 7, 8, 0x265F));
            //Pezzo Torre Nero
            pezziGiocatore.add(new Torre("Torre", "n_t1", 5, false, 8, 1, 0x265C));
            pezziGiocatore.add(new Torre("Torre", "n_t2", 5, false, 8, 8, 0x265C));
            //Pezzo Cavallo Nero
            pezziGiocatore.add(new Cavallo("Cavallo", "n_c1", 3, false, 8, 2, 0x265E));
            pezziGiocatore.add(new Cavallo("Cavallo", "n_c2", 3, false, 8, 7, 0x265E));
            //Pezzo Alfiere Nero
            pezziGiocatore.add(new Alfiere("Alfiere", "n_a1", 3, false, 8, 3, 0x265D));
            pezziGiocatore.add(new Alfiere("Alfiere", "n_a2", 3, false, 8, 6, 0x265D));
            //Pezzo Regina Nero
            pezziGiocatore.add(new Regina("Regina", "n_q1", 9, false, 8, 4, 0x265B));
            //Pezzo Re Nero
            pezziGiocatore.add(new Re("Re", "n_k1", 10, false, 8, 5, 0x265A));
        }

    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getColore() {
        return this.colore;
    }

    public void setColore(boolean colore) {
        this.colore = colore;
    }

    public List<Pezzo> getPezziGiocatore() {
        return this.pezziGiocatore;
    }

    public void setPezziGiocatore(List<Pezzo> listanuova) {
        this.pezziGiocatore = listanuova;
    }

    public List<Pezzo> getPezziMangiati() {
        return this.pezziMangiati;
    }

    public void setPezziMangiati(List<Pezzo> pezzimangiati) {
        this.pezziMangiati = pezzimangiati;
    }

    public int getValoreTotaleGiocatore() {
        return valoreTotaleGiocatore;
    }

    public void setValoreTotaleGiocatore(int valoreTotaleGiocatore) {
        this.valoreTotaleGiocatore = valoreTotaleGiocatore;
    }
    /**
     * Medoto che aggiunge alla lista dei pezzi mangiati all'avversario un pezzo mangiato
     * li rimuove dalla lista del giocatore
     * Calcola il valore totale del giocatore
     *
     */
    public void addPezzoMangiato (CasellaScacchiera casellaPezzoMangiato) {
        Pezzo pezzomangiato = null;
        for(Pezzo pezzoGiocatore: pezziGiocatore){
            if(pezzoGiocatore.getRiga() == casellaPezzoMangiato.getRiga() && pezzoGiocatore.getColonna() == casellaPezzoMangiato.getColonna()){
                 pezzomangiato=pezzoGiocatore;
            }
        }

        if(pezzomangiato != null){
            pezziMangiati.add(pezzomangiato);
            pezziGiocatore.remove(pezzomangiato);
            valoreTotaleGiocatore = valoreTotaleGiocatore + pezzomangiato.getValore();
            PartitaService.setNumeroMossePerPatta(0);
        }

    }

    /**
     * Metodo che aggiunge un pezzo ai pezzi del giocatore
     * @param pezzo
     */
    public void addPezzo(Pezzo pezzo) {
        pezziGiocatore.add(pezzo);
    }
}
