package com.gmail.leonidandand.tictactoe.game.model;

/**
 * Created by Leonid on 29.07.13.
 */
public class Score {
    private int playerScore;
    private int opponentScore;

    public Score() {
        playerScore = 0;
        opponentScore = 0;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void increasePlayerScore() {
        ++playerScore;
    }

    public void increaseOpponentScore() {
        ++opponentScore;
    }
}
