package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.GameState;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeJudge;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeJudgeImpl;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnPlayerWhoShouldMoveNextChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnPlayerMovedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 07.09.13.
 */
public class TicTacToeModelImpl implements TicTacToeModel, Serializable {

    transient private final List<OnGameStartedListener> onGameStartedListeners =
                                                  new ArrayList<OnGameStartedListener>();
    transient private final List<OnGameFinishedListener> onGameFinishedListeners =
                                                  new ArrayList<OnGameFinishedListener>();
    transient private final List<OnScoreChangedListener> onScoreChangedListeners =
                                                  new ArrayList<OnScoreChangedListener>();
    transient private final List<OnPlayerMovedListener> onPlayerMovedListeners =
                                                  new ArrayList<OnPlayerMovedListener>();
    transient private final List<OnPlayerWhoShouldMoveNextChangedListener>
                                 onPlayerWhoShouldMoveNextChangedListeners = new ArrayList<OnPlayerWhoShouldMoveNextChangedListener>();

    private final GameBoard gameBoard;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Score score;
    private final TicTacToeJudge judge;

    private Player playerWhoShouldMoveNext;


    public TicTacToeModelImpl(int gameBoardDimension, PlayersFactory playersFactory,
                              String firstPlayerType, String secondPlayerType) {

        gameBoard = new GameBoardImpl(gameBoardDimension);
        judge = new TicTacToeJudgeImpl(gameBoard);
        score = new Score();

        firstPlayer = playersFactory.createFirstPlayer(firstPlayerType, gameBoard, this);
        secondPlayer = playersFactory.createSecondPlayer(secondPlayerType, gameBoard, this);

        playerWhoShouldMoveNext = firstPlayer;
    }

    @Override
    public void addOnGameStartedListener(OnGameStartedListener listener) {
        onGameStartedListeners.add(listener);
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
    public void addOnPlayerMovedListener(OnPlayerMovedListener listener) {
        onPlayerMovedListeners.add(listener);
    }

    @Override
    public void addOnPlayerWhoShouldMoveNextChangedListener(OnPlayerWhoShouldMoveNextChangedListener listener) {
        onPlayerWhoShouldMoveNextChangedListeners.add(listener);
    }

    @Override
    public void startGame() {
        gameBoard.clear();
        notifyOnGameStartedListeners();
        notifyOnPlayerWhoShouldMoveNextChangedListeners();
        playerWhoShouldMoveNext.enableMoves();
    }

    private void notifyOnGameStartedListeners() {
        for (OnGameStartedListener each : onGameStartedListeners) {
            each.onGameStarted();
        }
    }

    private void notifyOnPlayerWhoShouldMoveNextChangedListeners() {
        for (OnPlayerWhoShouldMoveNextChangedListener each : onPlayerWhoShouldMoveNextChangedListeners) {
            each.onPlayerWhoShouldMoveNextChanged(playerWhoShouldMoveNext.getId());
        }
    }

    @Override
    public void onMove(Position movePos, Player player) {
        player.disableMoves();
        gameBoard.set(movePos, player.getId());
        notifyOnPlayerMovedListeners(movePos, player.getId());
        TicTacToeResult result = judge.getResult();
        if (result.isKnown()) {
            onGameFinished(result);
        } else {
            changePlayerWhoShouldMoveNext();
        }
    }

    private void notifyOnPlayerMovedListeners(Position pos, Player.Id id) {
        for (OnPlayerMovedListener each : onPlayerMovedListeners) {
            each.onPlayerMoved(pos, id);
        }
    }

    private void onGameFinished(TicTacToeResult result) {
        updateScoreIfNeed(result.getGameState());
        notifyOnGameFinishedListeners(result);
        playerWhoShouldMoveNext = definePlayerWhoShouldMoveNext(result.getGameState());
    }

    private void updateScoreIfNeed(GameState gameState) {
        if (gameState != GameState.DRAW) {
            score.updateScoreBy(gameState);
            notifyOnScoreChangedListeners();
        }
    }

    private void notifyOnScoreChangedListeners() {
        for (OnScoreChangedListener each : onScoreChangedListeners) {
            each.onScoreChanged();
        }
    }

    private void notifyOnGameFinishedListeners(TicTacToeResult result) {
        for (OnGameFinishedListener each : onGameFinishedListeners) {
            each.onGameFinished(result);
        }
    }

    private Player definePlayerWhoShouldMoveNext(GameState gameState) {
        switch (gameState) {
        case FIRST_PLAYER_WINS:
            return firstPlayer;
        case SECOND_PLAYER_WINS:
            return secondPlayer;
        case DRAW:
            return playerWhoShouldMoveNext;
        }
        throw new IllegalArgumentException("Unknown gameState: " + gameState);
    }

    private void changePlayerWhoShouldMoveNext() {
        playerWhoShouldMoveNext = playerOtherThan(playerWhoShouldMoveNext);
        notifyOnPlayerWhoShouldMoveNextChangedListeners();
        playerWhoShouldMoveNext.enableMoves();
    }

    private Player playerOtherThan(Player player) {
        return (player.getId() == Player.Id.FIRST_PLAYER) ? secondPlayer : firstPlayer;
    }

    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    @Override
    public Score getScore() {
        return score;
    }
}
