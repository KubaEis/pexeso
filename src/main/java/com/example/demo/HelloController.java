package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class HelloController {
    private Card firstCard;
    private Card secondCard;
    private ArrayList<Card> cards =  new ArrayList<>();
    private int score;

    @FXML
    private Label scoreLabel;
    @FXML
    private GridPane grid;

    @FXML
    private void initialize() {
        Player firstPlayer = new Player("First");
        Player secondPlayer = new Player("Second");
        generateCards();
        displayCards();
    }

    @FXML
    private void generateCards(){
        for(int i = 0; i < 8; i++){
            cards.add(new Card(i+1));
            cards.add(new Card(i+1));
        }
    }

    @FXML
    private void displayCards(){
        int column = 0;
        int row = 0;
        for(int i = 0; i < cards.size(); i++){
            grid.add(cards.get(i).getButton(), column, row);
            int finalI = i;
            cards.get(i).getButton().setOnAction(e -> handleCardClick (cards.get(finalI))) ;;
            column++;
            if (column == 4){
                column = 0;
                row++;
            }
        }
    }
    @FXML
    private void handleCardClick (Card card) {
        if (firstCard == null) {
            firstCard = card;
            firstCard.getButton().setText(String.valueOf(card.getId()));
        } else {
            secondCard = card;
            secondCard.getButton().setText(String.valueOf(card.getId()));
            checkMatch();
        }
    }

    @FXML
    private void checkMatch() {
        if (firstCard.getId() == secondCard.getId()){
            firstCard.getButton().setDisable(true);
            secondCard.getButton().setDisable(true);
            firstCard = null;
            secondCard = null;
            score++;
            scoreLabel.setText("SCORE: " + score);
        }else{
            firstCard.getButton().setText("?");
            secondCard.getButton().setText("?");
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
