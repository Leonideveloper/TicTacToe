package com.gmail.leonidandand.tictactoe.game.model.game_judge;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.Cell;
import com.gmail.leonidandand.tictactoe.game.model.GameState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameJudgeImpl implements GameJudge {
    private final Matrix<Cell> gameBoard;
    private final int gameBoardDimension;

    public GameJudgeImpl(Matrix<Cell> gameBoard) {
        this.gameBoard = gameBoard;
        this.gameBoardDimension = gameBoard.getDimension().rows;
    }

    @Override
    public TicTacToeResult getResult() {
        TicTacToeResult result = checkBoard();
        if (result.isKnown()) {
            return result;
        }
        if (gameBoard.contains(Cell.EMPTY)) {
            return TicTacToeResult.unknownResult();
        }
        return TicTacToeResult.drawResult();
    }

    private TicTacToeResult checkBoard() {
        // max number of fire-lines: 4 Fire Lines (need to combine):
        // -----------------
        //   X O O X O O X
        //   O X O X O X O
        //   O O X X X O O
        //   X X X X X X X
        //   O O X X X O O
        //   O X O X O X O
        //   X O O X O O X
        TicTacToeResult result = combineFireLines(
                checkRows(),
                checkColumns(),
                checkLeftUpperDiagonal(),
                checkRightUpperDiagonal()
        );
        return result;
    }

    private TicTacToeResult combineFireLines(TicTacToeResult... results) {
        List<TicTacToeResult> resultsToCombine = matchDecidedResults(results);
        if (resultsToCombine.isEmpty()) {
            return TicTacToeResult.unknownResult();
        } else {
            return combinedResults(resultsToCombine);
        }
    }

    private List<TicTacToeResult> matchDecidedResults(TicTacToeResult... results) {
        List<TicTacToeResult> matchDecidedResults = new LinkedList<TicTacToeResult>();
        for (TicTacToeResult each : results) {
            if (each.isKnown()) {
                matchDecidedResults.add(each);
            }
        }
        return matchDecidedResults;
    }

    private TicTacToeResult combinedResults(List<TicTacToeResult> resultsToCombine) {
        TicTacToeResult result = resultsToCombine.get(0);
        Collection<FireLine> fireLines = result.getFireLines();
        for (int i = 1; i < resultsToCombine.size(); ++i) {
            TicTacToeResult each = resultsToCombine.get(i);
            fireLines.addAll(each.getFireLines());
        }
        return result;
    }

    private TicTacToeResult checkRows() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            TicTacToeResult result = checkRow(i);
            if (result.isKnown()) {
                return result;
            }
        }
        return TicTacToeResult.unknownResult();
    }

    private TicTacToeResult checkRow(int row) {
        return resultByCellsPositions(cellsPositionsOnRow(row), FireLine.Type.ROW);
    }

    private List<Position> cellsPositionsOnRow(final int row) {
        List<Position> cells = new ArrayList<Position>(gameBoardDimension);
        for (int column = 0; column < gameBoardDimension; ++column) {
            cells.add(new Position(row, column));
        }
        return cells;
    }

    private TicTacToeResult resultByCellsPositions(
                    List<Position> cellsPositions, FireLine.Type fireLineType) {
        Position firstCellOnLinePosition = cellsPositions.get(0);
        Cell firstCellOnLine = gameBoard.get(firstCellOnLinePosition);
        if (firstCellOnLine == Cell.EMPTY) {
            return TicTacToeResult.unknownResult();
        }
        for (int i = 1; i < gameBoardDimension; ++i) {
            Position currentPosition = cellsPositions.get(i);
            Cell currentCell = gameBoard.get(currentPosition);
            if (firstCellOnLine != currentCell) {
                return TicTacToeResult.unknownResult();
            }
        }
        Collection<FireLine> fireLines = new ArrayList<FireLine>();
        fireLines.add(new FireLine(cellsPositions, fireLineType));
        return new TicTacToeResult(stateByCell(firstCellOnLine), fireLines);
    }

    private GameState stateByCell(Cell cell) {
        if (cell == Cell.PLAYER) {
            return GameState.PLAYER_WINS;
        } else if (cell == Cell.OPPONENT) {
            return GameState.OPPONENT_WINS;
        }
        throw new IllegalArgumentException("Input cell must be not empty!");
    }

    private TicTacToeResult checkColumns() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            TicTacToeResult result = checkColumn(i);
            if (result.isKnown()) {
                return result;
            }
        }
        return TicTacToeResult.unknownResult();
    }

    private TicTacToeResult checkColumn(int column) {
        return resultByCellsPositions(cellsPositionsOnColumn(column), FireLine.Type.COLUMN);
    }

    private List<Position> cellsPositionsOnColumn(final int column) {
        List<Position> cells = new ArrayList<Position>(gameBoardDimension);
        for (int row = 0; row < gameBoardDimension; ++row) {
            cells.add(new Position(row, column));
        }
        return cells;
    }

    private TicTacToeResult checkLeftUpperDiagonal() {
        return resultByCellsPositions(cellsPositionsOnLeftUpperDiagonal(),
                FireLine.Type.LEFT_UPPER_DIAGONAL);
    }

    private List<Position> cellsPositionsOnLeftUpperDiagonal() {
        List<Position> positions = new ArrayList<Position>(gameBoardDimension);
        for (int i = 0; i < gameBoardDimension; ++i) {
            positions.add(new Position(i, i));
        }
        return positions;
    }

    private TicTacToeResult checkRightUpperDiagonal() {
        return resultByCellsPositions(cellsPositionsOnRightUpperDiagonal(),
                FireLine.Type.RIGHT_UPPER_DIAGONAL);
    }

    private List<Position> cellsPositionsOnRightUpperDiagonal() {
        List<Position> positions = new ArrayList<Position>(gameBoardDimension);
        for (int i = 0; i < gameBoardDimension; ++i) {
            positions.add(new Position(i, gameBoardDimension - i - 1));
        }
        return positions;
    }
}
