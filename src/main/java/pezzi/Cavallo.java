package pezzi;

import engine.servizi.Mossa;
import gui.CasellaScacchiera;

import java.util.ArrayList;
import java.util.List;

public class Cavallo extends Pezzo{

    //metodo costruttore classe Cavallo: crea una nuova istanza di Cavallo
    public Cavallo(String nome, String codice, int valore, boolean colore, int riga, int colonna, int codicePezzo){
        super(nome, codice, valore, colore, riga, colonna,codicePezzo);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }

    /**
     * Metodo che calcola tutte le possibili mosse legali che il Pezzo Cavallo può fare
     * @return
     */
    public ArrayList<CasellaScacchiera> getArrayMosseNormali() {

        ArrayList<CasellaScacchiera> mosseDisponibili = new ArrayList<>();

        // Mosse del cavallo in ordine da: In alto a sinistra in senso antiOrario
        if (mossaLegaleCavallo(getRiga()+2, getColonna()-1)) {
            casellavuota(getRiga() + 2, getColonna() - 1, mosseDisponibili);
        }
        if (mossaLegaleCavallo(getRiga()+1, getColonna()-2)) {
            casellavuota(getRiga() + 1, getColonna() - 2, mosseDisponibili);
        }
        if (mossaLegaleCavallo(getRiga()-1, getColonna()-2)) {
            casellavuota(getRiga() - 1, getColonna() - 2, mosseDisponibili);
        }
        if (mossaLegaleCavallo(getRiga()-2, getColonna()-1)) {
            casellavuota(getRiga() - 2, getColonna() - 1, mosseDisponibili);
        }
        if (mossaLegaleCavallo(getRiga()-2, getColonna()+1)) {
            casellavuota(getRiga() - 2, getColonna() + 1, mosseDisponibili);
        }
        if (mossaLegaleCavallo(getRiga()-1, getColonna()+2)) {
            casellavuota(getRiga() - 1, getColonna() + 2, mosseDisponibili);
        }
        if (mossaLegaleCavallo(getRiga()+1, getColonna()+2)) {
            casellavuota(getRiga() + 1, getColonna() + 2, mosseDisponibili);
        }
        if (mossaLegaleCavallo(getRiga()+2, getColonna()+1)) {
            casellavuota(getRiga() + 2, getColonna() + 1, mosseDisponibili);
        }
        return mosseDisponibili;
    }


    public boolean mossaLegaleCavallo (int riga, int colonna){
        return riga <= 8 && riga >= 1 && colonna <= 8 && colonna >= 1;
    }
}
