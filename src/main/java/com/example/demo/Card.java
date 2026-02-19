package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Card {
    private int id;
    private Button button;
    private Image frontImage;
    private Image backImage;
    private boolean matches;
    public Card(int id) {
        this.id = id;
        this.button = new Button("");
        this.button.setMinSize(90, 90);
        this.frontImage = loadImage("images/"+id+".png");
        this.backImage = loadImage("images/back.png");
    }

    private Image loadImage(String path){
        InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
        if (stream == null){
            throw new RuntimeException("Can't find image "+path);
        }
        return new Image(stream);
    }
    private ImageView ImageView(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(90);
        imageView.setFitHeight(90);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void flip() {
        button.setGraphic(ImageView(frontImage));
    }
    public void flipBack() {
        button.setGraphic(ImageView(backImage));
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
