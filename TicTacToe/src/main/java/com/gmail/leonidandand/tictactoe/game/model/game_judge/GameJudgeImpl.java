package com.gmail.leonidandand.tictactoe.game.model.game_judge;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameJudgeImpl implements GameJudge {
    private final Matrix<Player.Id> gameBoard;
    private final int gameBoardDimension;
    private final LineCellsPositions lineCellsPositions;

    public GameJudgeImpl(Matrix<Player.Id> gameBoard) {
        this.gameBoard = gameBoard;
        this.gameBoardDimension = gameBoard.getDimension().rows;
        this.lineCellsPositions = new LineCellsPositions(gameBoardDimension);
    }

    @Override
    public TicTacToeResult getResult() {
        TicTacToeResult result = checkBoard();
        if (result.isKnown()) {
            return result;
        }
        if (gameBoard.contains(null)) {
            return TicTacToeResult.unknownResult();
        }
        return TicTacToeResult.drawResult();
    }

    private TicTacToeResult checkBoard() {
        // max number of fire-lines: 4 fire-lines (need to combine fire-lines):
        // *****************
        // X - - - X - - - X
        // - X - - X - - X -
        // - - X - X - X - -
        // - - - X X X - - -
        // X X X X X X X X X
        // - - - X X X - - -
        // - - X - X - X - -
        // - X - - X - - X -
        // X - - - X - - - X
        // *****************
        TicTacToeResult result = combineFireLines(
                checkRows(),
                checkColumns(),
                checkLeftUpperDiagonal(),
                checkRightUpperDiagonal()
        );
        return result;
    }

    private TicTacToeResult combineFireLines(TicTacToeResult... results) {
        List<TicTacToeResult> matchDecidedResults = matchDecidedResults(results);
        if (matchDecidedResults.isEmpty()) {
            return TicTacToeResult.unknownResult();
        } else {
            return resultWithCombinedFireLines(matchDecidedResults);
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

    private TicTacToeResult resultWithCombinedFireLines(List<TicTacToeResult> resultsToCombine) {
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

    private TicTacToeResult checkColumns() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            TicTacToeResult result = checkColumn(i);
            if (result.isKnown()) {
                return result;
            }
        }
        return TicTacToeResult.unknownResult();
    }

    private TicTacToeResult checkRow(int row) {
        return resultBy(lineCellsPositions.onRow(row), FireLine.Type.ROW);
    }

    private TicTacToeResult checkColumn(int column) {
        return resultBy(lineCellsPositions.onColumn(column), FireLine.Type.COLUMN);
    }

    private TicTacToeResult checkLeftUpperDiagonal() {
        return resultBy(lineCellsPositions.onLeftUpperDiagonal(),
                        FireLine.Type.LEFT_UPPER_DIAGONAL);
    }

    private TicTacToeResult checkRightUpperDiagonal() {
        return resultBy(lineCellsPositions.onRightUpperDiagonal(),
                        FireLine.Type.RIGHT_UPPER_DIAGONAL);
    }

    private TicTacToeResult resultBy(List<Position> cellsPositions, FireLine.Type fireLineType) {
        Position firstCellOnLinePosition = cellsPositions.get(0);
        Player.Id firstCellOnLine = gameBoard.get(firstCellOnLinePosition);
        if (firstCellOnLine == null) {
            return TicTacToeResult.unknownResult();
        }
        for (int i = 1; i < gameBoardDimension; ++i) {
            Position currentPosition = cellsPositions.get(i);
            Player.Id currentCell = gameBoard.get(currentPosition);
            if (firstCellOnLine != currentCell) {
                return TicTacToeResult.unknownResult();
            }
        }
        Collection<FireLine> fireLines = new ArrayList<FireLine>();
        fireLines.add(new FireLine(cellsPositions, fireLineType));
        return new TicTacToeResult(stateByCell(firstCellOnLine), fireLines);
    }

    private GameState stateByCell(Player.Id cell) {
        if (cell == Player.Id.PLAYER_1) {
            return GameState.PLAYER_1_WINS;
        } else if (cell == Player.Id.PLAYER_2) {
            return GameState.PLAYER_2_WINS;
        }
        throw new IllegalArgumentException("Input cell must be not empty!");
    }
}
