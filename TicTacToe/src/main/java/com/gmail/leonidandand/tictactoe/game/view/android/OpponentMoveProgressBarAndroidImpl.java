package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.view.View;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.view.OpponentMoveProgressBar;

import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
public class OpponentMoveProgressBarAndroidImpl
        implements OpponentMoveProgressBar, CapableSaveRestoreState {

    private static final String DISPLAYED_KEY = "OpponentMoveProgressBar.DISPLAYED";

    private final View progressBar;
    private boolean displayed;

    OpponentMoveProgressBarAndroidImpl(Activity activity) {
        progressBar = activity.findViewById(R.id.progressBar);
        hide();
    }

    @Override
    public void show() {
        progressBar.setVisibility(View.VISIBLE);
        displayed = true;
    }

    @Override
    public void hide() {
        progressBar.setVisibility(View.INVISIBLE);
        displayed = false;
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(DISPLAYED_KEY, displayed);
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        Boolean needToDisplay = (Boolean) bundle.get(DISPLAYED_KEY);
        if (needToDisplay) {
            show();
        } else {
            hide();
        }
    }
}
