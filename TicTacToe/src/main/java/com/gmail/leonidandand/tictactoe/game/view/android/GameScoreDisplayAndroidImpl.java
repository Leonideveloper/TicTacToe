package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.widget.TextView;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model.Score;
import com.gmail.leonidandand.tictactoe.game.view.GameScoreDisplay;

/**
 * Created by Leonid on 04.08.13.
 */
public class GameScoreDisplayAndroidImpl implements GameScoreDisplay {
    private final TextView playerScore;
    private final TextView opponentScore;

    public GameScoreDisplayAndroidImpl(Activity activity) {
        playerScore = (TextView) activity.findViewById(R.id.playerScore);
        opponentScore = (TextView) activity.findViewById(R.id.opponentScore);
    }

    @Override
    public void showScore(Score score) {
        playerScore.setText(String.valueOf(score.getPlayerScore()));
        opponentScore.setText(String.valueOf(score.getOpponentScore()));
    }
}
