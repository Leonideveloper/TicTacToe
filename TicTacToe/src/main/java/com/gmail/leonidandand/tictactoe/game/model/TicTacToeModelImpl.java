package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudge;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudgeImpl;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameState;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 07.09.13.
 */
public class TicTacToeModelImpl implements TicTacToeModel {
    private final int gameBoardDimension;
    private final Matrix<Player.Id> gameBoard;
    private final GameJudge gameJudge;
    private final Score score;

    private final List<OnNeedToShowMoveListener> onNeedToShowMoveListeners;
    private final List<OnMovePlayerChangedListener> onMovePlayerChangedListeners;
    private final List<OnGameFinishedListener> onGameFinishedListeners;
    private final List<OnScoreChangedListener> onScoreChangedListeners;

    private Player player_1;
    private Player player_2;
    private Player movePlayer;


    public TicTacToeModelImpl(int gameBoardDimension, Player player_1, Player player_2) {
        onNeedToShowMoveListeners = new ArrayList<OnNeedToShowMoveListener>();
        onMovePlayerChangedListeners = new ArrayList<OnMovePlayerChangedListener>();
        onGameFinishedListeners = new ArrayList<OnGameFinishedListener>();
        onScoreChangedListeners = new ArrayList<OnScoreChangedListener>();

        this.gameBoardDimension = gameBoardDimension;

        gameBoard = new ArrayMatrix<Player.Id>(gameBoardDimension, gameBoardDimension);
        clearGameBoard();

        gameJudge = new GameJudgeImpl(gameBoard);

        score = new Score();

        setPlayers(player_1, player_2);
    }

    private void clearGameBoard() {
        gameBoard.fill(null);
    }

    private void setPlayers(Player player_1, Player player_2) {
        this.player_1 = player_1;
        this.player_2 = player_2;
        player_1.setModel(this);
        player_2.setModel(this);
        player_1.enableMoves();
        movePlayer = player_1;
    }

    @Override
    public boolean isEmptyCell(Position pos) {
        return gameBoard.get(pos) == null;
    }

    @Override
    public int getGameBoardDimension() {
        return gameBoardDimension;
    }

    @Override
    public Player.Id getCellValueAtPos(Position pos) {
        return gameBoard.get(pos);
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void onMove(Position movePosition, Player player) {
        player.disableMoves();
        gameBoard.set(movePosition, player.getId());
        notifyOnNeedToShowMoveListeners(movePosition, player.getId());
        TicTacToeResult result = gameJudge.getResult();
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
        clearGameBoard();
    }

    private void notifyOnGameFinishedListeners(TicTacToeResult result) {
        for (OnGameFinishedListener each : onGameFinishedListeners) {
            each.onGameFinished(result);
        }
    }

    private void updateScoreIfNeed(GameState gameState) {
        if (gameState == GameState.PLAYER_1_WINS) {
            score.increaseScoreOfPlayer1();
            notifyOnScoreChangedListeners();
        } else if (gameState == GameState.PLAYER_2_WINS) {
            score.increaseScoreOfPlayer2();
            notifyOnScoreChangedListeners();
        }
    }

    private void notifyOnScoreChangedListeners() {
        for (OnScoreChangedListener each : onScoreChangedListeners) {
            each.onScoreChanged();
        }
    }

    private Player defineNextMovePlayer(GameState gameState) {
        switch (gameState) {
        case DRAW:
            return movePlayer;
        case PLAYER_1_WINS:
            return player_1;
        case PLAYER_2_WINS:
            return player_2;
        }
        throw new IllegalArgumentException("unknown argument - gameState");
    }

    private Player playerOtherThan(Player player) {
        return (player == player_1) ? player_2 : player_1;
    }

    @Override
    public void onViewIsReadyToStartGame() {
        notifyOnMovePlayerChangedListeners();
        movePlayer.enableMoves();
    }

    private void notifyOnMovePlayerChangedListeners() {
        for (OnMovePlayerChangedListener each : onMovePlayerChangedListeners) {
            each.onMovePlayerChanged(movePlayer.getId());
        }
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
    public void removeOnGameFinishedListener(OnGameFinishedListener listener) {
        onGameFinishedListeners.remove(listener);
    }

    @Override
    public void removeOnScoreChangedListener(OnScoreChangedListener listener) {
        onScoreChangedListeners.remove(listener);
    }

    @Override
    public void removeOnNeedToShowMoveListener(OnNeedToShowMoveListener listener) {
        onNeedToShowMoveListeners.remove(listener);
    }

    @Override
    public void removeOnMovePlayerChangedListener(OnMovePlayerChangedListener listener) {
        onMovePlayerChangedListeners.remove(listener);
    }
}
