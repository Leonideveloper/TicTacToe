package com.gmail.landanurm.tictactoe.game.model_view.view;


import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnNewGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 26.07.13.
 */
public class TicTacToeViewImpl implements TicTacToeView, OnCellClickListener,
                OnNeedToShowMoveListener, OnScoreChangedListener, OnMovePlayerChangedListener,
                OnGameFinishedListener, OnNeedToStartNewGameListener, OnNewGameStartedListener {

    private final GameBoardView gameBoardView;
    private final MoveProgressBar moveProgressBar;
    private final ResultDisplay resultDisplay;
    private final ScoreDisplay scoreDisplay;
    private final StartNewGameRequestor startNewGameRequestor;

    private final List<OnCellClickListener> onCellClickListeners;
    private final TicTacToeModel model;


    public TicTacToeViewImpl(ComponentsProvider viewComponentsProvider, TicTacToeModel model) {

        gameBoardView = viewComponentsProvider.getGameBoardView();
        gameBoardView.setOnCellClickListener(this);

        moveProgressBar = viewComponentsProvider.getMoveProgressBar();

        resultDisplay = viewComponentsProvider.getResultDisplay();

        scoreDisplay = viewComponentsProvider.getScoreDisplay();
        scoreDisplay.showScore(model.getScore());

        startNewGameRequestor = viewComponentsProvider.getStartNewGameRequestor();
        startNewGameRequestor.setOnNeedToStartNewGameListener(this);

        onCellClickListeners = new ArrayList<OnCellClickListener>();

        this.model = model;
        model.addOnNewGameStartedListener(this);
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        model.addOnNeedToShowMoveListener(this);
        model.addOnMovePlayerChangedListener(this);

        ConnectorPlayersToView connectorPlayersToView = new ConnectorPlayersToView(this, model);
        connectorPlayersToView.connect();
    }

    @Override
    public void addOnCellClickListener(OnCellClickListener listener) {
        onCellClickListeners.add(listener);
    }

    @Override
    public void onCellClick(com.gmail.landanurm.matrix.Position cellPos) {
        gameBoardView.blockMoves();
        notifyOnCellClickListeners(cellPos);
        gameBoardView.unblockMoves();
    }

    private void notifyOnCellClickListeners(com.gmail.landanurm.matrix.Position cellPos) {
        for (OnCellClickListener each : onCellClickListeners) {
            each.onCellClick(cellPos);
        }
    }

    @Override
    public void onNeedToShowMove(com.gmail.landanurm.matrix.Position pos, Player.Id playerId) {
        gameBoardView.showMove(pos, playerId);
    }

    @Override
    public void onScoreChanged() {
        scoreDisplay.showScore(model.getScore());
    }

    @Override
    public void onMovePlayerChanged(Player.Id playerId) {
        moveProgressBar.show(playerId);
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameBoardView.showFireLines(result.getFireLines());
        resultDisplay.show(result.getGameState());
        moveProgressBar.hide();
        startNewGameRequestor.requestToStartNewGame();
    }

    @Override
    public void onNeedToStartNewGame() {
        model.startGame();
    }

    @Override
    public void onNewGameStarted() {
        gameBoardView.clear();
        resultDisplay.hide();
        startNewGameRequestor.hideRequest();
    }
}
