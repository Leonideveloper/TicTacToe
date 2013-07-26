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
    public GameInfo gameResultInfo() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            GameInfo resultInfo = rowColumnResultInfo(i);
            if (resultInfo.resultIsKnown()) {
                return resultInfo;
            }
        }
        GameInfo resultInfo = diagonalsResultInfo();
        if (resultInfo.resultIsKnown()) {
            return resultInfo;
        }
        return gameBoardContainsEmptyCell()
                ? GameInfo.unknownResultInfo()
                : GameInfo.drawResultInfo();
    }

    private GameInfo rowColumnResultInfo(int index) {
        GameInfo rowResultInfo = rowResultInfo(index);
        if (rowResultInfo.resultIsKnown()) {
            return rowResultInfo;
        } else {
            return columnResultInfo(index);
        }
    }

    private GameInfo rowResultInfo(int row) {
        List<Matrix.Position> rowCellsPositions = rowCellsPositions(row);
        return resultInfoByCellsPositions(rowCellsPositions);
    }

    private List<Matrix.Position> rowCellsPositions(int row) {
        List<Matrix.Position> cells = new ArrayList<Matrix.Position>();
        for (int column = 0; column < gameBoardDimension; ++column) {
            cells.add(new Matrix.Position(row, column));
        }
        return cells;
    }

    private GameInfo resultInfoByCellsPositions(List<Matrix.Position> cellsPositions) {
        Matrix.Position firstCellOnLinePosition = cellsPositions.get(0);
        Cell firstCellOnLine = gameBoard.get(firstCellOnLinePosition);
        if (firstCellOnLine == Cell.EMPTY) {
            return GameInfo.unknownResultInfo();
        }
        for (int i = 1; i < gameBoardDimension; ++i) {
            Matrix.Position currentPosition = cellsPositions.get(i);
            Cell currentCell = gameBoard.get(currentPosition);
            if (firstCellOnLine != currentCell) {
                return GameInfo.unknownResultInfo();
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

    private GameInfo columnResultInfo(int column) {
        List<Matrix.Position> columnCellsPositions = columnCellsPositions(column);
        return resultInfoByCellsPositions(columnCellsPositions);
    }

    private List<Matrix.Position> columnCellsPositions(int column) {
        List<Matrix.Position> cells = new ArrayList<Matrix.Position>();
        for (int row = 0; row < gameBoardDimension; ++row) {
            cells.add(new Matrix.Position(row, column));
        }
        return cells;
    }

    private GameInfo diagonalsResultInfo() {
        GameInfo leftUpperDiagonalResultInfo = leftUpperDiagonalResultInfo();
        if (leftUpperDiagonalResultInfo.resultIsKnown()) {
            return leftUpperDiagonalResultInfo;
        } else {
            return rightUpperDiagonalResultInfo();
        }
    }

    private GameInfo leftUpperDiagonalResultInfo() {
        return resultInfoByCellsPositions(leftUpperDiagonalPositions());
    }

    private List<Matrix.Position> leftUpperDiagonalPositions() {
        List<Matrix.Position> positions = new ArrayList<Matrix.Position>();
        for (int i = 0; i < gameBoardDimension; ++i) {
            positions.add(new Matrix.Position(i, i));
        }
        return positions;
    }

    private GameInfo rightUpperDiagonalResultInfo() {
        return resultInfoByCellsPositions(rightUpperDiagonalPositions());
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
