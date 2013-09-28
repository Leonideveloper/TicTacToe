package com.gmail.leonidandand.tictactoe.game.model_view.model;

/**
 * Created by Leonid on 29.07.13.
 */
public class Score {
    private int scoreOfPlayer1;
    private int scoreOfPlayer2;

    Score() {
        scoreOfPlayer1 = 0;
        scoreOfPlayer2 = 0;
    }

    public int getScoreOfPlayer1() {
        return scoreOfPlayer1;
    }

    public int getScoreOfPlayer2() {
        return scoreOfPlayer2;
    }

    void increaseScoreOfPlayer1() {
        ++scoreOfPlayer1;
    }

    void increaseScoreOfPlayer2() {
        ++scoreOfPlayer2;
    }
}
