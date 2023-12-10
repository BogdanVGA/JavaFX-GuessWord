module HangmanFX {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.hangmanFX to javafx.fxml;
    exports org.hangmanFX;
}