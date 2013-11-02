package com.gmail.landanurm.tictactoe.game.model_view.view;


import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnMovedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnPlayerWhoShouldMoveNextChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.util.ArrayList;
import java.util.List;


public class TicTacToeViewImpl implements TicTacToeView, OnCellClickListener,
        OnMovedListener, OnScoreChangedListener, OnPlayerWhoShouldMoveNextChangedListener,
        OnGameFinishedListener, OnUserWantsToStartNewGameListener, OnGameStartedListener {

    private final GameBoardView gameBoardView;
    private final NextMoveProgressBar nextMoveProgressBar;
    private final ResultDisplay resultDisplay;
    private final ScoreDisplay scoreDisplay;
    private final StartNewGameRequestor startNewGameRequestor;

    private final List<OnCellClickListener> onCellClickListeners;
    private final TicTacToeModel model;


    public TicTacToeViewImpl(ComponentsProvider viewComponentsProvider, TicTacToeModel model) {

        onCellClickListeners = new ArrayList<OnCellClickListener>();

        gameBoardView = viewComponentsProvider.getGameBoardView();
        gameBoardView.setOnCellClickListener(this);
        nextMoveProgressBar = viewComponentsProvider.getNextMoveProgressBar();
        resultDisplay = viewComponentsProvider.getResultDisplay();
        scoreDisplay = viewComponentsProvider.getScoreDisplay();
        scoreDisplay.showScore(model.getScore());
        startNewGameRequestor = viewComponentsProvider.getStartNewGameRequestor();
        startNewGameRequestor.addOnUserWantsToStartNewGameListener(this);

        model.addOnGameStartedListener(this);
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        model.addOnMovedListener(this);
        model.addOnPlayerWhoShouldMoveNextChangedListener(this);
        this.model = model;
    }

    @Override
    public void addOnCellClickListener(OnCellClickListener listener) {
        onCellClickListeners.add(listener);
    }

    @Override
    public void onCellClick(Position cellPos) {
        gameBoardView.blockMoves();
        notifyOnCellClickListeners(cellPos);
        gameBoardView.unblockMoves();
    }

    private void notifyOnCellClickListeners(Position cellPos) {
        for (OnCellClickListener each : onCellClickListeners) {
            each.onCellClick(cellPos);
        }
    }

    @Override
    public void onMoved(Position pos, Player.Id playerId) {
        gameBoardView.showMove(pos, playerId);
    }

    @Override
    public void onScoreChanged() {
        scoreDisplay.showScore(model.getScore());
    }

    @Override
    public void onPlayerWhoShouldMoveNextChanged(Player.Id playerWhoShouldMoveNextId) {
        nextMoveProgressBar.show(playerWhoShouldMoveNextId);
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameBoardView.showFireLines(result.getFireLines());
        resultDisplay.show(result.getGameState());
        nextMoveProgressBar.hide();
        startNewGameRequestor.requestToStartNewGame();
    }

    @Override
    public void onUserWantsToStartNewGame() {
        model.startGame();
    }

    @Override
    public void onGameStarted() {
        gameBoardView.clear();
        resultDisplay.hide();
        startNewGameRequestor.hideRequest();
    }
}
