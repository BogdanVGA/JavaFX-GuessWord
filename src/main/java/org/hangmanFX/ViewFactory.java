package org.hangmanFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    public ViewFactory() {
        // TO-DO
    }

    public void showMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Guess the word");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
