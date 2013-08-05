package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class TicTacToeResult {
    private final GameState gameState;
    private final Collection<Matrix.Position> cellsOnFire;

    public static TicTacToeResult unknownResult() {
        return new TicTacToeResult(GameState.UNKNOWN, new ArrayList<Matrix.Position>());
    }

    public static TicTacToeResult drawResult() {
        return new TicTacToeResult(GameState.DRAW, new ArrayList<Matrix.Position>());
    }

    public TicTacToeResult(GameState gameState, Collection<Matrix.Position> cellsOnFire) {
        this.gameState = gameState;
        this.cellsOnFire = cellsOnFire;
    }

    public GameState gameState() {
        return gameState;
    }

    public Collection<Matrix.Position> cellsOnFire() {
        return cellsOnFire;
    }

    public boolean isKnown() {
        return gameState != GameState.UNKNOWN;
    }
}
