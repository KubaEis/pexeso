package com.example.demo;

public class Player {
    private String name;
    private int score;
    private boolean turn;
    Player(String name, boolean turn) {
        this.name = name;
        this.turn = turn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public boolean isTurn() {
        return turn;
    }
    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
