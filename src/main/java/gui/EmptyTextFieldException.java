package gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EmptyTextFieldException extends Exception{


    public EmptyTextFieldException(String errMess){
        System.err.println(errMess);
    }

}
