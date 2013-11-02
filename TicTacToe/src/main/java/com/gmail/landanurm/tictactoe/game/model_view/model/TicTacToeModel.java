package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnMovedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnPlayerWhoShouldMoveNextChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface TicTacToeModel {
    void addOnGameStartedListener(OnGameStartedListener listener);
    void addOnGameFinishedListener(OnGameFinishedListener listener);
    void addOnMovedListener(OnMovedListener listener);
    void addOnScoreChangedListener(OnScoreChangedListener listener);
    void addOnPlayerWhoShouldMoveNextChangedListener(OnPlayerWhoShouldMoveNextChangedListener listener);
    void startGame();
    void onMove(Position movePos, Player player);
    Player getFirstPlayer();
    Player getSecondPlayer();
    Score getScore();
}
