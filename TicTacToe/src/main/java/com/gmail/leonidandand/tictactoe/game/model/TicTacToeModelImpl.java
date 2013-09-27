package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Dimension;
import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.matrix.ReadOnlyMatrix;
import com.gmail.leonidandand.tictactoe.game.model.judge.TicTacToeJudge;
import com.gmail.leonidandand.tictactoe.game.model.judge.TicTacToeJudgeImpl;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model.player.PlayerFactory;
import com.gmail.leonidandand.tictactoe.game.model.result.TicTacToeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 07.09.13.
 */
public class TicTacToeModelImpl implements TicTacToeModel {

    private final List<OnNeedToShowMoveListener> onNeedToShowMoveListeners;
    private final List<OnMovePlayerChangedListener> onMovePlayerChangedListeners;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnScoreChangedListener> onScoreChangedListeners;

    private final Integer gameBoardDimension;
    private final Matrix<Player.Id> gameBoard;
    private final TicTacToeJudge judge;
    private final Score score;

    private final Player player_1;
    private final Player player_2;
    private Player movePlayer;


    public TicTacToeModelImpl(int gameBoardDimension, PlayerFactory playerFactory,
                              String firstPlayerType, String secondPlayerType) {

        onNeedToShowMoveListeners = new ArrayList<OnNeedToShowMoveListener>();
        onMovePlayerChangedListeners = new ArrayList<OnMovePlayerChangedListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();

        this.gameBoardDimension = gameBoardDimension;
        gameBoard = new ArrayMatrix<Player.Id>(new Dimension(gameBoardDimension, gameBoardDimension));
        judge = new TicTacToeJudgeImpl(gameBoard);
        score = new Score();

        player_1 = playerFactory.createFirstPlayer(firstPlayerType, this);
        player_2 = playerFactory.createSecondPlayer(secondPlayerType, this);
        movePlayer = player_1;
    }

    @Override
    public int getGameBoardDimension() {
        return gameBoardDimension;
    }

    @Override
    public ReadOnlyMatrix<Player.Id> getGameBoard() {
        return gameBoard;
    }

    @Override
    public Score getScore() {
        return score;
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
        throw new IllegalArgumentException("unknown argument - gameState");
    }

    private Player playerOtherThan(Player player) {
        return (player == player_1) ? player_2 : player_1;
    }

    private void notifyOnMovePlayerChangedListeners() {
        for (OnMovePlayerChangedListener each : onMovePlayerChangedListeners) {
            each.onMovePlayerChanged(movePlayer.getId());
        }
    }

    @Override
    public void onViewIsReadyToStartGame() {
        clearGameBoard();
        notifyOnMovePlayerChangedListeners();
        movePlayer.enableMoves();
    }

    private void clearGameBoard() {
        gameBoard.fill(null);
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
    public void addOnNeedToShowMoveListener(OnNeedToShowMoveListener listener) {
        onNeedToShowMoveListeners.add(listener);
    }

    @Override
    public void removeOnNeedToShowMoveListener(OnNeedToShowMoveListener listener) {
        onNeedToShowMoveListeners.remove(listener);
    }

    @Override
    public void addOnMovePlayerChangedListener(OnMovePlayerChangedListener listener) {
        onMovePlayerChangedListeners.add(listener);
    }

    @Override
    public void removeOnMovePlayerChangedListener(OnMovePlayerChangedListener listener) {
        onMovePlayerChangedListeners.remove(listener);
    }
}
