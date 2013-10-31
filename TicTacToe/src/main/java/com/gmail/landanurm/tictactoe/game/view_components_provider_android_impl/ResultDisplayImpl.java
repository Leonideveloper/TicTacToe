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

    ResultDisplayImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        Boolean needToDisplay = (Boolean) savedState.get(MapKeys.displayed);
        if (needToDisplay) {
            GameState savedGameState = (GameState) savedState.get(MapKeys.gameState);
            show(savedGameState);
        }
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.displayed, displayed);
        if (displayed) {
            outState.put(MapKeys.gameState, gameState);
        }
    }

    @Override
    public void hide() {
        this.displayed = false;
        hideAllViews();
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

    private void hideAllViews() {
        changeVisibilityOfViews(View.INVISIBLE,
                winnerFirstPlayer, winnerSecondPlayer,
                loserFirstPlayer, loserSecondPlayer);
    }

    private void showViews(View... views) {
        changeVisibilityOfViews(View.VISIBLE, views);
    }

    private void changeVisibilityOfViews(int visibility, View... viewsToChangeVisibility) {
        for (View each : viewsToChangeVisibility) {
            each.setVisibility(visibility);
        }
    }
}
