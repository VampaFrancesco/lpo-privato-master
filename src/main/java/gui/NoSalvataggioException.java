package gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class NoSalvataggioException extends Exception{

    /**
     * Metodo costruttore dell'eccezione che gestisce la mancaza di salvataggi di partite.
     * Tale metodo viene invocato nell momento in cui si desidera caricare una partita e non sono presenti file caricati.
     * @param errMess
     * @param event
     */
    public NoSalvataggioException(String errMess, ActionEvent event){
        super(errMess);
        ScacchieraController s = new ScacchieraController();
        Alert alertSalvataggio = new Alert(Alert.AlertType.NONE, "Non hai partite salvate. Salva prima una partita.");
        alertSalvataggio.setTitle("Errore Salvataggio");

        ButtonType ok = new ButtonType("OK");
        alertSalvataggio.getButtonTypes().add(ok);

        alertSalvataggio.showAndWait().ifPresent(scelta ->{
            if(scelta == ok){
                return;
            }
        });
    }
}
