package com.gmail.leonidandand.tictactoe.game.model.opponent;

import com.gmail.leonidandand.tictactoe.game.model.Cell;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public interface Opponent {
    void setGameBoard(Matrix<Cell> gameBoard);
    Matrix.Position positionToMove();
}
