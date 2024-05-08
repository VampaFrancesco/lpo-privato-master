package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class StartController {


    @FXML
    private Button play = new Button();

    @FXML
    public TextField nomePlayer1;

    public Button getPlay() {
        return play;
    }

    public TextField getNomePlayer1() {
        return nomePlayer1;
    }

    public TextField getNomePlayer2() {
        return nomePlayer2;
    }

    public CheckBox getDisabilita() {
        return disabilita;
    }

    public Parent getRoot() {
        return root;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Label getEccezione() {
        return eccezione;
    }

    @FXML
    public TextField nomePlayer2;

    @FXML
    private CheckBox disabilita;

    @FXML
    private Parent root;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    public Label eccezione;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Button carica = new Button();



    /**
     * Setta tutti i parametri per giocare e switcha alla scacchiera
     * @param event listener per il bottone
     * @throws IOException
     * @throws EmptyTextFieldException handler che gestisce i TextField lasciati in bianco
     */
    @FXML
    public void switchChessboard(ActionEvent event) throws IOException, EmptyTextFieldException {

    try {
        if ((nomePlayer1.getText().trim().isEmpty() || nomePlayer2.getText().trim().isEmpty()) && !disabilita.isSelected()) {
            eccezione.setFont(new Font("Arial", 24));
            eccezione.setTextFill(Color.RED);
            eccezione.setText("Non hai inserito i nomi.");
            throw new EmptyTextFieldException("Non hai inserito i nomi");

        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cheesboard.fxml"));
            root = loader.load();
            ScacchieraController sc = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            String nome1 = nomePlayer1.getText();
            String nome2 = nomePlayer2.getText();
            sc.initGame(nome1, nome2, disabilita.isSelected());
        }
    }catch(EmptyTextFieldException e){
            return;
        }
    }


    /**
     * Disabilita il TextFild per il giocatore2
     * @param event listner per il CheckBox
     */
    public void disablePlayer2(ActionEvent event){

       nomePlayer2.setDisable(((CheckBox) (event.getSource())).isSelected());

    }

    /**
     * Il metodo che invoca la finestra che visualizza la lista delle partite salvate
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @FXML
    public void CaricaSalvataggio(ActionEvent event) throws IOException, ClassNotFoundException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/listaPartitaSalvate.fxml"));
        root = loader.load();
        ListaPartitaSalvateController controller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        controller.init();

    }
}
