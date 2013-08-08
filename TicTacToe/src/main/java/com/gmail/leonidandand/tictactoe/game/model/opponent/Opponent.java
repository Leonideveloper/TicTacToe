package com.gmail.leonidandand.tictactoe.game.model.opponent;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.Cell;

/**
 * Created by Leonid on 18.07.13.
 */
public interface Opponent {
    void setGameBoard(Matrix<Cell> gameBoard);
    Position positionToMove();
}
