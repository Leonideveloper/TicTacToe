package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.widget.TextView;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.model.Score;
import com.gmail.leonidandand.tictactoe.game.view.GameScoreDisplay;

import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
public class GameScoreDisplayAndroidImpl implements GameScoreDisplay, CapableSaveRestoreState {

    private static final String PLAYER_SCORE_KEY = "GameScoreDisplay.PLAYER_SCORE";
    private static final String OPPONENT_SCORE_KEY = "GameScoreDisplay.OPPONENT_SCORE";

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

    @Override
    public void saveState(Map<String, Object> bundle) {
        CharSequence playerScoreText = playerScore.getText();
        CharSequence opponentScoreText = opponentScore.getText();
        bundle.put(PLAYER_SCORE_KEY, playerScoreText);
        bundle.put(OPPONENT_SCORE_KEY, opponentScoreText);
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        CharSequence playerScoreText = (CharSequence) bundle.get(PLAYER_SCORE_KEY);
        CharSequence opponentScoreText = (CharSequence) bundle.get(OPPONENT_SCORE_KEY);
        playerScore.setText(playerScoreText);
        opponentScore.setText(opponentScoreText);
    }
}
