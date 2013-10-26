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
class ScoreDisplayImpl implements ScoreDisplay {
    private final TextView firstPlayerScore;
    private final TextView secondPlayerScore;

    ScoreDisplayImpl(Activity activity) {
        firstPlayerScore = (TextView) activity.findViewById(R.id.firstPlayerScore);
        secondPlayerScore = (TextView) activity.findViewById(R.id.secondPlayerScore);
    }

    ScoreDisplayImpl(Activity activity, Map<String, Serializable> savedState) {
        this(activity);
    }

    void saveStateInto(Map<String, Serializable> outState) {
        // do nothing
    }

    @Override
    public void showScore(Score score) {
        firstPlayerScore.setText(String.valueOf(score.getFirstPlayerScore()));
        secondPlayerScore.setText(String.valueOf(score.getSecondPlayerScore()));
    }
}
