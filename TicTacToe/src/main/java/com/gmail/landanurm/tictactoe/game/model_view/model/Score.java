package com.gmail.landanurm.tictactoe.game.model_view.model;

import java.io.Serializable;

/**
 * Created by Leonid on 29.07.13.
 */
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

    void increaseFirstPlayerScore() {
        ++firstPlayerScore;
    }

    void increaseSecondPlayerScore() {
        ++secondPlayerScore;
    }
}
