package com.gmail.landanurm.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.landanurm.tictactoe.CurrentThemeProvider;
import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.game_theme.GameTheme;


public class TicTacToeGameActivity extends Activity {

    private TicTacToeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_activity);
        installGameTheme();

        controller = new TicTacToeController(this);

        boolean activityLaunched = (savedInstanceState == null);
        if (activityLaunched) {
            controller.startGame();
        } else {
            controller.restoreStateFrom(savedInstanceState);
        }
    }

    private void installGameTheme() {
        GameThemeInstaller gameThemeInstaller = new GameThemeInstaller(this);
        GameTheme currentGameTheme = CurrentThemeProvider.getCurrentTheme().getGameTheme();
        gameThemeInstaller.install(currentGameTheme);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        controller.saveStateInto(outState);
    }
}
