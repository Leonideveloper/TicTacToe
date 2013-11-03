package com.gmail.landanurm.tictactoe.game.android_view_components_provider;

import android.app.Activity;
import android.view.View;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.NextMoveProgressBar;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
class NextMoveProgressBarImpl implements NextMoveProgressBar {

    private static class MapKeys {
        final static String displayed = "NextMoveProgressBar.displayed";
        final static String idOfPlayerWhoShouldMoveNext = "NextMoveProgressBar.playerId";
    }

    private final View firstPlayerProgressBar;
    private final View secondPlayerProgressBar;
    private boolean displayed;
    private Player.Id idOfPlayerWhoShouldMoveNext;

    NextMoveProgressBarImpl(Activity activity) {
        firstPlayerProgressBar = activity.findViewById(R.id.firstPlayerProgressBar);
        secondPlayerProgressBar = activity.findViewById(R.id.secondPlayerProgressBar);
        hide();
    }

    @Override
    public void hide() {
        displayed = false;
        idOfPlayerWhoShouldMoveNext = null;
        setVisibility(View.INVISIBLE, View.INVISIBLE);
    }

    private void setVisibility(int firstPlayerVisibility, int secondPlayerVisibility) {
        firstPlayerProgressBar.setVisibility(firstPlayerVisibility);
        secondPlayerProgressBar.setVisibility(secondPlayerVisibility);
    }

    NextMoveProgressBarImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        restoreStateFrom(savedState);
    }

    private void restoreStateFrom(Map<String,Serializable> savedState) {
        Boolean needToDisplay = (Boolean) savedState.get(MapKeys.displayed);
        if (needToDisplay) {
            Player.Id playerId = (Player.Id) savedState.get(MapKeys.idOfPlayerWhoShouldMoveNext);
            show(playerId);
        }
    }

    @Override
    public void show(Player.Id playerId) {
        displayed = true;
        idOfPlayerWhoShouldMoveNext = playerId;
        if (playerId == Player.Id.FIRST_PLAYER) {
            setVisibility(View.VISIBLE, View.INVISIBLE);
        } else {
            setVisibility(View.INVISIBLE, View.VISIBLE);
        }
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.displayed, displayed);
        if (displayed) {
            outState.put(MapKeys.idOfPlayerWhoShouldMoveNext, idOfPlayerWhoShouldMoveNext);
        }
    }
}
