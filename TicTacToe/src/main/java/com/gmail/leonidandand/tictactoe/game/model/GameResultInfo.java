package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameResultInfo {
    private final GameResult gameResult;
    private final List<Matrix.Position> cellsOnFire;

    public static GameResultInfo unknownResultInfo() {
        return new GameResultInfo(GameResult.UNKNOWN, new ArrayList<Matrix.Position>());
    }

    public static GameResultInfo drawResultInfo() {
        return new GameResultInfo(GameResult.DRAW, new ArrayList<Matrix.Position>());
    }

    public GameResultInfo(GameResult gameResult, List<Matrix.Position> cellsOnFire) {
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
