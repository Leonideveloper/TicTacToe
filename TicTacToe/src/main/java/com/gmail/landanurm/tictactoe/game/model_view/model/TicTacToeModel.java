package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface TicTacToeModel {

    Player getFirstPlayer();
    Player getSecondPlayer();
    Score getScore();
    void startGame();

    void addOnGameStartedListener(OnGameStartedListener listener);
    void addOnGameFinishedListener(OnGameFinishedListener listener);
    void addOnScoreChangedListener(OnScoreChangedListener listener);
    void addOnNeedToShowMoveListener(OnNeedToShowMoveListener listener);
    void addOnMovePlayerChangedListener(OnMovePlayerChangedListener listener);
}
