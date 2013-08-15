package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.Score;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;

/**
 * Created by Leonid on 26.07.13.
 */
public abstract class GameViewImpl implements GameView, OnCellClickListener,
        OnOpponentMoveListener, OnGameFinishedListener, OnScoreChangedListener {

    private final GameController controller;
    private final GameModel model;

    private boolean movesBlocked;
    protected boolean gameFinished;


    public GameViewImpl(GameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
        model.addOnOpponentMoveListener(this);
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        gameFinished = false;
        movesBlocked = false;
    }

    protected abstract GameBoard getGameBoard();
    protected abstract GameResultDisplay getGameResultDisplay();
    protected abstract GameScoreDisplay getGameScoreDisplay();
    protected abstract OpponentMoveProgressBar getOpponentMoveProgressBar();

    @Override
    public void blockMoves() {
        movesBlocked = true;
        getOpponentMoveProgressBar().show();
    }

    @Override
    public void unblockMoves() {
        getOpponentMoveProgressBar().hide();
        movesBlocked = false;
    }

    @Override
    public boolean movesBlocked() {
        return movesBlocked;
    }

    @Override
    public void onCellClick(Position cellPos) {
        if (gameFinished) {
            gameFinished = false;
            prepareNewGame();
        } else if (model.emptyCell(cellPos) && !movesBlocked()) {
            getGameBoard().showMove(cellPos);
            controller.onPlayerMove(cellPos);
        }
    }

    private void prepareNewGame() {
        getGameBoard().clear();
        getGameResultDisplay().hide();
        controller.onViewIsReadyToStartGame();
    }

    @Override
    public void onOpponentMove(Position movePos) {
        getGameBoard().showMove(movePos);
    }

    @Override
    public void onGameFinished(TicTacToeResult result) {
        gameFinished = true;
        getGameBoard().showFireLines(result.getFireLines());
        getGameResultDisplay().show(result.getGameState());
    }

    @Override
    public void onScoreChanged() {
        Score newScore = model.getScore();
        getGameScoreDisplay().showScore(newScore);
    }
}
