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
                                          OnUserWantsToStartNewGameListener,
                                          OnScoreChangedListener,
                                          OnPlayerMovedListener,
                                          OnPlayerWhoShouldMoveNextChangedListener {


    private final GameBoardView gameBoardView;
    private final NextMoveProgressBar nextMoveProgressBar;
    private final ResultDisplay resultDisplay;
    private final ScoreDisplay scoreDisplay;
    private final StartNewGameRequestor startNewGameRequestor;
    private final TicTacToeModel model;


    public TicTacToeViewImpl(ComponentsProvider viewComponentsProvider, TicTacToeModel model) {
        gameBoardView = viewComponentsProvider.getGameBoardView();
        nextMoveProgressBar = viewComponentsProvider.getNextMoveProgressBar();
        resultDisplay = viewComponentsProvider.getResultDisplay();
        scoreDisplay = viewComponentsProvider.getScoreDisplay();
        scoreDisplay.showScore(model.getScore());
        startNewGameRequestor = viewComponentsProvider.getStartNewGameRequestor();
        startNewGameRequestor.addOnUserWantsToStartNewGameListener(this);

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
        startNewGameRequestor.hideRequest();
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
