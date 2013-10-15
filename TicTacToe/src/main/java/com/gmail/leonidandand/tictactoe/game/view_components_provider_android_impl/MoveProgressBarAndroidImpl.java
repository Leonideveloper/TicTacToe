package com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.View;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model_view.view.MoveProgressBar;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
class MoveProgressBarAndroidImpl implements MoveProgressBar {

    private static class MapKeys {
        final static String firstPlayerVisibility = "MoveProgressBar.firstPlayerVisibility";
        final static String secondPlayerVisibility = "MoveProgressBar.secondPlayerVisibility";
    }

    private final View firstPlayerProgressBar;
    private final View secondPlayerProgressBar;
    private int firstPlayerProgressBarVisibility;
    private int secondPlayerProgressBarVisibility;

    MoveProgressBarAndroidImpl(Activity activity) {
        firstPlayerProgressBar = activity.findViewById(R.id.firstPlayerProgressBar);
        secondPlayerProgressBar = activity.findViewById(R.id.secondPlayerProgressBar);
        setVisibility(Player.Id.PLAYER_1, View.INVISIBLE);
        setVisibility(Player.Id.PLAYER_2, View.INVISIBLE);
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.firstPlayerVisibility, firstPlayerProgressBarVisibility);
        outState.put(MapKeys.secondPlayerVisibility, secondPlayerProgressBarVisibility);
    }

    MoveProgressBarAndroidImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        setVisibility(Player.Id.PLAYER_1, (Integer) savedState.get(MapKeys.firstPlayerVisibility));
        setVisibility(Player.Id.PLAYER_2, (Integer) savedState.get(MapKeys.secondPlayerVisibility));
    }

    private void setVisibility(Player.Id playerId, int visibility) {
        if (playerId == Player.Id.PLAYER_1) {
            firstPlayerProgressBar.setVisibility(visibility);
            firstPlayerProgressBarVisibility = visibility;
        } else {
            secondPlayerProgressBar.setVisibility(visibility);
            secondPlayerProgressBarVisibility = visibility;
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
