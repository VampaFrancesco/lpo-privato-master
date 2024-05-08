package engine.servizi;

import pezzi.Cavallo;
import pezzi.Pezzo;
import pezzi.Re;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ScacchieraService implements Serializable {

    private final int numRighe = 8;
    private final int numColonne = 8;

    private static Table<Integer, Integer, Pezzo> scacchieraTable;


    /**
     * Inizializza l oggetto scacchiera, dato due liste di pezzi dei rispettivi giocatori G1 e G2
     *
     * @param pezziG1
     * @param pezziG2
     */
    public ScacchieraService(List<Pezzo> pezziG1, List<Pezzo> pezziG2) {

        //Creazione della tabella
        scacchieraTable = HashBasedTable.create(numRighe, numColonne);

        //Aggiunta Pezzi alla scacchiera
        for (Pezzo singoloPezzo : pezziG1) {
            scacchieraTable.put(singoloPezzo.getRiga(), singoloPezzo.getColonna(), singoloPezzo);
        }
        for (Pezzo singoloPezzo2 : pezziG2) {
            scacchieraTable.put(singoloPezzo2.getRiga(), singoloPezzo2.getColonna(), singoloPezzo2);
        }
    }

    /**
     * Restituisce un Singolo Pezzo nella scacchiera data la posizione Riga, Colonna
     *
     * @param riga
     * @param colonna
     * @return un Pezzo nella scacchiera
     */
    public static Pezzo getPezzo(int riga, int colonna) {
        return scacchieraTable.get(riga, colonna);
    }

    /**
     * Restituisce un Singolo Pezzo nella scacchiera dato il suo Codice
     *
     * @param codice
     * @return un Pezzo nella scacchiera
     */
    public static Pezzo getPezzoByCodice(String codice) {
        Pezzo pezzo = null;
        for (Table.Cell<Integer, Integer, Pezzo> cell : scacchieraTable.cellSet()) {
            if (cell.getValue().getCodice().equals(codice)) {
                pezzo = cell.getValue();
            }
        }
        return pezzo;
    }

    /**
     * Metodo che serve per aggiornare la posizione di un pezzo
     * @param pezzoDaMuovere
     * @param riga
     * @param colonna
     */

    public void aggiornaPosizionePezzo(Pezzo pezzoDaMuovere, int riga, int colonna) {
        scacchieraTable.remove(pezzoDaMuovere.getRiga(), pezzoDaMuovere.getColonna());
        pezzoDaMuovere.setPosizione(riga, colonna);
        scacchieraTable.put(riga, colonna, pezzoDaMuovere);
    }

    /**
     * Metodo Usato per il Debug della scacchiera Service per capire le effettive posizioni dei pezzi
     * stampa la scacchiera su terminale
     */
    public static void printScacchiera() {
        int v = 0;
        char[] let = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        System.out.print("   ");
        for (int i = 0; i < let.length; i++) {
            System.out.print(" " + let[i] + " ");
        }
        System.out.println();
        for (int i = 8; i >= 1; i--) {
            Map<Integer, Pezzo> riga = scacchieraTable.row(i); //recupero un rigo della tabella
            System.out.printf(" %-1s ", i);
            for (int j = 1; j <= 8; j++) { //scorro il rigo e recupero gli elementi
                Pezzo x = riga.get(j);
                if (x == null)
                    System.out.printf("|--"); //stampa pezzo
                else
                    System.out.printf("|%-2s", x.getCodice()); //stampa pezzo
            }
            System.out.println();
        }
    }

    /**
     * Metodo che con le cordinate di una casella simula tale mossa e controlla se riesce a bloccare lo scacco o meno
     * Ritorna vero se blocca lo scacco, falso altrimenti
     *
     * @param riga
     * @param colonna
     * @return
     */
    public boolean simulaMossaPerBloccoScacco(int riga, int colonna) { // Ritorna vero se blocca lo scacco

        //Salvo il pezzo nella posizione (riga colonna), puo essere anche null
        Pezzo tempRimosso = getPezzo(riga, colonna);
        Pezzo pezzoTemp = null;

        pezzoTemp = new Cavallo("Cavallo", "temp", -1, PartitaService.getColoreTurnoGiocatore(), 1, 1, 0x265E);

        //Simulo la mossa col pezzo temporaneo
        if (tempRimosso == null) {
            scacchieraTable.put(riga, colonna, pezzoTemp);
        } else {
            //Se gia è presente un pezzo in tale casella prima lo rimuovo
            scacchieraTable.remove(riga, colonna);
            scacchieraTable.put(riga, colonna, pezzoTemp);
        }

        //cambio la posizione del pezzo rimosso cosi da simulare il fatto che sia stato "Mangiato"
        if (tempRimosso != null) {
            tempRimosso.setPosizione(0, 0);
        }

        //Controllo se con tale mossa si è ancora sotto scacco
        if (Mossa.reSottoScacco()) {
            //Se si, rimetto tutto come prima è ritorno false
            scacchieraTable.remove(riga, colonna); //rimuovo il pezzo temporaneo

            if (tempRimosso != null) { //rimetto quello precedente rimosso
                scacchieraTable.put(riga, colonna, tempRimosso);
                tempRimosso.setPosizione(riga, colonna);
            }

            return false;

        } else {
            //Se no, rimetto tutto come prima è ritorno true
            scacchieraTable.remove(riga, colonna); //rimuovo il pezzo temporaneo

            if (tempRimosso != null) { //rimetto quello precedente rimosso
                scacchieraTable.put(riga, colonna, tempRimosso);
                tempRimosso.setPosizione(riga, colonna);
            }
            return true;
        }
    }

    public boolean simulaMossaPerUnPezzo(int riga, int colonna, String codPezzo) { // Ritorna vero se blocca lo scacco

        //Salvo il pezzo nella posizione (riga colonna), puo essere anche null
        Pezzo tempRimosso = getPezzo(riga, colonna);
        Pezzo pezzoTemp = null;

        if (codPezzo.equals("b_k1") || codPezzo.equals("n_k1")) {
            pezzoTemp = new Re("Re", codPezzo, 10, PartitaService.getColoreTurnoGiocatore(), riga, colonna, 0x265E);
        } else {
            pezzoTemp = new Cavallo("Cavallo", "temp", -1, PartitaService.getColoreTurnoGiocatore(), 0, 0, 0x265E);
        }

        //Rimuovo temporaneamente il re dalla scacchiera
        Pezzo pezzoCheEsegueLaMossa = ScacchieraService.getPezzoByCodice(codPezzo);
        scacchieraTable.remove(pezzoCheEsegueLaMossa.getRiga(), pezzoCheEsegueLaMossa.getColonna());

        //Simulo la mossa col pezzo temporaneo
        if (tempRimosso == null) {
            scacchieraTable.put(riga, colonna, pezzoTemp);
        } else {
            //Se gia è presente un pezzo in tale casella prima lo rimuovo
            scacchieraTable.remove(riga, colonna);
            scacchieraTable.put(riga, colonna, pezzoTemp);
        }

        //cambio la posizione del pezzo rimosso cosi da simulare il fatto che sia stato "Mangiato"
        if (tempRimosso != null) {
            tempRimosso.setPosizione(0, 0);
        }

        //Controllo se con tale mossa si è ancora sotto scacco
        if (Mossa.reSottoScacco()) {
            //Se si, rimetto tutto come prima è ritorno false
            scacchieraTable.remove(riga, colonna); //rimuovo il pezzo temporaneo
            scacchieraTable.put(pezzoCheEsegueLaMossa.getRiga(), pezzoCheEsegueLaMossa.getColonna(), pezzoCheEsegueLaMossa);
            if (tempRimosso != null) { //rimetto quello precedente rimosso
                scacchieraTable.put(riga, colonna, tempRimosso);
                tempRimosso.setPosizione(riga, colonna);
            }

            return false;

        } else {
            //Se no, rimetto tutto come prima è ritorno true
            scacchieraTable.remove(riga, colonna); //rimuovo il pezzo temporaneo
            scacchieraTable.put(pezzoCheEsegueLaMossa.getRiga(), pezzoCheEsegueLaMossa.getColonna(), pezzoCheEsegueLaMossa);
            if (tempRimosso != null) { //rimetto quello precedente rimosso
                scacchieraTable.put(riga, colonna, tempRimosso);
                tempRimosso.setPosizione(riga, colonna);
            }
            return true;
        }
    }


}
