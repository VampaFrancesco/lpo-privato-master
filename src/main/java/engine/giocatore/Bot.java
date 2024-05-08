package engine.giocatore;

import engine.servizi.PartitaService;
import gui.CasellaScacchiera;
import gui.ScacchieraController;
import pezzi.Pezzo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot extends Giocatore {

    List<Pezzo> listaPezzi = this.getPezziGiocatore();
    Random randomP = new Random();
    Random randomM = new Random();

    public Bot() {
        super("Bot", false);
    }

    /**
     * Il metodo mossaRadom implementa le mosse del bot controllando prima i pezzi che possono effettuare mosse e in modo randomsi scegli un pezzo e una sua mossa da effettuare.
     */
    public void mossaRandom() {
        ArrayList<Pezzo> tuttePezziDisponibili = new ArrayList<>();
        ScacchieraController sc = PartitaService.getScacchieraController();

        //lista dei pezzi che possono effettuare delle mosse
        for (Pezzo pezzo : listaPezzi) {
            if (pezzo.getArrayMosse().isEmpty()) continue;
            tuttePezziDisponibili.add(pezzo);
        }
        if (tuttePezziDisponibili.isEmpty()) return;
        int numeroRandomPezzo = randomP.nextInt(tuttePezziDisponibili.size());
        Pezzo pezzorandom = tuttePezziDisponibili.get(numeroRandomPezzo);//otteniamo il pezzo random

        //scelta in modo random della mossa del pezzorandom
        int numeroRandomMossa = randomM.nextInt(pezzorandom.getArrayMosse().size());
        System.out.println("Casella random");
        System.out.println(pezzorandom.getArrayMosse().get(numeroRandomMossa));

        CasellaScacchiera casellaDisponibile = pezzorandom.getArrayMosse().get(numeroRandomMossa);
        CasellaScacchiera casellaPerEffetuareMossa = null;
        for (CasellaScacchiera casellaConPezzi : ScacchieraController.getCaselleScacchiera()) {
            if (casellaConPezzi.getRiga() == casellaDisponibile.getRiga() && casellaDisponibile.getColonna() == casellaConPezzi.getColonna())
                casellaPerEffetuareMossa = casellaConPezzi;
        }

        sc.eseguiMossa(pezzorandom, casellaPerEffetuareMossa);
    }

}