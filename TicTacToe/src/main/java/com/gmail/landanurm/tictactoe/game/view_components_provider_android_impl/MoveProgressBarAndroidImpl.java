package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.MoveProgressBar;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
class MoveProgressBarAndroidImpl implements MoveProgressBar {

    private static class MapKeys {
        final static String firstVisibility = "MoveProgressBar.firstVisibility";
        final static String secondVisibility = "MoveProgressBar.secondVisibility";
    }

    private final ProgressBar firstPlayerProgressBar;
    private final ProgressBar secondPlayerProgressBar;
    private int firstVisibility;
    private int secondVisibility;

    MoveProgressBarAndroidImpl(Activity activity) {
        this(activity, View.INVISIBLE, View.INVISIBLE);
    }

    private MoveProgressBarAndroidImpl(Activity activity, int firstVisibility, int secondVisibility) {
        firstPlayerProgressBar = (ProgressBar) activity.findViewById(R.id.firstPlayerProgressBar);
        secondPlayerProgressBar = (ProgressBar) activity.findViewById(R.id.secondPlayerProgressBar);
        setVisibility(Player.Position.FIRST, firstVisibility);
        setVisibility(Player.Position.SECOND, secondVisibility);
    }

    private void setVisibility(Player.Position playerPosition, int visibility) {
        if (playerPosition == Player.Position.FIRST) {
            firstPlayerProgressBar.setVisibility(visibility);
            firstVisibility = visibility;
        } else {
            secondPlayerProgressBar.setVisibility(visibility);
            secondVisibility = visibility;
        }
    }

    MoveProgressBarAndroidImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity, (Integer) savedState.get(MapKeys.firstVisibility),
                       (Integer) savedState.get(MapKeys.secondVisibility));
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.firstVisibility, firstVisibility);
        outState.put(MapKeys.secondVisibility, secondVisibility);
    }

    @Override
    public void show(Player.Position playerPosition) {
        hide();
        setVisibility(playerPosition, View.VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(Player.Position.FIRST, View.INVISIBLE);
        setVisibility(Player.Position.SECOND, View.INVISIBLE);
    }
}
