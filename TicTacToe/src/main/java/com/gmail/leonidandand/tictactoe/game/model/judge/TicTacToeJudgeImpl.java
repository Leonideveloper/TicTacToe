package com.gmail.leonidandand.tictactoe.game.model.judge;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.result.FireLine;
import com.gmail.leonidandand.tictactoe.game.model.result.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model.result.TicTacToeResultCreator;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class TicTacToeJudgeImpl implements TicTacToeJudge {
    private final Integer gameBoardDimension;
    private final Matrix<Player.Id> gameBoard;
    private final LineCellsPositionsProvider positionsProvider;

    public TicTacToeJudgeImpl(Matrix<Player.Id> gameBoard) {
        this.gameBoard = gameBoard;
        this.gameBoardDimension = gameBoard.getDimension().rows;
        this.positionsProvider = new LineCellsPositionsProvider(gameBoardDimension);
    }

    @Override
    public TicTacToeResult getResult() {
        TicTacToeResult result = checkBoard();
        if (result.isKnown()) {
            return result;
        }
        if (gameBoard.contains(null)) {
            return TicTacToeResultCreator.createUnknownResult();
        }
        return TicTacToeResultCreator.createDrawResult();
    }

    private TicTacToeResult checkBoard() {
        // Max number of fire-lines: 4
        // So need to combine fire-lines
        // *****************      *****************
        // X O O O X O O O X      X O O O X O O O X
        // O X O O X O O X O      O X O O X O O X O
        // O O X O X O X O O      O O X O X O X O O
        // O O O X X X O O O      O O O X X X O O O
        // X X X X>-<X X X X  =>  X X X X>X<X X X X
        // O O O X X X - - -      O O O X X X - - -
        // O O X - X - X - -      O O X - X - X - -
        // O X - - X - - X -      O X - - X - - X -
        // X O O - X - - - X      X O O - X - - - X
        // *****************      *****************
        TicTacToeResult result = combineResults(
                checkRows(),
                checkColumns(),
                checkLeftUpperDiagonal(),
                checkRightUpperDiagonal()
        );
        return result;
    }

    private TicTacToeResult combineResults(TicTacToeResult... results) {
        TicTacToeResult.GameState gameState = gameStateOfResults(results);
        if (gameState == TicTacToeResult.GameState.UNKNOWN) {
            return TicTacToeResultCreator.createUnknownResult();
        } else {
            Collection<FireLine> combinedFireLines = combineFireLinesOfResults(results);
            return new TicTacToeResult(gameState, combinedFireLines);
        }
    }

    private TicTacToeResult.GameState gameStateOfResults(TicTacToeResult... results) {
        for (TicTacToeResult each : results) {
            if (each.isKnown()) {
                return each.getGameState();
            }
        }
        return TicTacToeResult.GameState.UNKNOWN;
    }

    private Collection<FireLine> combineFireLinesOfResults(TicTacToeResult... results) {
        Collection<FireLine> combined = new ArrayList<FireLine>();
        for (TicTacToeResult each : results) {
            combined.addAll(each.getFireLines());
        }
        return combined;
    }

    private TicTacToeResult checkRows() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            TicTacToeResult result = checkRow(i);
            if (result.isKnown()) {
                return result;
            }
        }
        return TicTacToeResultCreator.createUnknownResult();
    }

    private TicTacToeResult checkRow(int row) {
        return resultBy(positionsProvider.onRow(row), FireLine.Type.ROW);
    }

    private TicTacToeResult checkColumns() {
        for (int i = 0; i < gameBoardDimension; ++i) {
            TicTacToeResult result = checkColumn(i);
            if (result.isKnown()) {
                return result;
            }
        }
        return TicTacToeResultCreator.createUnknownResult();
    }

    private TicTacToeResult checkColumn(int column) {
        return resultBy(positionsProvider.onColumn(column), FireLine.Type.COLUMN);
    }

    private TicTacToeResult checkLeftUpperDiagonal() {
        return resultBy(positionsProvider.onLeftUpperDiagonal(),
                        FireLine.Type.LEFT_UPPER_DIAGONAL);
    }

    private TicTacToeResult checkRightUpperDiagonal() {
        return resultBy(positionsProvider.onRightUpperDiagonal(),
                        FireLine.Type.RIGHT_UPPER_DIAGONAL);
    }

    private TicTacToeResult resultBy(List<Position> cellsPositions, FireLine.Type fireLineType) {
        Position positionOfFirstCellOnLine = cellsPositions.get(0);
        Player.Id firstCellOnLine = gameBoard.get(positionOfFirstCellOnLine);
        if (firstCellOnLine == null) {
            return TicTacToeResultCreator.createUnknownResult();
        }
        for (int i = 1; i < gameBoardDimension; ++i) {
            Position currentPosition = cellsPositions.get(i);
            Player.Id currentCell = gameBoard.get(currentPosition);
            if (firstCellOnLine != currentCell) {
                return TicTacToeResultCreator.createUnknownResult();
            }
        }
        Collection<FireLine> fireLines = new ArrayList<FireLine>();
        fireLines.add(new FireLine(cellsPositions, fireLineType));
        return new TicTacToeResult(stateByCell(firstCellOnLine), fireLines);
    }

    private TicTacToeResult.GameState stateByCell(Player.Id cell) {
        if (cell == Player.Id.PLAYER_1) {
            return TicTacToeResult.GameState.PLAYER_1_WINS;
        } else if (cell == Player.Id.PLAYER_2) {
            return TicTacToeResult.GameState.PLAYER_2_WINS;
        }
        throw new IllegalArgumentException("Input cell must be not empty!");
    }
}
