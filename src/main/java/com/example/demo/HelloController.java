package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;

public class HelloController {
    private Card firstCard;
    private Card secondCard;
    private ArrayList<Card> cards =  new ArrayList<>();
    Player firstPlayer = new Player("First", true);
    Player secondPlayer = new Player("Second", false);
    @FXML
    private Label PlayerOneScoreLabel, PlayerTwoScoreLabel, PlayerTurn, InfoLabel;
    @FXML
    private GridPane grid;
    @FXML
    private Button NewGameButton;

    @FXML
    private void initialize() {
        int sc = 0;
        generateCards();
        displayCards();
        newGame();
    }
    @FXML
    private void newGame(){
        for(Card card : cards){
            card.flipBack();
            card.getButton().setDisable(false);
        }
        firstPlayer.setScore(0);
        secondPlayer.setScore(0);
        InfoLabel.setText("");
        PlayerOneScoreLabel.setText("PLAYER ONE SCORE: " + firstPlayer.getScore());
        PlayerTwoScoreLabel.setText("PLAYER TWO SCORE: " + secondPlayer.getScore());
        if (firstPlayer.isTurn()) {
            PlayerTurn.setText("TURN: PLAYER ONE");
        }else{
            PlayerTurn.setText("TURN: PLAYER TWO");
        }
        NewGameButton.setDisable(true);
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
        Collections.shuffle(cards);
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
            firstCard.flip();
        } else if (card.getButton().getText().equals("?")) {
            secondCard = card;
            secondCard.flip();
            checkMatch();
        }
    }

    @FXML
    private void checkMatch() {
        if (firstCard.getId() == secondCard.getId()){
            if (firstPlayer.isTurn()) {
                InfoLabel.setText("PLAYER ONE GOT A POINT");
                firstCard.getButton().setDisable(true);
                secondCard.getButton().setDisable(true);
                firstCard = null;
                secondCard = null;
                firstPlayer.setScore(firstPlayer.getScore() + 1);
                PlayerOneScoreLabel.setText("PLAYER ONE SCORE: " + firstPlayer.getScore());
                firstPlayer.setTurn(false);
                secondPlayer.setTurn(true);
                PlayerTurn.setText("TURN: PLAYER TWO");
                System.out.print(firstPlayer.getScore()+secondPlayer.getScore());
                checkGameState();
            }else{
                InfoLabel.setText("PLAYER TWO GOT A POINT");
                firstCard.getButton().setDisable(true);
                secondCard.getButton().setDisable(true);
                firstCard = null;
                secondCard = null;
                secondPlayer.setScore(secondPlayer.getScore() + 1);
                PlayerTwoScoreLabel.setText("PLAYER TWO SCORE: " + secondPlayer.getScore());
                secondPlayer.setTurn(false);
                firstPlayer.setTurn(true);
                PlayerTurn.setText("TURN: PLAYER ONE");
                System.out.print(firstPlayer.getScore()+secondPlayer.getScore());
                checkGameState();
            }
        }else{
            if(firstPlayer.isTurn()){
                InfoLabel.setText("WRONG CARD");
                firstCard.flipBack();
                secondCard.flipBack();
                firstCard = null;
                secondCard = null;
                firstPlayer.setTurn(false);
                secondPlayer.setTurn(true);
                PlayerTurn.setText("TURN: PLAYER TWO");
                System.out.print(firstPlayer.getScore()+secondPlayer.getScore());
            }else{
                InfoLabel.setText("WRONG CARD");
                firstCard.flipBack();
                secondCard.flipBack();
                firstCard = null;
                secondCard = null;
                secondPlayer.setTurn(false);
                firstPlayer.setTurn(true);
                PlayerTurn.setText("TURN: PLAYER ONE");
                System.out.print(firstPlayer.getScore()+secondPlayer.getScore());
            }
        }
    }

    @FXML
    private void checkWinner() {
        if (firstPlayer.getScore() > secondPlayer.getScore()){
            InfoLabel.setText("PLAYER ONE WON (Score: "+firstPlayer.getScore()+")");
        }else if (secondPlayer.getScore() > firstPlayer.getScore()){
            InfoLabel.setText("PLAYER TWO WON (Score: "+secondPlayer.getScore()+")");
        }
    }
    
    @FXML
    private void checkGameState() {
        if (firstPlayer.getScore()+secondPlayer.getScore() == 8){
            checkWinner();
            NewGameButton.setDisable(false);

        }
    }

    public ArrayList<Card> getCards() {

        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

}
