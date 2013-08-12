package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Dimension;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.OnEachHandler;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudge;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudgeImpl;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameBoardEmptyOrNotListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnNeedToRestartGameListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.opponent.Opponent;
import com.gmail.leonidandand.tictactoe.game.model.opponent.OpponentFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameModelImpl implements GameModel {

    private final Dimension dimension;
    private final GameJudge gameJudge;
    private final List<OnOpponentMoveListener> onOpponentMoveListeners;
    private final List<OnGameBoardEmptyOrNotListener> onGameBoardEmptyOrNotListeners;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnNeedToRestartGameListener> onNeedToRestartGameListeners;
    private final List<OnScoreChangedListener> onScoreChangedListeners;
    private final Matrix<Cell> gameBoard;
    private final Score score;

    private boolean gameBoardEmpty;
    private boolean opponentMovesFirst;
    private Opponent opponent;

    public GameModelImpl(Dimension gameBoardDimension) {
        onOpponentMoveListeners = new ArrayList<OnOpponentMoveListener>();
        onGameBoardEmptyOrNotListeners = new ArrayList<OnGameBoardEmptyOrNotListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onNeedToRestartGameListeners = new ArrayList<OnNeedToRestartGameListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();

        dimension = gameBoardDimension;
        gameBoard = new ArrayMatrix<Cell>(gameBoardDimension);
        initByEmpty(gameBoard);
        notifyOnGameBoardEmptyOrNotListeners();
        gameJudge = new GameJudgeImpl(gameBoard);

        score = new Score();
        opponentMovesFirst = false;
        setOpponent(OpponentFactory.createDefault());
    }

    private void initByEmpty(final Matrix<Cell> gameBoard) {
        gameBoard.forEach(new OnEachHandler<Cell>() {
            @Override
            public void handle(Position pos, Cell elem) {
                gameBoard.set(pos, Cell.EMPTY);
            }
        });
        gameBoardEmpty = true;
    }

    @Override
    public void setOpponent(Opponent opponent) {
        opponent.setGameBoard(gameBoard);
        this.opponent = opponent;
    }

    @Override
    public boolean emptyCell(Position pos) {
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
    public void addOnGameBoardEmptyOrNotListener(OnGameBoardEmptyOrNotListener listener) {
        onGameBoardEmptyOrNotListeners.add(listener);
    }

    @Override
    public void addOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.add(listener);
    }

    @Override
    public void addOnNeedToRestartGameListener(OnNeedToRestartGameListener listener) {
        onNeedToRestartGameListeners.add(listener);
    }

    @Override
    public void addOnScoreChangedListener(OnScoreChangedListener listener) {
        onScoreChangedListeners.add(listener);
    }

    @Override
    public void onPlayerMove(Position movePosition) {
        gameBoard.set(movePosition, Cell.PLAYER);
        if (gameBoardEmpty) {
            gameBoardEmpty = false;
            notifyOnGameBoardEmptyOrNotListeners();
        }
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
        if (gameBoardEmpty) {
            gameBoardEmpty = false;
            notifyOnGameBoardEmptyOrNotListeners();
        }
    }

    private void notifyOnOpponentMoveListeners(Position opponentMovePos) {
        for (OnOpponentMoveListener each : onOpponentMoveListeners) {
            each.onOpponentMove(opponentMovePos);
        }
    }

    private void onGameFinished(TicTacToeResult result) {
        GameState gameState = result.getGameState();
        opponentMovesFirst = defineOpponentMovesFirst(gameState);
        notifyOnGameFinishedListeners(result);
        if (gameState != GameState.DRAW) {
            changeScore(gameState);
            notifyOnScoreChangedListeners();
        }
        initByEmpty(gameBoard);
        notifyOnGameBoardEmptyOrNotListeners();
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

    @Override
    public void onPlayerGivesUp() {
        score.increaseOpponentScore();
        notifyOnScoreChangedListeners();
        initByEmpty(gameBoard);
        notifyOnGameBoardEmptyOrNotListeners();
        notifyOnNeedToRestartGameListeners(GameState.OPPONENT_WINS);
    }

    private void notifyOnNeedToRestartGameListeners(GameState gameState) {
        for (OnNeedToRestartGameListener each : onNeedToRestartGameListeners) {
            each.onNeedToRestartGame(gameState);
        }
    }

    private void notifyOnGameBoardEmptyOrNotListeners() {
        boolean empty = !gameBoard.contains(Cell.PLAYER) && !gameBoard.contains(Cell.OPPONENT);
        for (OnGameBoardEmptyOrNotListener each : onGameBoardEmptyOrNotListeners) {
            if (empty) {
                each.onBecomeEmpty();
            } else {
                each.onNoLongerEmpty();
            }
        }
    }
}
