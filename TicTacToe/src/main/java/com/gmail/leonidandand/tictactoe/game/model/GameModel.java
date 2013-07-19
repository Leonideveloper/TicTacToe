package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.game.view.GameViewAndroidImpl;
import com.gmail.leonidandand.tictactoe.utils.Dimension;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public interface GameModel {
    boolean emptyCell(Matrix.Position pos);

    void onPlayerTurn(Matrix.Position turnPosition);

    void onViewIsReadyToStartGame();

    void addOnOpponentMoveListener(OnOpponentMoveListener listener);

    void addOnGameFinishedListener(OnGameFinishedListener listener);

    Dimension getDimension();
}
