package com.gmail.leonidandand.tictactoe.game.model;

/**
 * Created by Leonid on 29.07.13.
 */
public class Score {
    private int player1;
    private int player2;

    public Score() {
        player1 = 0;
        player2 = 0;
    }

    public int getScoreOfPlayer1() {
        return player1;
    }

    public int getScoreOfPlayer2() {
        return player2;
    }

    public void increaseScoreOfPlayer1() {
        ++player1;
    }

    public void increaseScoreOfPlayer2() {
        ++player2;
    }
}
