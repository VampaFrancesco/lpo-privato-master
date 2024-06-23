package gui;

import engine.data.Salvataggio;
import engine.data.comparatori.CompareByNumeroMosse;
import engine.data.comparatori.CompareByNumeroPezzi;
import engine.data.comparatori.CompareByValoreTotali;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaPartitaSalvateController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    ListView<String> listViewPartiteSalvate;

    static ArrayList<Salvataggio> listSalvataggi = new ArrayList<>();

    /**
     * Metodo che viene invocato quando si carica la finestra
     */
    public void init() {
        listSalvataggi.clear();
        caricaPartiteFiltrate();
        renderListaPartitaSalvate();


    }

    /**
     * Metodo che si occupa di caricare, leggere e deserializzare le partite dal file
     * Tale metodo viene invocato nel momento in cui si desidera caricare una partita.
     * Nel caso in cui non sono presenti file, il metodo gestisce anche l'eccezzione attraverso la classe NoSalvataggioException().
     */

    public static void caricaPartiteFiltrate() {
        ArrayList<File> fileList = new ArrayList<>();
        File directory = new File(System.getProperty("user.dir") + File.separator + "saved_games");

        try {
            if (directory.exists() && directory.isDirectory()) {
                fileList.addAll(List.of(directory.listFiles()));
            } else {
                throw new NoSalvataggioException("Cartella insesistente", new ActionEvent());
            }
        } catch (Exception e) {
            return;
        }

        if (!fileList.isEmpty()) {
            for (File file : fileList) {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Salvataggio salvataggioRecuperato = (Salvataggio) ois.readObject();
                    salvataggioRecuperato.setNomeFile(file.getName());
                    listSalvataggi.add(salvataggioRecuperato);
                } catch (ClassNotFoundException | IOException e) {
                    return;
                }
            }
        }
    }

    /**
     * Il metodo renderListaPartitaSalvate() aggiorna la lista delle partite salvate quando il giocatore sceglie in che modo ordinare le partite salvate.
     */
    public void renderListaPartitaSalvate() {
        ObservableList<String> elementi = FXCollections.observableArrayList();
        for (Salvataggio save : listSalvataggi) {
            elementi.add(save.getNomeFile());

        }
        listViewPartiteSalvate.setItems(elementi);
        listViewPartiteSalvate.setOnMouseClicked(event -> {
            String selectedItem = listViewPartiteSalvate.getSelectionModel().getSelectedItem();
            if (selectedItem == null) return;
            File file = new File(System.getProperty("user.dir") + File.separator + "saved_games" + File.separator + selectedItem);
            Salvataggio.caricaPartita(file, event);
        });
    }

    /**
     * Questo metodo restituisce la lista delle partite salvate in ordine rispetto il numero dei pezzi.
     * Tale metodo viene richiamato dal click sul bottone "Numero pezzi".
     *
     * @param actionEvent
     */
    public void OrdinaNumeroPezzi(ActionEvent actionEvent) {
        listSalvataggi.sort(new CompareByNumeroPezzi());
        renderListaPartitaSalvate();
    }

    /**
     * Questo metodo restituisce la lista delle partite salvate in ordine rispetto al valore dei pezzi.
     * Tale metodo viene richiamato dal click sul botton "Valore complessivo dei pezzi".
     *
     * @param actionEvent
     */
    public void OrdinaValorePezzi(ActionEvent actionEvent) {
        listSalvataggi.sort(new CompareByValoreTotali());
        renderListaPartitaSalvate();
    }

    /**
     * Questo metodo restituisce la lista delle partite salvate in ordine rispetto al numero di mosse eseguite dai giocatori.
     * Tale metodo viene richiamato dla click sul botton "Numero di mosse".
     *
     * @param actionEvent
     */
    public void OrdinaNumeroMosse(ActionEvent actionEvent) {
        listSalvataggi.sort(new CompareByNumeroMosse());
        renderListaPartitaSalvate();
    }

    /** Metodo invocato per tornare alla pagina di start
     *
     * @param actionEvent
     * @throws IOException
     */
    public void returnStart(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/start.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}