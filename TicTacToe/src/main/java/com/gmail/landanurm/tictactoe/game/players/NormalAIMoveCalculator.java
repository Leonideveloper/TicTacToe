package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.ReadOnlyGameBoard;

import java.io.Serializable;

/**
 * Created by Leonid on 28.09.13.
 */
class NormalAIMoveCalculator implements AIMoveCalculator, Serializable {
    @Override
    public Position calculatePositionToMove(ReadOnlyGameBoard gameBoard) {
        final int gameBoardDimension = gameBoard.getDimension();
        for (int row = 0; row < gameBoardDimension; ++row) {
            for (int column = 0; column < gameBoardDimension; ++column) {
                Position pos = new Position(row, column);
                if (gameBoard.cellIsEmpty(pos)) {
                    return pos;
                }
            }
        }
        throw new RuntimeException("There is not empty cells!");
    }
}
