package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameInfo {
    private final GameResult gameResult;
    private final List<Matrix.Position> cellsOnFire;

    public static GameInfo unknownResult() {
        return new GameInfo(GameResult.UNKNOWN, new ArrayList<Matrix.Position>());
    }

    public static GameInfo drawResult() {
        return new GameInfo(GameResult.DRAW, new ArrayList<Matrix.Position>());
    }

    public GameInfo(GameResult gameResult, List<Matrix.Position> cellsOnFire) {
        this.gameResult = gameResult;
        this.cellsOnFire = cellsOnFire;
    }

    public GameResult gameResult() {
        return gameResult;
    }

    public List<Matrix.Position> cellsOnFire() {
        return cellsOnFire;
    }

    public boolean resultIsKnown() {
        return gameResult != GameResult.UNKNOWN;
    }
}
