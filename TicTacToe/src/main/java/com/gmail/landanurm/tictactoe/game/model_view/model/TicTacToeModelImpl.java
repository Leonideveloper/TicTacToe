package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
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
public class TicTacToeModelImpl implements TicTacToeModel, Serializable {

    private final List<OnNewGameStartedListener> onNewGameStartedListeners;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnScoreChangedListener> onScoreChangedListeners;
    private final List<OnNeedToShowMoveListener> onNeedToShowMoveListeners;
    private final List<OnMovePlayerChangedListener> onMovePlayerChangedListeners;

    private final GameBoard gameBoard;
    private final TicTacToeJudge judge;
    private final Score score;

    private final Player firstPlayer;
    private final Player secondPlayer;

    private Player movePlayer;


    public TicTacToeModelImpl(int gameBoardDimension, PlayersFactory playersFactory,
                              String firstPlayerType, String secondPlayerType) {

        onNewGameStartedListeners = new ArrayList<OnNewGameStartedListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();
        onNeedToShowMoveListeners = new ArrayList<OnNeedToShowMoveListener>();
        onMovePlayerChangedListeners = new ArrayList<OnMovePlayerChangedListener>();

        gameBoard = new GameBoardImpl(gameBoardDimension);
        judge = new TicTacToeJudgeImpl(gameBoard);
        score = new Score();

        firstPlayer = playersFactory.createFirstPlayer(firstPlayerType, this);
        secondPlayer = playersFactory.createSecondPlayer(secondPlayerType, this);

        movePlayer = firstPlayer;
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
    public ReadOnlyGameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void onMove(Position movePos, Player player) {
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

    private void notifyOnNeedToShowMoveListeners(Position pos, Player.Id id) {
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

    private void notifyOnMovePlayerChangedListeners() {
        for (OnMovePlayerChangedListener each : onMovePlayerChangedListeners) {
            each.onMovePlayerChanged(movePlayer.getId());
        }
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

    @Override
    public void setOnNewGameStartedListener(OnNewGameStartedListener listener) {
        onNewGameStartedListeners.clear();
        onNewGameStartedListeners.add(listener);
    }

    @Override
    public void setOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.clear();
        onGameFinishedListeners.add(listener);
    }

    @Override
    public void setOnScoreChangedListener(OnScoreChangedListener listener) {
        onScoreChangedListeners.clear();
        onScoreChangedListeners.add(listener);
    }

    @Override
    public void setOnNeedToShowMoveListener(OnNeedToShowMoveListener listener) {
        onNeedToShowMoveListeners.clear();
        onNeedToShowMoveListeners.add(listener);
    }

    @Override
    public void setOnMovePlayerChangedListener(OnMovePlayerChangedListener listener) {
        onMovePlayerChangedListeners.clear();
        onMovePlayerChangedListeners.add(listener);
    }
}
