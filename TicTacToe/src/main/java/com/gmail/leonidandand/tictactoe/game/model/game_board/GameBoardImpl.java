package com.gmail.leonidandand.tictactoe.game.model.game_board;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Dimension;
import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 27.09.13.
 */
public class GameBoardImpl implements GameBoard {
    private final Matrix<Player.Id> matrix;

    public GameBoardImpl(int dim) {
        matrix = new ArrayMatrix<Player.Id>(new Dimension(dim, dim));
    }

    @Override
    public boolean cellIsEmpty(Position pos) {
        return (matrix.get(pos) == null);
    }

    @Override
    public boolean containsEmptyCell() {
        return matrix.contains(null);
    }

    @Override
    public int getDimension() {
        return matrix.getDimension().rows;
    }

    @Override
    public Player.Id get(Position pos) {
        return matrix.get(pos);
    }

    @Override
    public void clear() {
        matrix.fill(null);
    }

    @Override
    public void set(Position pos, Player.Id id) {
        matrix.set(pos, id);
    }
}
