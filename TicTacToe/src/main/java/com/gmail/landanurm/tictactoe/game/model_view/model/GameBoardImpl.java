package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.tictactoe.game.model_view.model.game_board.GameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;

/**
 * Created by Leonid on 27.09.13.
 */
class GameBoardImpl implements GameBoard, Serializable {
    private final Matrix<Player.Position> cells;

    GameBoardImpl(int gameBoardDimension) {
        cells = new SquareMatrix<Player.Position>(gameBoardDimension);
    }

    @Override
    public boolean cellIsEmpty(com.gmail.landanurm.matrix.Position pos) {
        return (cells.get(pos) == null);
    }

    @Override
    public boolean containsEmptyCell() {
        return cells.contains(null);
    }

    @Override
    public int getDimension() {
        return cells.getDimension().rows;
    }

    @Override
    public Player.Position get(com.gmail.landanurm.matrix.Position pos) {
        return cells.get(pos);
    }

    @Override
    public void clear() {
        cells.fill(null);
    }

    @Override
    public void set(com.gmail.landanurm.matrix.Position pos, Player.Position position) {
        cells.set(pos, position);
    }
}
