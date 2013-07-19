package com.gmail.leonidandand.tictactoe.game.view;

import android.app.Activity;
import android.widget.Toast;

import com.gmail.leonidandand.tictactoe.game.model.GameResult;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameResultDisplayToastImpl implements GameResultDisplay {

    private final Activity activity;

    public GameResultDisplayToastImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void show(GameResult gameResult) {
        Toast.makeText(activity, gameResult.name(), Toast.LENGTH_LONG).show();
    }
}
