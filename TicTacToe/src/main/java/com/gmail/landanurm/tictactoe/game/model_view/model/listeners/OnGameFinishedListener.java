package com.gmail.landanurm.tictactoe.game.model_view.model.listeners;

import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;

/**
 * Created by Leonid on 18.07.13.
 */
public interface OnGameFinishedListener {
    void onGameFinished(TicTacToeResult result);
}
