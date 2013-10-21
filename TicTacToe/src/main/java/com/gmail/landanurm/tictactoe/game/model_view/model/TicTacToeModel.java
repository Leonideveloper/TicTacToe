package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnNewGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface TicTacToeModel {

    Player getFirstPlayer();
    Player getSecondPlayer();
    ReadOnlyGameBoard getGameBoard();
    Score getScore();

    void onMove(Position movePos, Player player);

    void startGame();

    void setOnNewGameStartedListener(OnNewGameStartedListener listener);
    void setOnGameFinishedListener(OnGameFinishedListener listener);
    void setOnScoreChangedListener(OnScoreChangedListener listener);
    void setOnNeedToShowMoveListener(OnNeedToShowMoveListener listener);
    void setOnMovePlayerChangedListener(OnMovePlayerChangedListener listener);
}
