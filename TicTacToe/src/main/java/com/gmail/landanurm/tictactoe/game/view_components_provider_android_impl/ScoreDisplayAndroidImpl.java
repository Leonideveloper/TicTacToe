package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.widget.TextView;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.Score;
import com.gmail.landanurm.tictactoe.game.model_view.view.ScoreDisplay;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
class ScoreDisplayAndroidImpl implements ScoreDisplay {
    private final TextView scoreOfPlayer1;
    private final TextView scoreOfPlayer2;

    ScoreDisplayAndroidImpl(Activity activity) {
        scoreOfPlayer1 = (TextView) activity.findViewById(R.id.scoreOfPlayer1);
        scoreOfPlayer2 = (TextView) activity.findViewById(R.id.scoreOfPlayer2);
    }

    void saveStateInto(Map<String,Serializable> outState) {
        // do nothing
    }

    ScoreDisplayAndroidImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
    }

    @Override
    public void showScore(Score score) {
        scoreOfPlayer1.setText(String.valueOf(score.getScoreOfPlayer1()));
        scoreOfPlayer2.setText(String.valueOf(score.getScoreOfPlayer2()));
    }
}
