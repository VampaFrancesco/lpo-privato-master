package engine.servizi;

import engine.giocatore.Giocatore;
import gui.CasellaScacchiera;
import pezzi.Pezzo;

import java.util.ArrayList;
import java.util.List;


public class Mossa {


    static ArrayList<CasellaScacchiera> mosseGiocatoreNemico = new ArrayList<>();

    /**
     * Metodo che controlla se il re è sotto scacco
     *
     * @return
     */
    public static boolean reSottoScacco() {

        //riempio mosse possibili del giocatore nemico
        mosseGiocatoreNemico.clear();
        mosseGiocatoreNemico = mosseNemico();

        Pezzo reAlleato = null;
        if (PartitaService.getColoreTurnoGiocatore()) {
            reAlleato = ScacchieraService.getPezzoByCodice("b_k1");
        } else {
            reAlleato = ScacchieraService.getPezzoByCodice("n_k1");
        }

        for (CasellaScacchiera mossa : mosseGiocatoreNemico) {
            if (mossa.getColonna() == reAlleato.getColonna() && mossa.getRiga() == reAlleato.getRiga()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Metodo che ritorna un array di tutte le possibili mosse disponibili del giocatore nemico
     *
     * @return
     */
    public static ArrayList<CasellaScacchiera> mosseNemico() {

        Giocatore gNemico = null;

        if (PartitaService.getColoreTurnoGiocatore()) {
            gNemico = PartitaService.getGiocatore2();
        } else {
            gNemico = PartitaService.getGiocatore1();
        }

        mosseGiocatoreNemico.clear();
        for (Pezzo singoloPezzo : gNemico.getPezziGiocatore()) {
            mosseGiocatoreNemico.addAll(singoloPezzo.getArrayMosseNormali());
        }
        return mosseGiocatoreNemico;


    }


    /**
     * Metodo che ritorna un array di tutte le mosse che bloccano uno scacco
     *
     * @return
     */
    public static ArrayList<CasellaScacchiera> getMosseParaScacco() {
        ScacchieraService scacchieraService = PartitaService.getScacchieraService();
        ArrayList<CasellaScacchiera> mosseTotaliGiocatoreAlleato = new ArrayList<>();
        ArrayList<CasellaScacchiera> mosseTotaliPerParareLoScacco = new ArrayList<>();
        Giocatore giocatoreAlleato = null;
        Pezzo reDaNonValutare = null;

        //Recupero giocatore alleato
        if (PartitaService.getColoreTurnoGiocatore()) {
            giocatoreAlleato = PartitaService.getGiocatore1();
            reDaNonValutare = ScacchieraService.getPezzoByCodice("b_k1");
        } else {
            giocatoreAlleato = PartitaService.getGiocatore2();
            reDaNonValutare = ScacchieraService.getPezzoByCodice("n_k1");
        }
        //Rimuovo il re, perche il re ha il suo metodo per il calcolo delle possibili mosse
        List<Pezzo> pezziTemp = new ArrayList<>(giocatoreAlleato.getPezziGiocatore());
        pezziTemp.remove(reDaNonValutare);

        //Recupero delle mosse possibili del giocatore alleato
        for (Pezzo singoloPezzo : pezziTemp) {
            mosseTotaliGiocatoreAlleato.addAll(singoloPezzo.getArrayMosseNormali());
        }


        //Ciclo e controllo se una possibile mossa possa parare lo scacco
        for (CasellaScacchiera mossa : mosseTotaliGiocatoreAlleato) {
            if (scacchieraService.simulaMossaPerBloccoScacco(mossa.getRiga(), mossa.getColonna())) {
                //Se ritorna vero, significa che si ci si sposta dentro la casella mossa si blocca lo stacco
                mosseTotaliPerParareLoScacco.add(mossa);
            }
        }

        return mosseTotaliPerParareLoScacco;

    }

    public static boolean isScaccoMatto() {
        ArrayList<CasellaScacchiera> mosseParoScacco = getMosseParaScacco();
        ArrayList<CasellaScacchiera> mosseRe = new ArrayList<>();
        if (PartitaService.getColoreTurnoGiocatore()) {
            mosseRe.addAll(ScacchieraService.getPezzoByCodice("b_k1").getArrayMosse());
        } else {
            mosseRe.addAll(ScacchieraService.getPezzoByCodice("n_k1").getArrayMosse());
        }
        return mosseParoScacco.isEmpty() && mosseRe.isEmpty();
    }

}
