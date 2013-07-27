package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameJudgeImpl implements GameJudge {
    private final Matrix<Cell> gameBoard;
    private final int gameBoardDimension;

    public GameJudgeImpl(Matrix<Cell> gameBoard) {
        this.gameBoard = gameBoard;
        this.gameBoardDimension = gameBoard.rows;
    }

    @Override
    public GameInfo gameInfo() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            GameInfo gameInfo = rowColumnGameInfo(i);
            if (gameInfo.resultIsKnown()) {
                return gameInfo;
            }
        }
        GameInfo gameInfo = diagonalsGameInfo();
        if (gameInfo.resultIsKnown()) {
            return gameInfo;
        }
        return gameBoardContainsEmptyCell()
                ? GameInfo.unknownResult()
                : GameInfo.drawResult();
    }

    private GameInfo rowColumnGameInfo(int index) {
        GameInfo rowGameInfo = rowGameInfo(index);
        if (rowGameInfo.resultIsKnown()) {
            return rowGameInfo;
        } else {
            return columnGameInfo(index);
        }
    }

    private GameInfo rowGameInfo(int row) {
        List<Matrix.Position> rowCellsPositions = rowCellsPositions(row);
        return gameInfoByCellsPositions(rowCellsPositions);
    }

    private List<Matrix.Position> rowCellsPositions(int row) {
        List<Matrix.Position> cells = new ArrayList<Matrix.Position>();
        for (int column = 0; column < gameBoardDimension; ++column) {
            cells.add(new Matrix.Position(row, column));
        }
        return cells;
    }

    private GameInfo gameInfoByCellsPositions(List<Matrix.Position> cellsPositions) {
        Matrix.Position firstCellOnLinePosition = cellsPositions.get(0);
        Cell firstCellOnLine = gameBoard.get(firstCellOnLinePosition);
        if (firstCellOnLine == Cell.EMPTY) {
            return GameInfo.unknownResult();
        }
        for (int i = 1; i < gameBoardDimension; ++i) {
            Matrix.Position currentPosition = cellsPositions.get(i);
            Cell currentCell = gameBoard.get(currentPosition);
            if (firstCellOnLine != currentCell) {
                return GameInfo.unknownResult();
            }
        }
        return new GameInfo(cellToResult(firstCellOnLine), cellsPositions);
    }

    private GameResult cellToResult(Cell cell) {
        if (cell == Cell.PLAYER) {
            return GameResult.PLAYER_WINS;
        } else if (cell == Cell.OPPONENT) {
            return GameResult.OPPONENT_WINS;
        }
        throw new IllegalArgumentException("Input cell must be not empty!");
    }

    private GameInfo columnGameInfo(int column) {
        List<Matrix.Position> columnCellsPositions = columnCellsPositions(column);
        return gameInfoByCellsPositions(columnCellsPositions);
    }

    private List<Matrix.Position> columnCellsPositions(int column) {
        List<Matrix.Position> cells = new ArrayList<Matrix.Position>();
        for (int row = 0; row < gameBoardDimension; ++row) {
            cells.add(new Matrix.Position(row, column));
        }
        return cells;
    }

    private GameInfo diagonalsGameInfo() {
        GameInfo leftUpperDiagonalGameInfo = leftUpperDiagonalGameInfo();
        if (leftUpperDiagonalGameInfo.resultIsKnown()) {
            return leftUpperDiagonalGameInfo;
        } else {
            return rightUpperDiagonalGameInfo();
        }
    }

    private GameInfo leftUpperDiagonalGameInfo() {
        return gameInfoByCellsPositions(leftUpperDiagonalPositions());
    }

    private List<Matrix.Position> leftUpperDiagonalPositions() {
        List<Matrix.Position> positions = new ArrayList<Matrix.Position>();
        for (int i = 0; i < gameBoardDimension; ++i) {
            positions.add(new Matrix.Position(i, i));
        }
        return positions;
    }

    private GameInfo rightUpperDiagonalGameInfo() {
        return gameInfoByCellsPositions(rightUpperDiagonalPositions());
    }

    private List<Matrix.Position> rightUpperDiagonalPositions() {
        List<Matrix.Position> positions = new ArrayList<Matrix.Position>();
        for (int i = 0; i < gameBoardDimension; ++i) {
            positions.add(new Matrix.Position(i, gameBoardDimension - i - 1));
        }
        return positions;
    }

    private boolean gameBoardContainsEmptyCell() {
        for (int row = 0; row < gameBoardDimension; ++row) {
            for (int column = 0; column < gameBoardDimension; ++column) {
                if (gameBoard.get(row, column) == Cell.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
}
