package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.View;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.TicTacToeResult;
import com.gmail.landanurm.tictactoe.game.model_view.view.ResultDisplay;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 19.07.13.
 */
class ResultDisplayAndroidImpl implements ResultDisplay {

    private static class MapKeys {
        final static String displayed = "ResultDisplay.displayed";
        final static String gameState = "ResultDisplay.gameState";
    }

    private final View winnerFirstPlayer;
    private final View winnerSecondPlayer;
    private final View loserFirstPlayer;
    private final View loserSecondPlayer;
    private Boolean displayed;
    private TicTacToeResult.GameState gameState;

    ResultDisplayAndroidImpl(Activity activity) {
        winnerFirstPlayer = activity.findViewById(R.id.winnerFirstPlayerImageView);
        winnerSecondPlayer = activity.findViewById(R.id.winnerSecondPlayerImageView);
        loserFirstPlayer = activity.findViewById(R.id.loserFirstPlayerImageView);
        loserSecondPlayer = activity.findViewById(R.id.loserSecondPlayerImageView);
        hide();
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.displayed, displayed);
        if (displayed) {
            outState.put(MapKeys.gameState, gameState);
        }
    }

    ResultDisplayAndroidImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        Boolean needToDisplay = (Boolean) savedState.get(MapKeys.displayed);
        if (needToDisplay) {
            show(
                 (TicTacToeResult.GameState) savedState.get(MapKeys.gameState)
            );
        }
    }

    @Override
    public void show(TicTacToeResult.GameState gameState) {
        hide();
        this.gameState = gameState;
        this.displayed = true;
        if (gameState == TicTacToeResult.GameState.PLAYER_1_WINS) {
            setVisibilityForViews(View.VISIBLE,
                    winnerFirstPlayer, loserSecondPlayer);
        } else if (gameState == TicTacToeResult.GameState.PLAYER_2_WINS) {
            setVisibilityForViews(View.VISIBLE,
                    winnerSecondPlayer, loserFirstPlayer);
        } else if (gameState == TicTacToeResult.GameState.DRAW) {
            setVisibilityForViews(View.VISIBLE,
                    loserFirstPlayer, loserSecondPlayer);
        } else {
            throw new IllegalArgumentException("Unknown gameState: " + gameState);
        }
    }

    @Override
    public void hide() {
        this.displayed = false;
        this.gameState = null;
        setVisibilityForViews(View.INVISIBLE,
                winnerFirstPlayer, winnerSecondPlayer,
                loserFirstPlayer, loserSecondPlayer);
    }

    private void setVisibilityForViews(int visibility, View... views) {
        for (View each : views) {
            each.setVisibility(visibility);
        }
    }
}
