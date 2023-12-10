package org.hangmanFX;

import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Label wordLbl;
    public TextField letterFld;
    public Label messageLbl;
    public Button tryLetterBtn;
    public Button resetBtn;
    public Button exitBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        tryLetterBtn.setOnAction(actionEvent -> onTryLetterBtn());
        resetBtn.setOnAction(actionEvent -> onResetBtn());
        exitBtn.setOnAction(actionEvent -> onExit());
    }

    private void bindData() {
        StringProperty word = Model.getInstance().getWordToShow();
        wordLbl.textProperty().bind(word);
        StringProperty message = Model.getInstance().getMessage();
        messageLbl.textProperty().bind(message);
    }

    private void onTryLetterBtn() {
        boolean gameOver = Model.getInstance().isGameOver();
        if(gameOver) {
            Model.getInstance().setMessage("Game already over. Press 'Reset' to restart!");
            letterFld.setText("");
            return;
        }
        char letter = letterFld.getText().charAt(0);
        letterFld.setText("");
        Model.getInstance().tryLetter(letter);
    }

    private void onResetBtn() {
        Model.getInstance().resetWord();
    }

    private void onExit() {
        Stage toClose = (Stage) exitBtn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(toClose);
    }
}
