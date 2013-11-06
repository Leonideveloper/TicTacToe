package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.tictactoe.game.model_view.model.judge.GameState;

import java.io.Serializable;


public class Score implements Serializable {
    private int firstPlayerScore;
    private int secondPlayerScore;

    Score() {
        firstPlayerScore = 0;
        secondPlayerScore = 0;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    void updateScoreBy(GameState gameState) {
        if (gameState == GameState.FIRST_PLAYER_WINS) {
            ++firstPlayerScore;
        } else if (gameState == GameState.SECOND_PLAYER_WINS) {
            ++secondPlayerScore;
        }
    }
}
