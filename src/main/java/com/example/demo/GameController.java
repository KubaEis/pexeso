package com.example.demo;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;

public class GameController {
    private boolean lockBoard = false;
    private Card firstCard;
    private Card secondCard;
    private ArrayList<Card> cards =  new ArrayList<>();
    Player firstPlayer = new Player("First", true);
    Player secondPlayer = new Player("Second", false);
    Player thirdPlayer = new Player("Third", false);
    @FXML
    private Label PlayerOneScoreLabel, PlayerTwoScoreLabel, PlayerThreeScoreLabel, PlayerTurn, InfoLabel;
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
        int column = 0;
        int row = 0;
        for(int i = 0; i < cards.size(); i++){
            grid.getChildren().remove(cards.get(i).getButton());
            column++;
            if (column == 4){
                column = 0;
                row++;
            }
        }
        cards.clear();
        generateCards();
        displayCards();
        for(Card card : cards){
            card.flipBack();
            card.getButton().setDisable(false);
        }
        firstPlayer.setScore(0);
        secondPlayer.setScore(0);
        thirdPlayer.setScore(0);
        InfoLabel.setText("");

        PlayerOneScoreLabel.setText("PLAYER "+firstPlayer.getName()+" COLLECTED: " + firstPlayer.getScore() + " FRUITS (pocet kol: "+firstPlayer.getTurns() + ")");
        PlayerTwoScoreLabel.setText("PLAYER "+secondPlayer.getName()+" COLLECTED: " + secondPlayer.getScore() + " FRUITS (pocet kol: "+secondPlayer.getTurns() + ")");
        PlayerThreeScoreLabel.setText("PLAYER "+thirdPlayer.getName()+" COLLECTED: " + thirdPlayer.getScore() + " FRUITS (pocet kol: "+thirdPlayer.getTurns() + ")");

        if(firstPlayer.isTurn()) {
            PlayerTurn.setText("PLAYER "+secondPlayer.getName()+" IS COLLECTING FRUITS");
        }else if(secondPlayer.isTurn()) {
            PlayerTurn.setText("PLAYER "+thirdPlayer.getName()+" IS COLLECTING FRUITS");
        }else if(thirdPlayer.isTurn()){
            PlayerTurn.setText("PLAYER "+firstPlayer.getName()+" IS COLLECTING FRUITS");
        }
    }

    @FXML
    private void generateCards(){
        for(int i = 0; i < 12; i++){
            cards.add(new Card(i+1, false));
            cards.add(new Card(i+1, false));
        }
        cards.add(new Card(13, true));
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
            if (column == 5){
                column = 0;
                row++;
            }
        }
    }

    @FXML
    private void switchPlayer() {
        if(firstPlayer.isTurn()) {
            firstPlayer.setTurn(false);
            PlayerOneScoreLabel.setDisable(true);
            secondPlayer.setTurn(true);
            PlayerTwoScoreLabel.setDisable(false);
            PlayerTurn.setText("PLAYER "+secondPlayer.getName()+" IS COLLECTING FRUITS");
        }else if(secondPlayer.isTurn()) {
            secondPlayer.setTurn(false);
            PlayerTwoScoreLabel.setDisable(true);
            thirdPlayer.setTurn(true);
            PlayerThreeScoreLabel.setDisable(false);
            PlayerTurn.setText("PLAYER "+thirdPlayer.getName()+" IS COLLECTING FRUITS");
        }else if(thirdPlayer.isTurn()){
            thirdPlayer.setTurn(false);
            PlayerThreeScoreLabel.setDisable(true);
            firstPlayer.setTurn(true);
            PlayerOneScoreLabel.setDisable(false);
            PlayerTurn.setText("PLAYER "+firstPlayer.getName()+" IS COLLECTING FRUITS");
        }
    }

    @FXML
    private void addScore(Player player) {
        player.setScore(player.getScore() + 1);
        InfoLabel.setText("PLAYER "+player.getName()+" COLLECTED A FRUIT");
        if(firstPlayer.isTurn()) {
            PlayerOneScoreLabel.setText("PLAYER "+firstPlayer.getName()+" COLLECTED: " + firstPlayer.getScore() + " FRUITS (pocet kol: "+firstPlayer.getTurns() + ")");
        }else if(secondPlayer.isTurn()) {
            PlayerTwoScoreLabel.setText("PLAYER "+secondPlayer.getName()+" COLLECTED: " + secondPlayer.getScore() + " FRUITS (pocet kol: "+secondPlayer.getTurns() + ")");
        }else if(thirdPlayer.isTurn()) {
            PlayerThreeScoreLabel.setText("PLAYER "+thirdPlayer.getName()+" COLLECTED: " + thirdPlayer.getScore() + " FRUITS (pocet kol: "+thirdPlayer.getTurns() + ")");
        }
    }

    private void addTurn(Player player) {
        player.setTurns(player.getTurns() + 1);
        if(firstPlayer.isTurn()) {
            PlayerOneScoreLabel.setText("PLAYER "+firstPlayer.getName()+" COLLECTED: " + firstPlayer.getScore() + " FRUITS (pocet kol: "+firstPlayer.getTurns() + ")");
        }else if(secondPlayer.isTurn()) {
            PlayerTwoScoreLabel.setText("PLAYER "+secondPlayer.getName()+" COLLECTED: " + secondPlayer.getScore() + " FRUITS (pocet kol: "+secondPlayer.getTurns() + ")");
        }else if(thirdPlayer.isTurn()) {
            PlayerThreeScoreLabel.setText("PLAYER "+thirdPlayer.getName()+" COLLECTED: " + thirdPlayer.getScore() + " FRUITS (pocet kol: "+thirdPlayer.getTurns() + ")");
        }
    }

    @FXML
    private void handleCardClick(Card card) {

        if (lockBoard) return;

        if (card == firstCard) return;
            if (firstCard == null) {
                firstCard = card;
                firstCard.flip();
            }else if (secondCard == null) {
                secondCard = card;
                secondCard.flip();
                checkMatch();
            }
    }


    @FXML
    private void checkMatch() {
        if(firstPlayer.isTurn()) {
            addTurn(firstPlayer);
        }else if(secondPlayer.isTurn()) {
            addTurn(secondPlayer);
        }else if(thirdPlayer.isTurn()) {
            addTurn(thirdPlayer);
        }
        lockBoard = true;
            if (firstCard.getId() == secondCard.getId()) {

                if (firstPlayer.isTurn()) {
                    addScore(firstPlayer);
                }else if (secondPlayer.isTurn()) {
                    addScore(secondPlayer);
                }else  if(thirdPlayer.isTurn()) {
                    addScore(thirdPlayer);
                }

                firstCard.getButton().setDisable(true);
                secondCard.getButton().setDisable(true);

                firstCard = null;
                secondCard = null;

                lockBoard = false;
                switchPlayer();
                checkGameState();
            } else {

                InfoLabel.setText("WRONG FRUIT");

                Card tempFirst = firstCard;
                Card tempSecond = secondCard;

                PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
                pause.setOnFinished(e -> {
                    tempFirst.flipBack();
                    tempSecond.flipBack();

                    firstCard = null;
                    secondCard = null;

                    lockBoard = false;
                    switchPlayer();
                });

                pause.play();
            }
    }



    @FXML
    private void checkWinner() {
        if (firstPlayer.getScore() > secondPlayer.getScore() && firstPlayer.getScore() > thirdPlayer.getScore()){
            InfoLabel.setText("PLAYER "+firstPlayer.getName()+" WON (Fruits: "+firstPlayer.getScore()+")");
        }else if (secondPlayer.getScore() > firstPlayer.getScore() && secondPlayer.getScore() > thirdPlayer.getScore()){
            InfoLabel.setText("PLAYER "+secondPlayer.getName()+" WON (Fruits: "+secondPlayer.getScore()+")");
        }else if (thirdPlayer.getScore() >  firstPlayer.getScore() && thirdPlayer.getScore() > secondPlayer.getScore()){
            InfoLabel.setText("PLAYER "+secondPlayer.getName()+" WON (Fruits: "+secondPlayer.getScore()+")");
        }
    }

    @FXML
    private void checkGameState() {
        if (firstPlayer.getScore()+secondPlayer.getScore() == 8){
            checkWinner();
        }
    }

    public ArrayList<Card> getCards() {

        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

}
