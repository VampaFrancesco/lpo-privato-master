package engine.servizi;

import engine.data.Salvataggio;
import engine.giocatore.Bot;
import engine.giocatore.Giocatore;
import engine.giocatore.Umano;
import gui.ScacchieraController;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.Serializable;

public class PartitaService implements Serializable {


    private static Giocatore g1;
    private static Giocatore g2;
    private static boolean isBot;

    public Giocatore g1NonStatico;
    public Giocatore g2NonStatico;

    private static ScacchieraController scacchieraController;
    private static ScacchieraService scacchieraService;
    private static boolean partitaInCorso = false;
    private static boolean turnoGiocatore = true;
    private static boolean giocatoreSottoScacco = false;
    private static int numeroMosseTotali = 1;
    private static int numeroMossePerPatta;
    static PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

    public static int getNumeroMosseTotali() {
        return numeroMosseTotali;
    }

    public static void setNumeroMosseTotali(int numeroMosseTotali) {
        PartitaService.numeroMosseTotali = numeroMosseTotali;
    }

    public static int getNumeroMossePerPatta() {
        return numeroMossePerPatta;
    }

    public static void setNumeroMossePerPatta(int numeroMosseSenzaMangiare) {
        PartitaService.numeroMossePerPatta = numeroMosseSenzaMangiare;
    }

    public PartitaService(Salvataggio salvataggio, ScacchieraController scacchieraController){

        PartitaService.isBot = salvataggio.isG2IsBot();

        //inializza i giocatori
        g1 = salvataggio.getG1();
        g2 = salvataggio.getG2();

        //crea scacchiera service

        scacchieraService = new ScacchieraService(g1.getPezziGiocatore(),g2.getPezziGiocatore());
        PartitaService.scacchieraController = scacchieraController;
        partitaInCorso = true;
    }


    /**
     * Metodo costruttore che viene utilizzato per creare un'istanza della partita, quindi per iniziare una partita dati i due nomi dei giocatori.
     * Inoltre si necessita il controllo sul tipo di giocatore ovvero che sia il bot o no.
     *
     * @param nome1
     * @param nome2
     * @param isBot
     * @param scacchieraController
     */
    public PartitaService(String nome1, String nome2, boolean isBot, ScacchieraController scacchieraController) {
        PartitaService.isBot = isBot;
        //inizializzazione Giocatori
        g1 = new Umano(nome1, true);
        if (isBot) {
            g2 = new Bot();
        } else {
            g2 = new Umano(nome2, false);
        }
        //creazione Scacchiera Service
        scacchieraService = new ScacchieraService(g1.getPezziGiocatore(), g2.getPezziGiocatore());
        this.scacchieraController = scacchieraController;
        partitaInCorso = true;
    }

    /**
     * Costruttore che serve per creare una partita dal salvaggio
     * @param giocatore1
     * @param giocatore2
     */
    public PartitaService(Giocatore giocatore1, Giocatore giocatore2){
        this.g1NonStatico = giocatore1;
        this.g2NonStatico = giocatore2;
    }
    /**
     * Il metodo cambioTurno() è utile per cambiare il turno da un giocatore all'altro una volta effettuata una mossa.
     * Controlla inoltre se il re è sotto scacco per stabile se continuare o no la partita
     */
    public static void cambioTurno() {
        //Cambio turno del giocatore
        turnoGiocatore = !turnoGiocatore;

        giocatoreSottoScacco = Mossa.reSottoScacco();

        if ((g2 instanceof Bot) && g2.getColore() == getColoreTurnoGiocatore()) {
            pauseTransition.setOnFinished(event -> {
                ((Bot) g2).mossaRandom();
            });
            pauseTransition.play();
        }
        numeroMosseTotali++;

        if(numeroMossePerPatta == 50) { ScacchieraController.patta50Mosse(); }
            numeroMossePerPatta++;

    }

    public static void controlloScaccoMatto() {
        if (Mossa.reSottoScacco() && Mossa.isScaccoMatto()) {
            try {
                scacchieraController.endGame();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
    public static Giocatore getGiocatore1() {
        return g1;
    }

    public static Giocatore getGiocatore2() {
        return g2;
    }

    public static ScacchieraService getScacchieraService() {
        return scacchieraService;
    }

    public static boolean getColoreTurnoGiocatore() {
        return turnoGiocatore;
    }

    public static boolean isPartitaInCorso() {
        return partitaInCorso;
    }

    public static boolean isGiocatoreSottoScacco() {
        return giocatoreSottoScacco;
    }

    public static boolean getIsBot() {
        return isBot;
    }

    public static ScacchieraController getScacchieraController() {
        return scacchieraController;
    }

    public static void setScacchieraController(ScacchieraController scacchieraController) {
        PartitaService.scacchieraController = scacchieraController;
    }

    public static boolean isIsBot() {
        return isBot;
    }

    public static void setIsBot(boolean isBot) {
        PartitaService.isBot = isBot;
    }

    @Override
    public String toString() {
        return "PartitaService{}";
    }
}
