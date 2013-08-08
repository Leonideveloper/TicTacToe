package com.gmail.leonidandand.tictactoe.game.controller;

import android.app.Activity;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameView;
import com.gmail.leonidandand.tictactoe.game.view.android.GameViewAndroidImpl;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameControllerAndroidImpl implements GameController {
    private final GameModel model;
    private final GameView gameView;

    public GameControllerAndroidImpl(GameModel model, Activity activity) {
        this.model = model;
        gameView = new GameViewAndroidImpl(this, model, activity);
    }

    @Override
    public void onViewIsReadyToStartGame() {
        model.onViewIsReadyToStartGame();
    }

    @Override
    public void onPlayerMove(Position movePos) {
        gameView.blockMoves();
        model.onPlayerMove(movePos);
        gameView.unblockMoves();
    }

    GameView getGameView() {
        return gameView;
    }
}
