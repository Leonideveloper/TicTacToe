package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.widget.Toast;

import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.model.GameState;
import com.gmail.leonidandand.tictactoe.game.view.GameResultDisplay;

import java.util.Map;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameResultDisplayAndroidToastImpl
        implements GameResultDisplay, CapableSaveRestoreState {

    private static final String DISPLAYED_KEY = "GameResultDisplay.displayed";
    private static final String GAME_STATE_KEY = "GameResultDisplay.gameState";

    private final Activity activity;
    private boolean displayed;
    private GameState gameState;

    GameResultDisplayAndroidToastImpl(Activity activity) {
        this.activity = activity;
        hide();
    }

    @Override
    public void show(GameState gameState) {
        this.gameState = gameState;
        Toast.makeText(activity, gameState.name(), Toast.LENGTH_LONG).show();
        displayed = true;
    }

    @Override
    public void hide() {
        this.gameState = null;
        displayed = false;
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(DISPLAYED_KEY, displayed);
        if (displayed) {
            bundle.put(GAME_STATE_KEY, gameState);
        }
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        Boolean needToDisplay = (Boolean) bundle.get(DISPLAYED_KEY);
        if (needToDisplay) {
            GameState savedGameState = (GameState) bundle.get(GAME_STATE_KEY);
            show(savedGameState);
        } else {
            hide();
        }
    }
}
