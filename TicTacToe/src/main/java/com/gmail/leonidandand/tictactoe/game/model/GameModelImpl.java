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
    private GameResultInfo lastGameResultInfo;

    public GameModelImpl(Dimension gameBoardDimension) {
        dimension = gameBoardDimension;
        onOpponentMoveListeners = new ArrayList<OnOpponentMoveListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        gameBoard = new Matrix<Cell>(gameBoardDimension);
        initGameBoardByEmpty();
        opponent = new StupidAIOpponent(gameBoard);
        gameJudge = new GameJudgeImpl(gameBoard);
        lastGameResultInfo = GameResultInfo.unknownResultInfo();
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
    public void onViewIsReadyToStartGame() {
        if (lastGameResultInfo.gameResult() == GameResult.OPPONENT_WINS) {
            opponentMove();
        }
    }

    @Override
    public void onPlayerTurn(Matrix.Position turnPosition) {
        gameBoard.set(turnPosition, Cell.PLAYER);
        if (gameNotFinished()) {
            opponentMove();
        }
        GameResultInfo gameResultInfo = gameJudge.gameResultInfo();
        if (gameResultInfo.resultIsKnown()) {
            onGameFinished(gameResultInfo);
        }
    }

    private boolean gameNotFinished() {
        return !gameJudge.gameResultInfo().resultIsKnown();
    }

    private void opponentMove() {
        Matrix.Position opponentMovePos = opponent.positionToMove();
        gameBoard.set(opponentMovePos, Cell.OPPONENT);
        notifyOnOpponentMoveListeners(opponentMovePos);
    }

    private void onGameFinished(GameResultInfo gameResultInfo) {
        lastGameResultInfo = gameResultInfo;
        notifyOnGameFinishedListeners(gameResultInfo);
        initGameBoardByEmpty();
    }

    @Override
    public void addOnOpponentMoveListener(OnOpponentMoveListener listener) {
        onOpponentMoveListeners.add(listener);
    }

    private void notifyOnOpponentMoveListeners(Matrix.Position opponentMovePos) {
        for (OnOpponentMoveListener each : onOpponentMoveListeners) {
            each.onOpponentMove(opponentMovePos);
        }
    }

    @Override
    public void addOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.add(listener);
    }

    private void notifyOnGameFinishedListeners(GameResultInfo gameResultInfo) {
        for (OnGameFinishedListener each : onGameFinishedListeners) {
            each.onGameFinished(gameResultInfo);
        }
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }
}
