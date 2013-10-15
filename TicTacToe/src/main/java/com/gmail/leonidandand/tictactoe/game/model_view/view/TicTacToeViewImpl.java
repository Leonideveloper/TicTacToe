package com.gmail.leonidandand.tictactoe.game.model_view.view;


import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnNewGameStartedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 26.07.13.
 */
public class TicTacToeViewImpl implements TicTacToeView, Serializable,
                OnCellClickListener, OnNewGameStartedListener, OnNeedToShowMoveListener,
                OnGameFinishedListener, OnScoreChangedListener, OnMovePlayerChangedListener {

    private transient final GameBoardView gameBoardView;
    private transient final MoveProgressBar moveProgressBar;
    private transient final ResultDisplay resultDisplay;
    private transient final ScoreDisplay scoreDisplay;

    private final List<OnCellClickListener> onCellClickListeners;
    private final TicTacToeModel model;

    private boolean movesBlocked;


    public TicTacToeViewImpl(ComponentsProvider viewComponentsProvider,
                             TicTacToeModel model) {

        gameBoardView = viewComponentsProvider.getGameBoardView();
        gameBoardView.setOnCellClickListener(this);

        moveProgressBar = viewComponentsProvider.getMoveProgressBar();

        resultDisplay = viewComponentsProvider.getResultDisplay();

        scoreDisplay = viewComponentsProvider.getScoreDisplay();
        scoreDisplay.showScore(model.getScore());

        onCellClickListeners = new ArrayList<OnCellClickListener>();

        movesBlocked = false;

        this.model = model;
        model.setOnNewGameStartedListener(this);
        model.setOnGameFinishedListener(this);
        model.setOnScoreChangedListener(this);
        model.setOnNeedToShowMoveListener(this);
        model.setOnMovePlayerChangedListener(this);
        connectPlayers(model.getFirstPlayer(), model.getSecondPlayer());
    }

    private void connectPlayers(Player firstPlayer, Player secondPlayer) {
        connectIfPlayerIsOnCellClickListener(firstPlayer);
        connectIfPlayerIsOnCellClickListener(secondPlayer);
    }

    private void connectIfPlayerIsOnCellClickListener(Player player) {
        if (player instanceof OnCellClickListener) {
            addOnCellClickListener((OnCellClickListener) player);
        }
    }

    @Override
    public void addOnCellClickListener(OnCellClickListener listener) {
        onCellClickListeners.add(listener);
    }

    @Override
    public void onCellClick(Position cellPos) {
        if (movesBlocked()) {
            return;
        }
        if (model.gameFinished()) {
            model.startGame();
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

    private void notifyOnCellClickListeners(Position cellPos) {
        for (OnCellClickListener each : onCellClickListeners) {
            each.onCellClick(cellPos);
        }
    }

    @Override
    public void onNeedToShowMove(Position pos, Player.Id playerId) {
        gameBoardView.showMove(pos, playerId);
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameBoardView.showFireLines(result.getFireLines());
        resultDisplay.show(result.getGameState());
        moveProgressBar.hide();
    }

    @Override
    public void onMovePlayerChanged(Player.Id playerId) {
        moveProgressBar.show(playerId);
    }

    @Override
    public void onScoreChanged() {
        scoreDisplay.showScore(model.getScore());
    }

    @Override
    public void onNewGameStarted() {
        gameBoardView.clear();
        resultDisplay.hide();
    }
}
