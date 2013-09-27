package com.gmail.leonidandand.tictactoe.game.view;


import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 26.07.13.
 */
public abstract class TicTacToeViewAbstract implements TicTacToeView, OnCellClickListener,
                                        OnNeedToShowMoveListener, OnGameFinishedListener,
                                        OnScoreChangedListener, OnMovePlayerChangedListener {

    private final List<OnCellClickListener> onCellClickListeners;
    private boolean movesBlocked;
    private boolean gameFinished;
    private TicTacToeModel model;

    protected TicTacToeViewAbstract(TicTacToeModel model) {
        onCellClickListeners = new ArrayList<OnCellClickListener>();
        gameFinished = false;
        movesBlocked = false;
        plugModel(model);
    }

    protected TicTacToeViewAbstract(TicTacToeViewAbstract toRestore) {
        onCellClickListeners = toRestore.onCellClickListeners;
        gameFinished = toRestore.gameFinished;
        movesBlocked = toRestore.movesBlocked;
        plugModel(toRestore.model);
        toRestore.unplugModel();
    }

    private void plugModel(TicTacToeModel model) {
        unplugModel();
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        model.addOnNeedToShowMoveListener(this);
        model.addOnMovePlayerChangedListener(this);
        connectPlayers(model.getFirstPlayer(), model.getSecondPlayer());
        this.model = model;
    }

    private void unplugModel() {
        if (model == null) {
            return;
        }
        model.removeOnGameFinishedListener(this);
        model.removeOnScoreChangedListener(this);
        model.removeOnNeedToShowMoveListener(this);
        model.removeOnMovePlayerChangedListener(this);
        model = null;
    }

    private void connectPlayers(Player firstPlayer, Player secondPlayer) {
        connectIfPlayerIsOnCellClickListener(firstPlayer);
        connectIfPlayerIsOnCellClickListener(secondPlayer);
    }

    private void connectIfPlayerIsOnCellClickListener(Player player) {
        if (player instanceof OnCellClickListener) {
            this.addOnCellClickListener((OnCellClickListener) player);
        }
    }

    protected abstract GameBoardView getGameBoardView();
    protected abstract ResultDisplay getResultDisplay();
    protected abstract ScoreDisplay getScoreDisplay();
    protected abstract MoveProgressBar getMoveProgressBar();

    @Override
    public void addOnCellClickListener(OnCellClickListener listener) {
        onCellClickListeners.add(listener);
    }

    @Override
    public void onCellClick(Position cellPos) {
        if (movesBlocked()) {
            return;
        }
        if (gameFinished) {
            gameFinished = false;
            prepareNewGame();
        } else {
            blockMoves();
            notifyOnCellClickListeners(cellPos);
            unblockMoves();
        }
    }

    private boolean movesBlocked() {
        return movesBlocked;
    }

    private void blockMoves() {
        movesBlocked = true;
    }

    private void unblockMoves() {
        movesBlocked = false;
    }

    protected void prepareNewGame() {
        getGameBoardView().clear();
        getResultDisplay().hide();
        model.onViewIsReadyToStartGame();
    }

    private void notifyOnCellClickListeners(Position cellPos) {
        for (OnCellClickListener each : onCellClickListeners) {
            each.onCellClick(cellPos);
        }
    }

    @Override
    public void onNeedToShowMove(Position pos, Player.Id playerId) {
        getGameBoardView().showMove(pos, playerId);
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameFinished = true;
        getGameBoardView().showFireLines(result.getFireLines());
        getResultDisplay().show(result.getGameState());
        getMoveProgressBar().hide();
    }

    @Override
    public void onMovePlayerChanged(Player.Id movePlayerId) {
        getMoveProgressBar().show(movePlayerId);
    }

    @Override
    public void onScoreChanged() {
        getScoreDisplay().showScore(model.getScore());
    }
}
