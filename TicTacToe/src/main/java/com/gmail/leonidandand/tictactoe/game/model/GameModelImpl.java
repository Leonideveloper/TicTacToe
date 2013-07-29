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
    private final List<OnOpponentMoveListener> onOpponentMoveListeners;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnScoreChangedListener> onScoreChangedListeners;
    private final Matrix<Cell> gameBoard;
    private final Score score;
    private boolean opponentMovesFirst;
    private Opponent opponent;

    public GameModelImpl(Dimension gameBoardDimension) {
        dimension = gameBoardDimension;
        gameBoard = new Matrix<Cell>(gameBoardDimension);
        initGameBoardByEmpty();
        gameJudge = new GameJudgeImpl(gameBoard);
        onOpponentMoveListeners = new ArrayList<OnOpponentMoveListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();
        score = new Score();
        opponentMovesFirst = false;
        setOpponent(OpponentFactory.createDefault());
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
    public void setOpponent(Opponent opponent) {
        opponent.setGameBoard(gameBoard);
        this.opponent = opponent;
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
    public Score getScore() {
        return score;
    }

    @Override
    public void addOnOpponentMoveListener(OnOpponentMoveListener listener) {
        onOpponentMoveListeners.add(listener);
    }

    @Override
    public void addOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.add(listener);
    }

    @Override
    public void addOnScoreChangedListener(OnScoreChangedListener listener) {
        onScoreChangedListeners.add(listener);
    }

    @Override
    public void onPlayerMove(Matrix.Position movePosition) {
        gameBoard.set(movePosition, Cell.PLAYER);
        GameInfo gameInfo = gameJudge.gameInfo();
        if (!gameInfo.resultIsKnown()) {
            opponentMove();
            gameInfo = gameJudge.gameInfo();
        }
        if (gameInfo.resultIsKnown()) {
            onGameFinished(gameInfo);
        }
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
        GameResult gameResult = gameInfo.gameResult();
        opponentMovesFirst = defineOpponentMovesFirst(gameResult);
        notifyOnGameFinishedListeners(gameInfo);
        if (gameResult != GameResult.DRAW) {
            changeScore(gameResult);
            notifyOnScoreChangedListeners();
        }
        initGameBoardByEmpty();
    }

    private boolean defineOpponentMovesFirst(GameResult gameResult) {
        return (gameResult == GameResult.OPPONENT_WINS) ||
               (opponentMovesFirst && (gameResult == GameResult.DRAW));
    }

    private void notifyOnGameFinishedListeners(GameInfo gameInfo) {
        for (OnGameFinishedListener each : onGameFinishedListeners) {
            each.onGameFinished(gameInfo);
        }
    }

    private void changeScore(GameResult gameResult) {
        if (gameResult == GameResult.PLAYER_WINS) {
            score.increasePlayerScore();
        } else if (gameResult == GameResult.OPPONENT_WINS) {
            score.increaseOpponentScore();
        }
    }

    private void notifyOnScoreChangedListeners() {
        for (OnScoreChangedListener each : onScoreChangedListeners) {
            each.onScoreChanged();
        }
    }

    @Override
    public void onViewIsReadyToStartGame() {
        if (opponentMovesFirst) {
            opponentMove();
        }
    }
}
