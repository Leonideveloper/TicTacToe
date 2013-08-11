package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.game.model.listeners.OnScoreChangedListener;
import com.gmail.leonidandand.tictactoe.game.model.Score;

/**
 * Created by Leonid on 26.07.13.
 */
public abstract class GameViewImpl
                    implements  GameView,
                                OnCellClickListener,
                                OnOpponentMoveListener,
                                OnGameFinishedListener,
                                OnScoreChangedListener {

    private final GameController controller;
    private final GameModel model;

    private boolean movesBlocked;
    private boolean gameFinished;

    public GameViewImpl(GameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
        model.addOnOpponentMoveListener(this);
        model.addOnGameFinishedListener(this);
        model.addOnScoreChangedListener(this);
        gameFinished = false;
        movesBlocked = false;
    }

    @Override
    public void blockMoves() {
        movesBlocked = true;
        getOpponentMoveProgressBar().show();
    }

    protected abstract OpponentMoveProgressBar getOpponentMoveProgressBar();

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
            getGameBoard().clear();
            controller.onViewIsReadyToStartGame();
        } else if (model.emptyCell(cellPos) && !movesBlocked()) {
            getGameBoard().showMove(cellPos);
            controller.onPlayerMove(cellPos);
        }
    }

    protected abstract GameBoard getGameBoard();

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

    protected abstract GameResultDisplay getGameResultDisplay();

    @Override
    public void onScoreChanged() {
        Score newScore = model.getScore();
        getGameScoreDisplay().showScore(newScore);
    }

    protected abstract GameScoreDisplay getGameScoreDisplay();

}
