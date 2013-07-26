package com.gmail.leonidandand.tictactoe.game.controller;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameView;
import com.gmail.leonidandand.tictactoe.game.view.android.GameViewAndroidImpl;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameControllerAndroidImpl extends GameControllerImpl {

    private final GameView gameView;

    public GameControllerAndroidImpl(GameModel model, Activity activity) {
        super(model);
        gameView = new GameViewAndroidImpl(this, model, activity);
    }

    @Override
    protected GameView getGameView() {
        return gameView;
    }
}
