package engine.data;

import engine.giocatore.Giocatore;
import engine.servizi.PartitaService;
import gui.ScacchieraController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Salvataggio implements Serializable{

    private Giocatore g1,g2;
    private int numeroMosse = 0;
    private boolean g2IsBot;

    private String nomeFile;

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }
    public String getNomeFile() {
        return nomeFile;
    }

    public Giocatore getG1() {
        return g1;
    }

    public void setG1(Giocatore g1) {
        this.g1 = g1;
    }

    public Giocatore getG2() {
        return g2;
    }

    public void setG2(Giocatore g2) {
        this.g2 = g2;
    }

    public int getNumeroMosse() {
        return numeroMosse;
    }

    public void setNumeroMosse(int numeroMosse) {
        this.numeroMosse = numeroMosse;
    }

    public boolean isG2IsBot() {
        return g2IsBot;
    }

    public void setG2IsBot(boolean g2IsBot) {
        this.g2IsBot = g2IsBot;
    }

    public static Salvataggio salvataggio = new Salvataggio();

    /**
     * Il metodo salvaPartita() salva una partita in un file presente nella directory. Se la directory non esiste allora la crea.
     * Inoltre il metodo gestisce l'eccezzione di input/output.
     * @param g1
     * @param g2
     * @param ps
     * @param file
     * @throws IOException
     */
    public void salvaPartita(Giocatore g1, Giocatore g2, PartitaService ps, File file) throws IOException {
        String directoryPath = System.getProperty("user.dir") + File.separator + "saved_games";
        File directory = new File(directoryPath);

        // Se la directory non esiste, crea la directory
        if (!directory.exists()) {
            directory.mkdirs(); // crea la directory e tutte le directory padre se non esistono
        }
        salvataggio.setG1(g1);
        salvataggio.setG2(g2);
        salvataggio.setNumeroMosse(PartitaService.getNumeroMosseTotali());
        salvataggio.g2IsBot = PartitaService.getIsBot();
        try (FileOutputStream fos = new FileOutputStream(directoryPath + File.separator + file.getName());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(salvataggio);
        } catch (Exception e) {
            return;
        }
    }


    @FXML
    private static Parent root;
    @FXML
    private static Stage stage;
    @FXML
    private static Scene scene;


    public static void caricaPartita(File file, MouseEvent event){

        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)){

            Salvataggio salvataggioRecuperato = (Salvataggio) ois.readObject();

            FXMLLoader loader = new FXMLLoader(Salvataggio.class.getResource("/cheesboard.fxml"));
            root = loader.load();
            ScacchieraController sc = loader.getController();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            sc.initGameRecuperato(salvataggioRecuperato);
        }catch(ClassNotFoundException | IOException e){
            return;
        }
    }



}
