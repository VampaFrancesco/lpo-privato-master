package pezzi;

import engine.servizi.Mossa;
import engine.servizi.PartitaService;
import gui.CasellaScacchiera;

import java.util.ArrayList;
import java.util.List;

public class Re extends Pezzo {

    //metodo costruttore classe Re: crea una nuova istanza di Re
    public Re(String nome, String codice, int valore, boolean colore, int riga, int colonna, int codicePezzo) {
        super(nome, codice, valore, colore, riga, colonna, codicePezzo);
        setImage();
    }

    @Override
    public List<Mossa> getMosse() {
        return null;
    }


    /**
     * Metodo che calcola tutte le possibili mosse legali che il Pezzo Re può fare
     *
     * @return
     */
    public boolean mossaLegaleRe(int riga, int colonna) {
        return riga <= 8 && riga >= 1 && colonna <= 8 && colonna >= 1;
    }

    /**
     * Metodo che restituisce le mosse disponibili che puoi fare legali
     * @return
     */
    public ArrayList<CasellaScacchiera> getArrayMosse() {
        ArrayList<CasellaScacchiera> mosseObbligate = new ArrayList<>();
        ArrayList<CasellaScacchiera> mosseNormaliDelPezzo = getArrayMosseNormali();

        for (CasellaScacchiera mossa : mosseNormaliDelPezzo) {
            if (PartitaService.getScacchieraService().simulaMossaPerUnPezzo(mossa.getRiga(), mossa.getColonna(), getCodice())) {
                mosseObbligate.add(mossa);
            }
        }
        return mosseObbligate;
    }

    /**
     * Metodo che restituisce la lista di tutte le mosse in generale che il Re può fare
     * @return
     */

    public ArrayList<CasellaScacchiera> getArrayMosseNormali() {
        ArrayList<CasellaScacchiera> mosseDisponibili = new ArrayList<>();

        // Mosse su assi Verticali e Orizzontali
        if (mossaLegaleRe(getRiga() + 1, getColonna())) {
            casellavuota(getRiga() + 1, getColonna(), mosseDisponibili);
        }
        if (mossaLegaleRe(getRiga() - 1, getColonna())) {
            casellavuota(getRiga() - 1, getColonna(), mosseDisponibili);
        }
        if (mossaLegaleRe(getRiga(), getColonna() + 1)) {
            casellavuota(getRiga(), getColonna() + 1, mosseDisponibili);
        }
        if (mossaLegaleRe(getRiga(), getColonna() - 1)) {
            casellavuota(getRiga(), getColonna() - 1, mosseDisponibili);
        }

        // Mosse su assi diagonali
        if (mossaLegaleRe(getRiga() + 1, getColonna() + 1)) {
            casellavuota(getRiga() + 1, getColonna() + 1, mosseDisponibili);
        }
        if (mossaLegaleRe(getRiga() + 1, getColonna() - 1)) {
            casellavuota(getRiga() + 1, getColonna() - 1, mosseDisponibili);
        }
        if (mossaLegaleRe(getRiga() - 1, getColonna() + 1)) {
            casellavuota(getRiga() - 1, getColonna() + 1, mosseDisponibili);
        }
        if (mossaLegaleRe(getRiga() - 1, getColonna() - 1)) {
            casellavuota(getRiga() - 1, getColonna() - 1, mosseDisponibili);
        }
        return mosseDisponibili;
    }


}
