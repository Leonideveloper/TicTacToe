package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.widget.Toast;

import com.gmail.leonidandand.tictactoe.game.model.GameState;
import com.gmail.leonidandand.tictactoe.game.view.GameResultDisplay;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameResultDisplayAndroidToastImpl implements GameResultDisplay {

    private final Activity activity;

    public GameResultDisplayAndroidToastImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void show(GameState gameState) {
        Toast.makeText(activity, gameState.name(), Toast.LENGTH_LONG).show();
    }
}
