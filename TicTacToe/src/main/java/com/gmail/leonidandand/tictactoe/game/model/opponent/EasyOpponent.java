package com.gmail.leonidandand.tictactoe.game.model.opponent;

import com.gmail.leonidandand.matrix.Dimension;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.Cell;

/**
 * Created by Leonid on 18.08.13.
 */
public class EasyOpponent extends BaseOpponent {
    @Override
    public Position positionToMove() {
        Dimension dim = gameBoard.getDimension();
        for (int row = 0; row < dim.rows; ++row) {
            for (int column = 0; column < dim.columns; ++column) {
                Position pos = new Position(row, column);
                if (gameBoard.get(pos) == Cell.EMPTY) {
                    return pos;
                }
            }
        }
        throw new RuntimeException("There is not empty cells!");
    }
}
