package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.OnEachHandler;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudge;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudgeImpl;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.opponent.Opponent;
import com.gmail.leonidandand.tictactoe.game.model.tic_tac_toe_result.TicTacToeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameModelImpl implements GameModel {

    protected final Matrix<Cell> gameBoard;
    protected final GameJudge gameJudge;

    private final int gameBoardDimension;
    private final Score score;
    private final List<OnOpponentMoveListener> onOpponentMoveListeners;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnScoreChangedListener> onScoreChangedListeners;
    protected boolean opponentMovesFirst;
    private Opponent opponent;

    public GameModelImpl(int gameBoardDimension) {
        onOpponentMoveListeners = new ArrayList<OnOpponentMoveListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();

        this.gameBoardDimension = gameBoardDimension;

        gameBoard = new ArrayMatrix<Cell>(gameBoardDimension, gameBoardDimension);
        initByEmpty(gameBoard);

        gameJudge = new GameJudgeImpl(gameBoard);

        score = new Score();

        opponentMovesFirst = false;
    }

    private void initByEmpty(final Matrix<Cell> gameBoard) {
        gameBoard.forEach(new OnEachHandler<Cell>() {
            @Override
            public void handle(Position pos, Cell elem) {
                gameBoard.set(pos, Cell.EMPTY);
            }
        });
    }

    @Override
    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
        opponent.setGameBoard(gameBoard);
    }

    @Override
    public boolean emptyCell(Position pos) {
        return gameBoard.get(pos) == Cell.EMPTY;
    }

    @Override
    public int getGameBoardDimension() {
        return gameBoardDimension;
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
    public void removeOnOpponentMoveListener(OnOpponentMoveListener listener) {
        onOpponentMoveListeners.remove(listener);
    }

    @Override
    public void addOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.add(listener);
    }

    @Override
    public void removeOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.remove(listener);
    }

    @Override
    public void addOnScoreChangedListener(OnScoreChangedListener listener) {
        onScoreChangedListeners.add(listener);
    }

    @Override
    public void removeOnScoreChangedListener(OnScoreChangedListener listener) {
        onScoreChangedListeners.remove(listener);
    }

    @Override
    public void onPlayerMove(Position movePosition) {
        gameBoard.set(movePosition, Cell.PLAYER);
        TicTacToeResult result = gameJudge.getResult();
        if (!result.isKnown()) {
            opponentMove();
            result = gameJudge.getResult();
        }
        if (result.isKnown()) {
            onGameFinished(result);
        }
    }

    private void opponentMove() {
        Position opponentMovePos = opponent.positionToMove();
        gameBoard.set(opponentMovePos, Cell.OPPONENT);
        notifyOnOpponentMoveListeners(opponentMovePos);
    }

    private void notifyOnOpponentMoveListeners(Position opponentMovePos) {
        for (OnOpponentMoveListener each : onOpponentMoveListeners) {
            each.onOpponentMove(opponentMovePos);
        }
    }

    protected void onGameFinished(TicTacToeResult result) {
        GameState gameState = result.getGameState();
        opponentMovesFirst = defineOpponentMovesFirst(gameState);
        notifyOnGameFinishedListeners(result);
        if (gameState != GameState.DRAW) {
            changeScore(gameState);
            notifyOnScoreChangedListeners();
        }
        initByEmpty(gameBoard);
    }

    private boolean defineOpponentMovesFirst(GameState gameState) {
        return (gameState == GameState.OPPONENT_WINS) ||
               (opponentMovesFirst && (gameState == GameState.DRAW));
    }

    private void notifyOnGameFinishedListeners(TicTacToeResult result) {
        for (OnGameFinishedListener each : onGameFinishedListeners) {
            each.onGameFinished(result);
        }
    }

    private void changeScore(GameState gameState) {
        if (gameState == GameState.PLAYER_WINS) {
            score.increasePlayerScore();
        } else if (gameState == GameState.OPPONENT_WINS) {
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
