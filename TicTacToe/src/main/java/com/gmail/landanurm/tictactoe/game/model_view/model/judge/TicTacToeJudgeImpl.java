package com.gmail.landanurm.tictactoe.game.model_view.model.judge;

import com.gmail.landanurm.matrix.*;
import com.gmail.landanurm.tictactoe.game.model_view.model.game_board.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class TicTacToeJudgeImpl implements TicTacToeJudge, Serializable {
    private final int gameBoardDimension;
    private final LineCellsPositionsProvider positionsProvider;
    private final ReadOnlyGameBoard gameBoard;

    public TicTacToeJudgeImpl(ReadOnlyGameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.gameBoardDimension = gameBoard.getDimension();
        this.positionsProvider = new LineCellsPositionsProvider(gameBoardDimension);
    }

    @Override
    public TicTacToeResult getResult() {
        TicTacToeResult result = checkGameBoard();
        if (result.isKnown()) {
            return result;
        }
        if (gameBoard.containsEmptyCell()) {
            return TicTacToeResultCreator.createUnknownResult();
        }
        return TicTacToeResultCreator.createDrawResult();
    }

    private TicTacToeResult checkGameBoard() {
        // Max number of fire-lines: 4
        // So need to combine fire-lines
        // *****************      *****************
        // X O O O X O O O X      X O O O X O O O X
        // O X O O X O O X O      O X O O X O O X O
        // O O X O X O X O O      O O X O X O X O O
        // O O O X X X O O O      O O O X X X O O O
        // X X X X - X X X X  =>  X X X X X X X X X  Move at the central cell
        // O O O X X X - - -      O O O X X X - - -
        // O O X - X - X - -      O O X - X - X - -
        // O X - - X - - X -      O X - - X - - X -
        // X O O - X - - - X      X O O - X - - - X
        // *****************      *****************
        return combineResults(
            checkRows(),
            checkColumns(),
            checkLeftUpperDiagonal(),
            checkRightUpperDiagonal()
        );
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
        if (gameBoard.cellIsEmpty(positionOfFirstCellOnLine)) {
            return TicTacToeResultCreator.createUnknownResult();
        }
        Player.Id firstCellOnLine = gameBoard.get(positionOfFirstCellOnLine);
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
        if (cell == Player.Id.FIRST_PLAYER) {
            return TicTacToeResult.GameState.FIRST_PLAYER_WINS;
        } else if (cell == Player.Id.SECOND_PLAYER) {
            return TicTacToeResult.GameState.SECOND_PLAYER_WINS;
        }
        throw new IllegalArgumentException("Input cell must be not empty!");
    }
}
