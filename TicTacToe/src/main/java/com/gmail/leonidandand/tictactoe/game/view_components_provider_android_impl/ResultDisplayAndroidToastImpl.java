package com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.widget.Toast;

import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.leonidandand.tictactoe.game.model_view.view.ResultDisplay;

/**
 * Created by Leonid on 19.07.13.
 */
class ResultDisplayAndroidToastImpl implements ResultDisplay {
    private final Activity activity;
    private boolean displayed;
    private TicTacToeResult.GameState gameState;

    ResultDisplayAndroidToastImpl(Activity activity) {
        this.activity = activity;
        hide();
    }

    ResultDisplayAndroidToastImpl(Activity activity, ResultDisplayAndroidToastImpl toRestore) {
        this(activity);
        if (toRestore.displayed) {
            show(toRestore.gameState);
        }
    }

    @Override
    public void show(TicTacToeResult.GameState gameState) {
        this.gameState = gameState;
        Toast.makeText(activity, gameState.name(), Toast.LENGTH_SHORT).show();
        displayed = true;
    }

    @Override
    public void hide() {
        this.gameState = null;
        displayed = false;
    }
}
