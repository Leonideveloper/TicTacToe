package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.View;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.MoveProgressBar;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
class MoveProgressBarImpl implements MoveProgressBar {

    private static class MapKeys {
        final static String displayed = "MoveProgressBar.displayed";
        final static String movePlayerId = "MoveProgressBar.movePlayerId";
    }

    private final View firstPlayerProgressBar;
    private final View secondPlayerProgressBar;
    private Boolean displayed;
    private Player.Id movePlayerId;

    MoveProgressBarImpl(Activity activity) {
        firstPlayerProgressBar = activity.findViewById(R.id.firstPlayerProgressBar);
        secondPlayerProgressBar = activity.findViewById(R.id.secondPlayerProgressBar);
        hide();
    }

    MoveProgressBarImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        Boolean needToDisplay = (Boolean) savedState.get(MapKeys.displayed);
        if (needToDisplay) {
            Player.Id playerId = (Player.Id) savedState.get(MapKeys.movePlayerId);
            show(playerId);
        }
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.displayed, displayed);
        if (displayed) {
            outState.put(MapKeys.movePlayerId, movePlayerId);
        }
    }

    @Override
    public void show(Player.Id playerId) {
        displayed = true;
        movePlayerId = playerId;
        if (playerId == Player.Id.FIRST_PLAYER) {
            setVisibility(View.VISIBLE, View.INVISIBLE);
        } else {
            setVisibility(View.INVISIBLE, View.VISIBLE);
        }
    }

    @Override
    public void hide() {
        displayed = false;
        setVisibility(View.INVISIBLE, View.INVISIBLE);
    }

    private void setVisibility(int firstPlayerVisibility, int secondPlayerVisibility) {
        firstPlayerProgressBar.setVisibility(firstPlayerVisibility);
        secondPlayerProgressBar.setVisibility(secondPlayerVisibility);
    }
}
