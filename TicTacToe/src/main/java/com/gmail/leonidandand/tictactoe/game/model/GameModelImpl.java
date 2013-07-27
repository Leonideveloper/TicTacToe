package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Dimension;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameModelImpl implements GameModel {

    private final Dimension dimension;
    private final GameJudge gameJudge;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnOpponentMoveListener> onOpponentMoveListeners;
    private final Matrix<Cell> gameBoard;
    private final Opponent opponent;
    private boolean opponentMovesFirst;

    public GameModelImpl(Dimension gameBoardDimension) {
        dimension = gameBoardDimension;
        gameBoard = new Matrix<Cell>(gameBoardDimension);
        initGameBoardByEmpty();
        gameJudge = new GameJudgeImpl(gameBoard);
        onOpponentMoveListeners = new ArrayList<OnOpponentMoveListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        opponent = new StupidAIOpponent(gameBoard);
        opponentMovesFirst = false;
    }

    private void initGameBoardByEmpty() {
        gameBoard.forEach(new Matrix.OnEachHandler<Cell>() {
            @Override
            public void handle(Matrix<Cell> matrix, Matrix.Position pos) {
                gameBoard.set(pos, Cell.EMPTY);
            }
        });
    }

    @Override
    public boolean emptyCell(Matrix.Position pos) {
        return gameBoard.get(pos) == Cell.EMPTY;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public void addOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.add(listener);
    }

    @Override
    public void addOnOpponentMoveListener(OnOpponentMoveListener listener) {
        onOpponentMoveListeners.add(listener);
    }

    @Override
    public void onPlayerMove(Matrix.Position movePosition) {
        gameBoard.set(movePosition, Cell.PLAYER);
        if (gameNotFinished()) {
            opponentMove();
        }
        GameInfo gameInfo = gameJudge.gameInfo();
        if (gameInfo.resultIsKnown()) {
            onGameFinished(gameInfo);
        }
    }

    private boolean gameNotFinished() {
        return !gameJudge.gameInfo().resultIsKnown();
    }

    private void opponentMove() {
        Matrix.Position opponentMovePos = opponent.positionToMove();
        gameBoard.set(opponentMovePos, Cell.OPPONENT);
        notifyOnOpponentMoveListeners(opponentMovePos);
    }

    private void notifyOnOpponentMoveListeners(Matrix.Position opponentMovePos) {
        for (OnOpponentMoveListener each : onOpponentMoveListeners) {
            each.onOpponentMove(opponentMovePos);
        }
    }

    private void onGameFinished(GameInfo gameInfo) {
        opponentMovesFirst = defineOpponentMovesFirst(gameInfo.gameResult());
        notifyOnGameFinishedListeners(gameInfo);
        initGameBoardByEmpty();
    }

    private boolean defineOpponentMovesFirst(GameResult gameResult) {
        return (gameResult == GameResult.OPPONENT_WINS) ||
               (opponentMovesFirst && gameResult == GameResult.DRAW);
    }

    private void notifyOnGameFinishedListeners(GameInfo gameInfo) {
        for (OnGameFinishedListener each : onGameFinishedListeners) {
            each.onGameFinished(gameInfo);
        }
    }

    @Override
    public void onViewIsReadyToStartGame() {
        if (opponentMovesFirst) {
            opponentMove();
        }
    }
}
