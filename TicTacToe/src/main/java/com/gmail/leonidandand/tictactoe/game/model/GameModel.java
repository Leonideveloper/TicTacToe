package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.game.view.GameViewImpl;
import com.gmail.leonidandand.tictactoe.utils.Dimension;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public interface GameModel {
    boolean emptyCell(Matrix.Position pos);
    Dimension getDimension();
    Score getScore();
    void addOnOpponentMoveListener(OnOpponentMoveListener listener);
    void addOnGameFinishedListener(OnGameFinishedListener listener);
    void addOnScoreChangedListener(OnScoreChangedListener listener);
    void setOpponent(Opponent opponent);
    void onPlayerMove(Matrix.Position movePosition);
    void onViewIsReadyToStartGame();
}
