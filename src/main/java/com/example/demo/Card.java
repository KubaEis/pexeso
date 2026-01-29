package com.example.demo;

import javafx.scene.control.Button;

public class Card {
    private int id;
    private Button button;
    private boolean matches;
    public Card(int id) {
        this.id = id;
        this.button = new Button("?");
        this.button.setMinSize(90, 90);
    }
    public void flip() {
        button.setText(String.valueOf(id));
    }
    public void flipBack() {
        button.setText("?");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isMatches() {
        return matches;
    }

    public void setMatches(boolean matches) {
        this.matches = matches;
    }
}
