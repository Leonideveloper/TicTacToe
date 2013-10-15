package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.widget.Toast;

import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.landanurm.tictactoe.game.model_view.view.ResultDisplay;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 19.07.13.
 */
class ResultDisplayAndroidToastImpl implements ResultDisplay {

    private static class MapKeys {
        final static String displayed = "ResultDisplay.displayed";
        final static String gameState = "ResultDisplay.gameState";
    }

    private final Activity activity;
    private boolean displayed;
    private TicTacToeResult.GameState gameState;

    ResultDisplayAndroidToastImpl(Activity activity) {
        this.activity = activity;
        hide();
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.displayed, displayed);
        if (displayed) {
            outState.put(MapKeys.gameState, gameState);
        }
    }

    ResultDisplayAndroidToastImpl(Activity activity, Map<String, Serializable> savedState) {
        this.activity = activity;
        this.displayed = (Boolean) savedState.get(MapKeys.displayed);
        if (displayed) {
            show( (TicTacToeResult.GameState) savedState.get(MapKeys.gameState) );
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
        displayed = false;
        gameState = null;
    }
}
