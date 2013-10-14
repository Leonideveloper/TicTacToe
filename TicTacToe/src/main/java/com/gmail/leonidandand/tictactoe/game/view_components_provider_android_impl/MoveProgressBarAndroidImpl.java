package com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.View;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model_view.view.MoveProgressBar;

/**
 * Created by Leonid on 04.08.13.
 */
class MoveProgressBarAndroidImpl implements MoveProgressBar {
    private final View firstPlayerProgressBar;
    private final View secondPlayerProgressBar;
    private int firstPlayerProgressBarVisibility;
    private int secondPlayerProgressBarVisibility;

    MoveProgressBarAndroidImpl(Activity activity) {
        firstPlayerProgressBar = activity.findViewById(R.id.firstPlayerProgressBar);
        secondPlayerProgressBar = activity.findViewById(R.id.secondPlayerProgressBar);
        hide();
    }

    MoveProgressBarAndroidImpl(Activity activity, MoveProgressBarAndroidImpl toRestore) {
        this(activity);
        setVisibility(Player.Id.PLAYER_1, toRestore.firstPlayerProgressBarVisibility);
        setVisibility(Player.Id.PLAYER_2, toRestore.secondPlayerProgressBarVisibility);
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
