package com.gmail.landanurm.tictactoe.game.android_view_components_provider;

import android.app.Activity;
import android.view.View;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnUserWantsToRestartGameListener;
import com.gmail.landanurm.tictactoe.game.model_view.view.NeedToRestartGameRequestor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Leonid on 21.10.13.
 */
class NeedToRestartGameRequestorImpl implements NeedToRestartGameRequestor {

    private static class MapKeys {
        final static String requested = "NeedToRestartGameRequestor.requested";
    }

    private final Collection<OnUserWantsToRestartGameListener> onUserWantsToRestartGameListeners;
    private final View restartGameButton;
    private boolean requested;

    NeedToRestartGameRequestorImpl(Activity activity) {
        onUserWantsToRestartGameListeners = new ArrayList<OnUserWantsToRestartGameListener>();
        restartGameButton = activity.findViewById(R.id.restartGameButton);
        restartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyOnUserWantsToRestartGameListeners();
            }
        });
        hideRequest();
    }

    private void notifyOnUserWantsToRestartGameListeners() {
        for (OnUserWantsToRestartGameListener each : onUserWantsToRestartGameListeners) {
            each.onUserWantsToRestartGame();
        }
    }

    @Override
    public void hideRequest() {
        requested = false;
        restartGameButton.setVisibility(View.INVISIBLE);
    }

    NeedToRestartGameRequestorImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        restoreStateFrom(savedState);
    }

    private void restoreStateFrom(Map<String, Serializable> savedState) {
        Boolean needToRequest = (Boolean) savedState.get(MapKeys.requested);
        if (needToRequest) {
            requestNeedToRestartGame();
        }
    }

    @Override
    public void requestNeedToRestartGame() {
        requested = true;
        restartGameButton.setVisibility(View.VISIBLE);
    }

    void saveStateInto(Map<String,Serializable> outState) {
        outState.put(MapKeys.requested, requested);
    }

    @Override
    public void addOnUserWantsToRestartGameListener(OnUserWantsToRestartGameListener listener) {
        onUserWantsToRestartGameListeners.add(listener);
    }
}
