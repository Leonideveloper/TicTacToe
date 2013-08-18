package com.gmail.leonidandand.tictactoe.game.model.opponent;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.tictactoe.game.model.Cell;

/**
 * Created by Leonid on 18.08.13.
 */
abstract class BaseOpponent implements Opponent {
    protected Matrix<Cell> gameBoard;

    @Override
    public void setGameBoard(Matrix<Cell> gameBoard) {
        this.gameBoard = gameBoard;
    }

}
