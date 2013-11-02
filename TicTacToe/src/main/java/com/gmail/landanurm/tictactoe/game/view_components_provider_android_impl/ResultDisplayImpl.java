package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.View;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.GameState;
import com.gmail.landanurm.tictactoe.game.model_view.view.ResultDisplay;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 19.07.13.
 */
class ResultDisplayImpl implements ResultDisplay {

    private static class MapKeys {
        final static String displayed = "ResultDisplay.displayed";
        final static String gameState = "ResultDisplay.gameState";
    }

    private final View winnerFirstPlayer;
    private final View winnerSecondPlayer;
    private final View loserFirstPlayer;
    private final View loserSecondPlayer;
    private Boolean displayed;
    private GameState gameState;

    ResultDisplayImpl(Activity activity) {
        winnerFirstPlayer = activity.findViewById(R.id.winnerFirstPlayerImageView);
        winnerSecondPlayer = activity.findViewById(R.id.winnerSecondPlayerImageView);
        loserFirstPlayer = activity.findViewById(R.id.loserFirstPlayerImageView);
        loserSecondPlayer = activity.findViewById(R.id.loserSecondPlayerImageView);
        hide();
    }

    @Override
    public void hide() {
        this.displayed = false;
        this.gameState = null;
        hideAllViews();
    }

    private void hideAllViews() {
        changeVisibility(View.INVISIBLE,
                winnerFirstPlayer, winnerSecondPlayer, loserFirstPlayer, loserSecondPlayer);
    }

    private void changeVisibility(int visibility, View... viewsToChangeVisibility) {
        for (View each : viewsToChangeVisibility) {
            each.setVisibility(visibility);
        }
    }

    ResultDisplayImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        restoreStateFrom(savedState);
    }

    private void restoreStateFrom(Map<String,Serializable> savedState) {
        Boolean needToDisplay = (Boolean) savedState.get(MapKeys.displayed);
        if (needToDisplay) {
            GameState savedGameState = (GameState) savedState.get(MapKeys.gameState);
            show(savedGameState);
        }
    }

    @Override
    public void show(GameState gameState) {
        this.displayed = true;
        this.gameState = gameState;

        hideAllViews();

        switch (gameState) {
        case FIRST_PLAYER_WINS:
            showViews(winnerFirstPlayer, loserSecondPlayer);
            break;
        case SECOND_PLAYER_WINS:
            showViews(winnerSecondPlayer, loserFirstPlayer);
            break;
        case DRAW:
            showViews(loserFirstPlayer, loserSecondPlayer);
            break;
        default:
            throw new IllegalArgumentException("Unknown gameState: " + gameState);
        }
    }

    private void showViews(View... views) {
        changeVisibility(View.VISIBLE, views);
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.displayed, displayed);
        if (displayed) {
            outState.put(MapKeys.gameState, gameState);
        }
    }
}
