package com.gmail.leonidandand.tictactoe.game.view;


import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 26.07.13.
 */
public abstract class TicTacToeViewImpl implements TicTacToeView, OnCellClickListener,
                                        OnNeedToShowMoveListener, OnGameFinishedListener,
                                        OnScoreChangedListener, OnMovePlayerChangedListener {

    private final List<OnCellClickListener> onCellClickListeners;
    private boolean movesBlocked;
    protected boolean gameFinished;
    private TicTacToeModel model;

    protected TicTacToeViewImpl(TicTacToeModel model) {
        onCellClickListeners = new ArrayList<OnCellClickListener>();
        gameFinished = false;
        movesBlocked = false;
        plugModel(model);
    }

    @Override
    public void plugModel(TicTacToeModel model) {
        unplugModel();
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        model.addOnNeedToShowMoveListener(this);
        model.addOnMovePlayerChangedListener(this);
        this.model = model;
    }

    @Override
    public void unplugModel() {
        if (model != null) {
            model.removeOnGameFinishedListener(this);
            model.removeOnScoreChangedListener(this);
            model.removeOnNeedToShowMoveListener(this);
            model.removeOnMovePlayerChangedListener(this);
            model = null;
        }
    }

    protected abstract GameBoard getGameBoard();
    protected abstract ResultDisplay getGameResultDisplay();
    protected abstract ScoreDisplay getGameScoreDisplay();
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

    protected boolean movesBlocked() {
        return movesBlocked;
    }

    protected void blockMoves() {
        movesBlocked = true;
    }

    protected void unblockMoves() {
        movesBlocked = false;
    }

    protected void prepareNewGame() {
        getGameBoard().clear();
        getGameResultDisplay().hide();
        model.onViewIsReadyToStartGame();
    }

    private void notifyOnCellClickListeners(Position cellPos) {
        for (OnCellClickListener each : onCellClickListeners) {
            each.onCellClick(cellPos);
        }
    }

    @Override
    public void onNeedToShowMove(Position pos, Player.Id playerId) {
        getGameBoard().showMove(pos, playerId);
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameFinished = true;
        getGameBoard().showFireLines(result.getFireLines());
        getGameResultDisplay().show(result.getGameState());
        getMoveProgressBar().hide();
    }

    @Override
    public void onMovePlayerChanged(Player.Id movePlayerId) {
        getMoveProgressBar().show(movePlayerId);
    }

    @Override
    public void onScoreChanged() {
        getGameScoreDisplay().showScore(model.getScore());
    }
}
