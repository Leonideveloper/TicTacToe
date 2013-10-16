package com.gmail.landanurm.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.landanurm.tictactoe.R;


public class TicTacToeActivity extends Activity {
    private TicTacToeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_activity);

        controller = new TicTacToeController(this);

        boolean activityLaunched = (savedInstanceState == null);
        if (activityLaunched) {
            controller.startGame();
        } else {
            controller.restoreStateByUsing(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        controller.saveStateInto(outState);
    }
}
