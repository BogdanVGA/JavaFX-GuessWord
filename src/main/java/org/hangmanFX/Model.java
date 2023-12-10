package org.hangmanFX;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final StringProperty message;
    private final StringProperty wordToShow;
    private final ArrayList<String> words;

    private String wordToGuess;
    private int lives;
    private int mistakes;
    private int guessedLetters;
    private boolean gameOver;

    private Model() {
        this.lives = 1;
        this.mistakes = 0;
        this.guessedLetters = 0;
        this.gameOver = false;
        this.words = new ArrayList<>();
        this.viewFactory = new ViewFactory();
        this.message = new SimpleStringProperty("Welcome to Guess the Word!");
        this.wordToGuess = getWordToGuess();
        this.wordToShow = new SimpleStringProperty("_".repeat(this.wordToGuess.length()));
    }

    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return this.viewFactory;
    }

    public StringProperty getWordToShow() {
        return this.wordToShow;
    }

    public void setMessage(String message) {
        this.message.setValue(message);
    }

    public StringProperty getMessage() {
        return this.message;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void tryLetter(char testLetter) {
        char[] letters = this.wordToShow.getValue().toCharArray();
        boolean letterFound = false;
        for(int i = 0; i < this.wordToGuess.length(); i++) {
            char crtLetter = this.wordToGuess.charAt(i);
            if(crtLetter == testLetter) {
                letters[i] = crtLetter;
                letterFound = true;
                this.guessedLetters++;
            }
        }
        this.wordToShow.setValue(String.valueOf(letters));
        if(this.guessedLetters == this.wordToGuess.length()) {
            this.message.setValue("You guessed the whole word!");
            this.gameOver = true;
            return;
        }
        if(letterFound) {
            this.message.setValue("You guessed right!");
        } else {
            this.mistakes++;
            if(this.mistakes == this.wordToGuess.length()) {
                this.lives--;
                this.mistakes = 0;
            }
            if(this.lives == 0) {
                this.message.setValue("You lost. The word was: " + this.wordToGuess);
                this.gameOver = true;
                return;
            }
            this.message.setValue("Wrong guess! " + this.mistakes + " mistake(s). Lives: " + this.lives);
        }
    }

    public void resetWord() {
        this.gameOver = false;
        this.wordToGuess = getWordToGuess();
        this.lives = 1;
        this.mistakes = 0;
        this.guessedLetters = 0;
        this.wordToShow.setValue("_".repeat(this.wordToGuess.length()));
        this.message.setValue("The word was reset!");
    }

    private String getWordToGuess() {
        File dictionary = new File("./Dictionary_eng.txt");
        if(this.words.isEmpty()) {
            try (Scanner readFile = new Scanner(dictionary)) {
                while(readFile.hasNextLine()) {
                    String word = readFile.nextLine();
                    if(word.length() < 4) {
                        continue;
                    }
                    this.words.add(word);
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Dictionary file not found...");
            }
        }
        Random random = new Random();
        int chosenIndex = random.nextInt(words.size());
        return words.get(chosenIndex).toLowerCase();
    }
}
