package com.gmail.leonidandand.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface TicTacToeModel {

    boolean gameFinished();

    Player getFirstPlayer();
    Player getSecondPlayer();
    ReadOnlyGameBoard getGameBoard();
    Score getScore();
    void onMove(Position movePos, Player player);
    void onViewIsReadyToStartGame();

    void setOnGameFinishedListener(OnGameFinishedListener listener);
    void setOnScoreChangedListener(OnScoreChangedListener listener);
    void setOnNeedToShowMoveListener(OnNeedToShowMoveListener listener);
    void setOnMovePlayerChangedListener(OnMovePlayerChangedListener listener);
}
