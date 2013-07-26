package com.gmail.leonidandand.tictactoe.game.controller;

import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameView;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 26.07.13.
 */
abstract class GameControllerImpl implements GameController {

    private final GameModel model;

    public GameControllerImpl(GameModel model) {
        this.model = model;
    }

    protected abstract GameView getGameView();

    @Override
    public void onViewIsReadyToStartGame() {
        model.onViewIsReadyToStartGame();
    }

    @Override
    public void onPlayerMove(Matrix.Position movePos) {
        getGameView().blockMoves();
        model.onPlayerTurn(movePos);
        getGameView().unblockMoves();
    }
}
