package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public class StupidAIOpponent implements Opponent {

    private final Matrix<Cell> gameBoard;

    public StupidAIOpponent(Matrix<Cell> gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public Matrix.Position positionToMove() {
        for (int row = 0; row < gameBoard.rows; ++row) {
            for (int column = 0; column < gameBoard.columns; ++column) {
                if (gameBoard.get(row, column) == Cell.EMPTY) {
                    return new Matrix.Position(row, column);
                }
            }
        }
        throw new RuntimeException("There is not empty cells!");
    }
}
