package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.tictactoe.game.model_view.model.game_board.GameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeJudge;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeJudgeImpl;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnNewGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 07.09.13.
 */
public class TicTacToeModelImpl implements TicTacToeModel, OnMoveListener, Serializable {

    private transient final List<OnNewGameStartedListener> onNewGameStartedListeners =
                                                  new ArrayList<OnNewGameStartedListener>();
    private transient final List<OnGameFinishedListener> onGameFinishedListeners =
                                                  new ArrayList<OnGameFinishedListener>();
    private transient final List<OnScoreChangedListener> onScoreChangedListeners =
                                                  new ArrayList<OnScoreChangedListener>();
    private transient final List<OnNeedToShowMoveListener> onNeedToShowMoveListeners =
                                                  new ArrayList<OnNeedToShowMoveListener>();
    private transient final List<OnMovePlayerChangedListener> onMovePlayerChangedListeners =
                                                  new ArrayList<OnMovePlayerChangedListener>();

    private final GameBoard gameBoard;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Score score;
    private final TicTacToeJudge judge;

    private Player movePlayer;


    public TicTacToeModelImpl(int gameBoardDimension, PlayersFactory playersFactory,
                              String firstPlayerType, String secondPlayerType) {

        gameBoard = new GameBoardImpl(gameBoardDimension);
        judge = new TicTacToeJudgeImpl(gameBoard);
        score = new Score();

        firstPlayer = playersFactory.createFirstPlayer(firstPlayerType, gameBoard, this);
        secondPlayer = playersFactory.createSecondPlayer(secondPlayerType, gameBoard, this);

        movePlayer = firstPlayer;
    }

    @Override
    public void startGame() {
        gameBoard.clear();
        notifyOnNewGameStartedListeners();
        notifyOnMovePlayerChangedListeners();
        movePlayer.enableMoves();
    }

    private void notifyOnNewGameStartedListeners() {
        for (OnNewGameStartedListener each : onNewGameStartedListeners) {
            each.onNewGameStarted();
        }
    }

    private void notifyOnMovePlayerChangedListeners() {
        for (OnMovePlayerChangedListener each : onMovePlayerChangedListeners) {
            each.onMovePlayerChanged(movePlayer.getId());
        }
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

    @Override
    public void addOnNewGameStartedListener(OnNewGameStartedListener listener) {
        onNewGameStartedListeners.add(listener);
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
    public void addOnNeedToShowMoveListener(OnNeedToShowMoveListener listener) {
        onNeedToShowMoveListeners.add(listener);
    }

    @Override
    public void addOnMovePlayerChangedListener(OnMovePlayerChangedListener listener) {
        onMovePlayerChangedListeners.add(listener);
    }

    @Override
    public void onMove(com.gmail.landanurm.matrix.Position movePos, Player player) {
        player.disableMoves();
        gameBoard.set(movePos, player.getId());
        notifyOnNeedToShowMoveListeners(movePos, player.getId());
        TicTacToeResult result = judge.getResult();
        if (result.isKnown()) {
            onGameFinished(result);
        } else {
            changeMovePlayer();
        }
    }

    private void notifyOnNeedToShowMoveListeners(com.gmail.landanurm.matrix.Position pos, Player.Id id) {
        for (OnNeedToShowMoveListener each : onNeedToShowMoveListeners) {
            each.onNeedToShowMove(pos, id);
        }
    }

    private void onGameFinished(TicTacToeResult result) {
        updateScoreIfNeed(result.getGameState());
        notifyOnGameFinishedListeners(result);
        movePlayer = defineNextMovePlayer(result.getGameState());
    }

    private void updateScoreIfNeed(TicTacToeResult.GameState gameState) {
        if (gameState == TicTacToeResult.GameState.FIRST_PLAYER_WINS) {
            score.increaseFirstPlayerScore();
            notifyOnScoreChangedListeners();
        } else if (gameState == TicTacToeResult.GameState.SECOND_PLAYER_WINS) {
            score.increaseSecondPlayerScore();
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

    private Player defineNextMovePlayer(TicTacToeResult.GameState gameState) {
        switch (gameState) {
            case FIRST_PLAYER_WINS:
                return firstPlayer;
            case SECOND_PLAYER_WINS:
                return secondPlayer;
            case DRAW:
                return movePlayer;
        }
        throw new IllegalArgumentException("unknown gameState: " + gameState);
    }

    private void changeMovePlayer() {
        movePlayer = playerOtherThan(movePlayer);
        notifyOnMovePlayerChangedListeners();
        movePlayer.enableMoves();
    }

    private Player playerOtherThan(Player player) {
        return (player.getId() == Player.Id.FIRST_PLAYER) ? secondPlayer : firstPlayer;
    }
}
