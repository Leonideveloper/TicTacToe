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

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.firstVisibility, firstVisibility);
        outState.put(MapKeys.secondVisibility, secondVisibility);
    }

    MoveProgressBarAndroidImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity, (Integer) savedState.get(MapKeys.firstVisibility),
                       (Integer) savedState.get(MapKeys.secondVisibility));
    }

    private MoveProgressBarAndroidImpl(Activity activity, int firstVisibility, int secondVisibility) {
        firstPlayerProgressBar = (ProgressBar) activity.findViewById(R.id.firstPlayerProgressBar);
        secondPlayerProgressBar = (ProgressBar) activity.findViewById(R.id.secondPlayerProgressBar);
        setVisibility(Player.Id.PLAYER_1, firstVisibility);
        setVisibility(Player.Id.PLAYER_2, secondVisibility);
    }

    private void setVisibility(Player.Id playerId, int visibility) {
        if (playerId == Player.Id.PLAYER_1) {
            firstPlayerProgressBar.setVisibility(visibility);
            firstVisibility = visibility;
        } else {
            secondPlayerProgressBar.setVisibility(visibility);
            secondVisibility = visibility;
        }
    }

    @Override
    public void show(Player.Id playerId) {
        hide();
        setVisibility(playerId, View.VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(Player.Id.PLAYER_1, View.INVISIBLE);
        setVisibility(Player.Id.PLAYER_2, View.INVISIBLE);
    }
}