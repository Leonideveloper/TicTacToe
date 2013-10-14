package com.gmail.leonidandand.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Dimension;
import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;

/**
 * Created by Leonid on 27.09.13.
 */
class GameBoardImpl implements GameBoard, Serializable {
    private final Matrix<Player.Id> cells;

    GameBoardImpl(int gameBoardDimension) {
        cells = new ArrayMatrix<Player.Id>(new Dimension(gameBoardDimension, gameBoardDimension));
    }

    @Override
    public boolean cellIsEmpty(Position pos) {
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
    public Player.Id get(Position pos) {
        return cells.get(pos);
    }

    @Override
    public void clear() {
        cells.fill(null);
    }

    @Override
    public void set(Position pos, Player.Id id) {
        cells.set(pos, id);
    }
}
