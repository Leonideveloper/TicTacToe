package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.View;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.view.AskerAboutNeedToStartGame;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnNeedToStartGameListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Leonid on 21.10.13.
 */
class AskerAboutNeedToStartGameAndroidImpl implements AskerAboutNeedToStartGame {

    private static class MapKeys {
        final static String asked = "AskerAboutNeedToStartGame.asked";
    }

    private final View askerLayout;
    private final Collection<OnNeedToStartGameListener> onNeedToStartGameListeners;
    private boolean asked;

    AskerAboutNeedToStartGameAndroidImpl(Activity activity) {
        onNeedToStartGameListeners = new ArrayList<OnNeedToStartGameListener>();
        askerLayout = activity.findViewById(R.id.layoutOfAskerAboutNeedToStartGame);
        hide();
        View startNewGameButton = askerLayout.findViewById(R.id.startNewGameButton);
        startNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyOnNeedToStartGameListeners();
            }
        });
    }

    void saveStateInto(Map<String,Serializable> viewComponentsState) {
        viewComponentsState.put(MapKeys.asked, asked);
    }

    AskerAboutNeedToStartGameAndroidImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
        Boolean needToAsk = (Boolean) savedState.get(MapKeys.asked);
        if (needToAsk) {
            askAboutNeedToStartGame();
        }
    }

    @Override
    public void setOnNeedToStartGameListener(OnNeedToStartGameListener onNeedToStartGameListener) {
        onNeedToStartGameListeners.clear();
        onNeedToStartGameListeners.add(onNeedToStartGameListener);
    }

    private void notifyOnNeedToStartGameListeners() {
        for (OnNeedToStartGameListener each : onNeedToStartGameListeners) {
            each.onNeedToStartGame();
        }
    }

    @Override
    public void askAboutNeedToStartGame() {
        asked = true;
        askerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        asked = false;
        askerLayout.setVisibility(View.INVISIBLE);
    }
}
