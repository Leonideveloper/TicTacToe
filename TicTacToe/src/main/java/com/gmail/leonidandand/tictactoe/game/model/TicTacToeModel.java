package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface TicTacToeModel {

    boolean cellIsEmpty(Position cellPos);

    int getGameBoardDimension();

    Player.Id getCell(Position cellPos);

    Score getScore();

    Player getFirstPlayer();
    Player getSecondPlayer();

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
