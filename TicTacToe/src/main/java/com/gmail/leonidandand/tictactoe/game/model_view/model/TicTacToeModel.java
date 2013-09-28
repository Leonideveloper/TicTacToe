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

    Player getFirstPlayer();
    Player getSecondPlayer();
    ReadOnlyGameBoard getGameBoard();
    Score getScore();
    void onMove(Position movePos, Player player);
    void onViewIsReadyToStartGame();

    void addOnGameFinishedListener(OnGameFinishedListener listener);
    void removeOnGameFinishedListener(OnGameFinishedListener listener);

    void addOnScoreChangedListener(OnScoreChangedListener listener);
    void removeOnScoreChangedListener(OnScoreChangedListener listener);

    void addOnNeedToShowMoveListener(OnNeedToShowMoveListener listener);
    void removeOnNeedToShowMoveListener(OnNeedToShowMoveListener listener);

    void addOnMovePlayerChangedListener(OnMovePlayerChangedListener listener);
    void removeOnMovePlayerChangedListener(OnMovePlayerChangedListener listener);
}
