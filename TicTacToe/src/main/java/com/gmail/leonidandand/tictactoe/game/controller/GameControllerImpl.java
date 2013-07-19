package com.gmail.leonidandand.tictactoe.game.controller;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameView;
import com.gmail.leonidandand.tictactoe.game.view.GameViewAndroidImpl;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameControllerImpl implements GameController {

    private final GameModel model;
    private final GameView view;

    public GameControllerImpl(GameModel model, Activity activity) {
        this.model = model;
        this.view = new GameViewAndroidImpl(this, model, activity);
    }

    @Override
    public void onViewIsReadyToStartGame() {
        model.onViewIsReadyToStartGame();
    }

    @Override
    public void onPlayerMove(Matrix.Position movePos) {
        view.blockMoves();
        model.onPlayerTurn(movePos);
        view.unblockMoves();
    }
}
