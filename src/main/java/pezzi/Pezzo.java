package pezzi;

import engine.servizi.Mossa;
import engine.servizi.PartitaService;
import engine.servizi.ScacchieraService;
import gui.CasellaScacchiera;
import gui.ScacchieraController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Pezzo extends ImageView implements Serializable {
    @FXML
    Scene scene;
    private String nome, codice;
    private final int valore;
    private boolean colore;
    private int riga, colonna;
    private int codicePezzoUTF8;


    /**
     * metodo costruttore: crea una nuova istanza Pezzo quando viene invocato
     */
    public Pezzo(String nome, String codice, int valore, boolean colore, int riga, int colonna, int codicePezzo) {
        this.nome = nome;
        this.codice = codice;
        this.valore = valore;
        this.colore = colore;
        this.riga = riga;
        this.colonna = colonna;
        this.codicePezzoUTF8 = codicePezzo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    public int getValore() {
        return valore;
    }

    public void setColore(boolean colore) {
        this.colore = colore;
    }

    public boolean getColore() {
        return colore;
    }

    public int getCodicePezzoUTF8() {
        return codicePezzoUTF8;
    }

    public void setCodicePezzoUTF8(int codicePezzoUTF8) {
        this.codicePezzoUTF8 = codicePezzoUTF8;
    }


    /**
     * Metodo usato per calcolare le mosse normali controlla se una casella è occupata
     * Se lo è: Permette al Pezzo di mangiare se è del colore opposto
     * Se non lo è: Permette al Pezzo di muoversi liberamente
     *
     * @return
     */
    public boolean casellavuota(int riga, int colonna, ArrayList<CasellaScacchiera> mosseDisponibili) {
        // Controllo casella occupata
        if (ScacchieraService.getPezzo(riga, colonna) != null) {
            if (ScacchieraService.getPezzo(riga, colonna).getColore() != getColore()) {
                mosseDisponibili.add(new CasellaScacchiera(riga, colonna, true,this));
                return false;
            }
            return false;
        } else {
            mosseDisponibili.add(new CasellaScacchiera(riga, colonna, false,this));
            return true;
        }
    }


    /**
     * motodo setPosizione(): utile per settere la posizione dei pezzi.
     * Importante per modificare la posizione del pezzo dopo una mossa.
     */
    public void setPosizione(int newRiga, int newColonna) {
        this.riga = newRiga;
        this.colonna = newColonna;
    }

    public int getRiga() {
        return riga;
    }

    public int getColonna() {
        return colonna;
    }


    /**
     * metodo toString(): stampa le informazioni principali del pezzo (nome, colore, posizione).
     * Ad esempio: Pedone bianco posRiga: A posColonna: 4
     *
     * @return
     */
    public String toString() {
        return nome + " " + (colore ? "bianco" : "nero") + " " + "posRiga: " + riga + " posColonna: " + colonna;
    }

    public abstract List<Mossa> getMosse();

    /**
     * Metodo che setta l'immagine su un pezzo
     */
    public void setImage() {
        this.setImage(new Image("/pezzi/" + (this.colore ? "Bianco" : "Nero") + "/" + this.nome + ".png"));
    }

    /**
     * Metodo che dopo aver calcolato l'array delle possibili mosse, chiama controller scacchiera per render
     * delle caselle disponibili per il movimento del pezzo
     */
    public void selezionaCaselleDisponibili() {
        ScacchieraController.selezionaPosizioniDisponibili(getArrayMosse(), this);
    }

    /**
     * Metodo che viene invocato quando si fa click col mouse
     * Richiama il metodo che seleziona il pezzo nella casella e mette in primo piano il pezzo
     *
     * @param e
     */
    public void pressPezzo(MouseEvent e) {
        ((CasellaScacchiera) this.getParent()).clickSuPezzoNellaCasella(this);
        this.getParent().toFront();
    }

    /**
     * Metodo che viene invocato quando si trascina il mouse dopo il click
     * viene calcolata la posizione del pezzo(rispetto a tutta la finestra) e viene aggiornata centrando
     * il pezzo al cursore del mouse
     *
     * @param e
     */
    public void dragPezzo(MouseEvent e) {
        double posCasellaX = 0;
        double posCasellaY = 0;
        //Recupero la posizione della casella e del relativo pezzo in modo assoluto rispetto alla finestra
        if (this.getParent().getParent() != null) {
            posCasellaX = this.getParent().getParent().getLayoutX() + this.getParent().getLayoutX();
            posCasellaY = this.getParent().getParent().getLayoutY() + this.getParent().getLayoutY();
        }
        //Sposto il pezzo centrandolo al mouse (-30 perche la casella è 60)
        this.setTranslateX(e.getSceneX() - posCasellaX - 30);
        this.setTranslateY(e.getSceneY() - posCasellaY - 30);

        //evidenzia la casella sotto il cursore
        ((CasellaScacchiera) this.getParent()).coloraCasellaAlPassaggioDelMouse(e);
    }

    /**
     * Metodo che viene invocato quando viene rilasciato il click del mouse
     * viene reimpostata la posizione iniziale del pezzo e viene controllato se la posizone del cursore
     * e una casella valida per una mossa
     *
     * @param e
     */
    public void releasePezzo(MouseEvent e) {
        this.setTranslateX(0);
        this.setTranslateY(0);
        ((CasellaScacchiera) this.getParent()).controlloRilascioPezzo(e);
    }

    /**
     * Metodo che viene sovrascritto in ogni classe figlio
     * Ritorna un array di posizione di caselle legali per una mossa
     *
     * @return
     */
    public ArrayList<CasellaScacchiera> getArrayMosseNormali() {
        ArrayList<CasellaScacchiera> mosseDisponibiliNormali = new ArrayList<>();
        return mosseDisponibiliNormali;
    }

    /**
     * Metodo che calcola le mosse disponibili
     * Ritorna un array di posizione di caselle legali per una mossa
     * con la differenza che rispetto a getArrayMosseNormali controlla se si è sotto scacco
     * e quindi limita le mosse in modo tale da bloccare lo scacco
     *
     * @return
     */
    public ArrayList<CasellaScacchiera> getArrayMosse() {

        if (PartitaService.isGiocatoreSottoScacco()) {
            ArrayList<CasellaScacchiera> mosseDisponibiliTotali = Mossa.getMosseParaScacco();
            ArrayList<CasellaScacchiera> mosseNormaliDelPezzo = getArrayMosseNormali();
            ArrayList<CasellaScacchiera> mosseObbligateDelPezzo = new ArrayList<>();

            for (CasellaScacchiera mossa : mosseNormaliDelPezzo) {
                for (CasellaScacchiera mossaNemico : mosseDisponibiliTotali) {
                    if (mossa.getRiga() == mossaNemico.getRiga() && mossa.getColonna() == mossaNemico.getColonna()) {
                        mosseObbligateDelPezzo.add(mossa);
                        break;
                    }
                }
            }
            return mosseObbligateDelPezzo;
        } else {
            //Controllo se qualche mossa possa portare a uno scacco in caso lo limito
            ArrayList<CasellaScacchiera> mosseNormaliDelPezzo = getArrayMosseNormali();
            ArrayList<CasellaScacchiera> mosseObbligateDelPezzo = new ArrayList<>();

            for (CasellaScacchiera mossa : mosseNormaliDelPezzo) {
                if(PartitaService.getScacchieraService().simulaMossaPerUnPezzo(mossa.getRiga(), mossa.getColonna(),this.getCodice())){
                    mosseObbligateDelPezzo.add(mossa);
                }
            }
            return mosseObbligateDelPezzo;
        }
    }


}
