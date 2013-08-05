package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.view.View;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.view.OpponentMoveProgressBar;

/**
 * Created by Leonid on 04.08.13.
 */
public class OpponentMoveProgressBarAndroidImpl implements OpponentMoveProgressBar {

    private final View progressBar;

    OpponentMoveProgressBarAndroidImpl(Activity activity) {
        progressBar = activity.findViewById(R.id.progressBar);
    }

    @Override
    public void show() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
