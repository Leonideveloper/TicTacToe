package com.gmail.leonidandand.tictactoe.game.model_view.view;


import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnMovePlayerChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnNeedToShowMoveListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 26.07.13.
 */
public class TicTacToeViewImpl implements TicTacToeView, OnCellClickListener,
                                        OnNeedToShowMoveListener, OnGameFinishedListener,
                                        OnScoreChangedListener, OnMovePlayerChangedListener {

    private final GameBoardView gameBoardView;
    private final ResultDisplay resultDisplay;
    private final ScoreDisplay scoreDisplay;
    private final MoveProgressBar moveProgressBar;

    private final List<OnCellClickListener> onCellClickListeners;

    private boolean movesBlocked;
    private boolean gameFinished;
    private TicTacToeModel model;

    public TicTacToeViewImpl(TicTacToeViewComponentsProvider viewComponentsProvider,
                                TicTacToeModel model) {
        gameBoardView = viewComponentsProvider.getGameBoardView();
        gameBoardView.setOnCellClickListener(this);
        resultDisplay = viewComponentsProvider.getResultDisplay();
        scoreDisplay = viewComponentsProvider.getScoreDisplay();
        scoreDisplay.showScore(model.getScore());
        moveProgressBar = viewComponentsProvider.getMoveProgressBar();

        onCellClickListeners = new ArrayList<OnCellClickListener>();
        gameFinished = false;
        movesBlocked = false;
        plugModel(model);

        prepareNewGame();
    }

    public TicTacToeViewImpl(TicTacToeViewComponentsProvider viewComponentsProvider,
                                TicTacToeViewImpl toRestore) {
        gameBoardView = viewComponentsProvider.getGameBoardView();
        gameBoardView.setOnCellClickListener(this);
        resultDisplay = viewComponentsProvider.getResultDisplay();
        scoreDisplay = viewComponentsProvider.getScoreDisplay();
        scoreDisplay.showScore(toRestore.model.getScore());
        moveProgressBar = viewComponentsProvider.getMoveProgressBar();

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
        gameBoardView.clear();
        resultDisplay.hide();
        model.onViewIsReadyToStartGame();
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
        gameFinished = true;
        gameBoardView.showFireLines(result.getFireLines());
        resultDisplay.show(result.getGameState());
        moveProgressBar.hide();
    }

    @Override
    public void onMovePlayerChanged(Player.Id movePlayerId) {
        moveProgressBar.show(movePlayerId);
    }

    @Override
    public void onScoreChanged() {
        scoreDisplay.showScore(model.getScore());
    }
}
