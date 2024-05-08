package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Runner extends Application {


    /**
     * Metodo che viene invocato da launch(args) nel main
     * crea la finestra e la inizializza
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/start.fxml"))));
            Scene scene = new Scene(root);
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("/logoAppBg.png")).toExternalForm());
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
