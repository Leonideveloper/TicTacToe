package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface TicTacToeModel {
    boolean isEmptyCell(Position pos);
    int getGameBoardDimension();
    Player.Id getCellValueAtPos(Position pos);
    Score getScore();
    void onMove(Position movePosition, Player player);
    void onViewIsReadyToStartGame();

    void addOnGameFinishedListener(OnGameFinishedListener listener);
    void addOnScoreChangedListener(OnScoreChangedListener listener);
    void addOnNeedToShowMoveListener(OnNeedToShowMoveListener listener);
    void addOnMovePlayerChangedListener(OnMovePlayerChangedListener listener);
    void removeOnGameFinishedListener(OnGameFinishedListener listener);
    void removeOnScoreChangedListener(OnScoreChangedListener listener);
    void removeOnNeedToShowMoveListener(OnNeedToShowMoveListener listener);
    void removeOnMovePlayerChangedListener(OnMovePlayerChangedListener listener);
}
