package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.GameInfo;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 26.07.13.
 */
public abstract class GameViewImpl implements GameView, OnCellClickListener,
                        OnOpponentMoveListener, OnGameFinishedListener {

    private final GameController controller;
    private final GameModel model;
    private boolean movesBlocked;
    private boolean gameFinished;

    public GameViewImpl(GameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;
        model.addOnOpponentMoveListener(this);
        model.addOnGameFinishedListener(this);

        gameFinished = false;
        movesBlocked = false;
    }

    protected abstract GameBoard gameBoard();

    protected abstract GameResultDisplay gameResultDisplay();

    protected OnCellClickListener getOnCellClickListener() {
        return this;
    }

    @Override
    public void blockMoves() {
        movesBlocked = true;
    }

    @Override
    public void unblockMoves() {
        movesBlocked = false;
    }

    @Override
    public boolean movesBlocked() {
        return movesBlocked;
    }

    @Override
    public void onCellClick(Matrix.Position cellPos) {
        if (gameFinished) {
            gameFinished = false;
            gameBoard().clear();
            controller.onViewIsReadyToStartGame();
        } else if (model.emptyCell(cellPos) && !movesBlocked()) {
            gameBoard().showMove(cellPos);
            controller.onPlayerMove(cellPos);
        }
    }

    @Override
    public void onOpponentMove(Matrix.Position movePos) {
        gameBoard().showMove(movePos);
    }

    @Override
    public void onGameFinished(GameInfo gameInfo) {
        gameFinished = true;
        gameBoard().showFireLine(gameInfo.cellsOnFire());
        gameResultDisplay().show(gameInfo.gameResult());
    }
}
