package com.gmail.landanurm.tictactoe.game.model_view.view;


import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameFinishedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnGameStartedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnPlayerMovedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnPlayerWhoShouldMoveNextChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.listeners.OnScoreChangedListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;


public class TicTacToeViewImpl implements TicTacToeView,
                                          OnGameStartedListener,
                                          OnGameFinishedListener,
                                          OnUserWantsToRestartGameListener,
                                          OnScoreChangedListener,
                                          OnPlayerMovedListener,
                                          OnPlayerWhoShouldMoveNextChangedListener {


    private final GameBoardView gameBoardView;
    private final NeedToRestartGameRequestor needToRestartGameRequestor;
    private final NextMoveProgressBar nextMoveProgressBar;
    private final ResultDisplay resultDisplay;
    private final ScoreDisplay scoreDisplay;
    private final TicTacToeModel model;


    public TicTacToeViewImpl(ComponentsProvider viewComponentsProvider, TicTacToeModel model) {
        gameBoardView = viewComponentsProvider.getGameBoardView();
        needToRestartGameRequestor = viewComponentsProvider.getNeedToRestartGameRequestor();
        needToRestartGameRequestor.addOnUserWantsToRestartGameListener(this);
        nextMoveProgressBar = viewComponentsProvider.getNextMoveProgressBar();
        resultDisplay = viewComponentsProvider.getResultDisplay();
        scoreDisplay = viewComponentsProvider.getScoreDisplay();
        scoreDisplay.showScore(model.getScore());

        model.addOnGameStartedListener(this);
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        model.addOnPlayerMovedListener(this);
        model.addOnPlayerWhoShouldMoveNextChangedListener(this);
        this.model = model;
    }

    @Override
    public void addOnCellClickListener(OnCellClickListener listener) {
        gameBoardView.addOnCellClickListener(listener);
    }

    @Override
    public void onGameStarted() {
        gameBoardView.clear();
        resultDisplay.hide();
        needToRestartGameRequestor.hideRequest();
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameBoardView.showFireLines(result.getFireLines());
        resultDisplay.show(result.getGameState());
        nextMoveProgressBar.hide();
        needToRestartGameRequestor.requestNeedToRestartGame();
    }

    @Override
    public void onUserWantsToRestartGame() {
        model.startGame();
    }

    @Override
    public void onScoreChanged() {
        scoreDisplay.showScore(model.getScore());
    }

    @Override
    public void onPlayerMoved(Position pos, Player.Id playerId) {
        gameBoardView.showMove(pos, playerId);
    }

    @Override
    public void onPlayerWhoShouldMoveNextChanged(Player.Id playerWhoShouldMoveNextId) {
        nextMoveProgressBar.show(playerWhoShouldMoveNextId);
    }
}
