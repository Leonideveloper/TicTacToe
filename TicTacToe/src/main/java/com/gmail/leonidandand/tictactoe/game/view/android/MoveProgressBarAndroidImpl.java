package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.view.View;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.player.Player;
import com.gmail.leonidandand.tictactoe.game.view.MoveProgressBar;

import java.util.Map;

/**
 * Created by Leonid on 04.08.13.
 */
public class MoveProgressBarAndroidImpl implements MoveProgressBar, CapableSaveRestoreState {

    private static final String FIRST_PLAYER_VISIBILITY_KEY = "MoveProgressBar.FIRST_PLAYER";
    private static final String SECOND_PLAYER_VISIBILITY_KEY = "MoveProgressBar.SECOND_PLAYER";

    private final View firstPlayerProgressBar;
    private final View secondPlayerProgressBar;

    MoveProgressBarAndroidImpl(Activity activity) {
        firstPlayerProgressBar = activity.findViewById(R.id.firstPlayerProgressBar);
        secondPlayerProgressBar = activity.findViewById(R.id.secondPlayerProgressBar);
        hide();
    }

    @Override
    public void show(Player.Id movePlayerId) {
        hide();
        progressBarByPlayerId(movePlayerId).setVisibility(View.VISIBLE);
    }

    private View progressBarByPlayerId(Player.Id movePlayerId) {
        return movePlayerId == Player.Id.PLAYER_1
                ? firstPlayerProgressBar
                : secondPlayerProgressBar;
    }

    @Override
    public void hide() {
        firstPlayerProgressBar.setVisibility(View.INVISIBLE);
        secondPlayerProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(FIRST_PLAYER_VISIBILITY_KEY, firstPlayerProgressBar.getVisibility());
        bundle.put(SECOND_PLAYER_VISIBILITY_KEY, secondPlayerProgressBar.getVisibility());
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        Integer firstPlayerVisibility = (Integer) bundle.get(FIRST_PLAYER_VISIBILITY_KEY);
        firstPlayerProgressBar.setVisibility(firstPlayerVisibility);
        Integer secondPlayerVisibility = (Integer) bundle.get(SECOND_PLAYER_VISIBILITY_KEY);
        secondPlayerProgressBar.setVisibility(secondPlayerVisibility);
    }
}
