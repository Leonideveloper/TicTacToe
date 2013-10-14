package com.gmail.leonidandand.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.TicTacToeJudge;
import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.TicTacToeJudgeImpl;
import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnNewGameStartedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.PlayerFactory;

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

    private final Player player_1;
    private final Player player_2;
    private Player movePlayer;

    private boolean gameFinished;


    public TicTacToeModelImpl(int gameBoardDimension, PlayerFactory playerFactory,
                              String firstPlayerType, String secondPlayerType) {

        onNewGameStartedListeners = new ArrayList<OnNewGameStartedListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();
        onNeedToShowMoveListeners = new ArrayList<OnNeedToShowMoveListener>();
        onMovePlayerChangedListeners = new ArrayList<OnMovePlayerChangedListener>();

        gameBoard = new GameBoardImpl(gameBoardDimension);
        judge = new TicTacToeJudgeImpl(gameBoard);
        score = new Score();

        player_1 = playerFactory.createFirstPlayer(firstPlayerType, this);
        player_2 = playerFactory.createSecondPlayer(secondPlayerType, this);
        movePlayer = player_1;

        gameFinished = false;
    }

    @Override
    public boolean gameFinished() {
        return gameFinished;
    }

    @Override
    public Player getFirstPlayer() {
        return player_1;
    }

    @Override
    public Player getSecondPlayer() {
        return player_2;
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
            movePlayer = playerOtherThan(movePlayer);
            notifyOnMovePlayerChangedListeners();
            movePlayer.enableMoves();
        }
    }

    private void notifyOnNeedToShowMoveListeners(Position pos, Player.Id id) {
        for (OnNeedToShowMoveListener each : onNeedToShowMoveListeners) {
            each.onNeedToShowMove(pos, id);
        }
    }

    private void onGameFinished(TicTacToeResult result) {
        gameFinished = true;
        updateScoreIfNeed(result.getGameState());
        notifyOnGameFinishedListeners(result);
        movePlayer = defineNextMovePlayer(result.getGameState());
    }

    private void updateScoreIfNeed(TicTacToeResult.GameState gameState) {
        if (gameState == TicTacToeResult.GameState.PLAYER_1_WINS) {
            score.increaseScoreOfPlayer1();
            notifyOnScoreChangedListeners();
        } else if (gameState == TicTacToeResult.GameState.PLAYER_2_WINS) {
            score.increaseScoreOfPlayer2();
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
        case PLAYER_1_WINS:
            return player_1;
        case PLAYER_2_WINS:
            return player_2;
        case DRAW:
            return movePlayer;
        }
        throw new IllegalArgumentException("unknown argument \'gameState\': " + gameState.name());
    }

    private Player playerOtherThan(Player player) {
        return (player.getId() == Player.Id.PLAYER_1) ? player_2 : player_1;
    }

    private void notifyOnMovePlayerChangedListeners() {
        for (OnMovePlayerChangedListener each : onMovePlayerChangedListeners) {
            each.onMovePlayerChanged(movePlayer.getId());
        }
    }

    @Override
    public void startGame() {
        gameFinished = false;
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
