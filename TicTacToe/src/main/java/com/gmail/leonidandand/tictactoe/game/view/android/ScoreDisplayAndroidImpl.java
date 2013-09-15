package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.widget.TextView;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.model.Score;
import com.gmail.leonidandand.tictactoe.game.view.ScoreDisplay;

import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
public class ScoreDisplayAndroidImpl implements ScoreDisplay, CapableSaveRestoreState {

    private static final String PLAYER_1_SCORE_KEY = "ScoreDisplay.PLAYER_1_SCORE";
    private static final String PLAYER_2_SCORE_KEY = "ScoreDisplay.PLAYER_2_SCORE";

    private final TextView scoreOfPlayer_1;
    private final TextView scoreOfPlayer_2;

    ScoreDisplayAndroidImpl(Activity activity) {
        scoreOfPlayer_1 = (TextView) activity.findViewById(R.id.scoreOfPlayer1);
        scoreOfPlayer_2 = (TextView) activity.findViewById(R.id.scoreOfPlayer2);
    }

    @Override
    public void showScore(Score score) {
        scoreOfPlayer_1.setText(String.valueOf(score.getScoreOfPlayer1()));
        scoreOfPlayer_2.setText(String.valueOf(score.getScoreOfPlayer2()));
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(PLAYER_1_SCORE_KEY, scoreOfPlayer_1.getText());
        bundle.put(PLAYER_2_SCORE_KEY, scoreOfPlayer_2.getText());
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        scoreOfPlayer_1.setText((CharSequence) bundle.get(PLAYER_1_SCORE_KEY));
        scoreOfPlayer_2.setText((CharSequence) bundle.get(PLAYER_2_SCORE_KEY));
    }
}
