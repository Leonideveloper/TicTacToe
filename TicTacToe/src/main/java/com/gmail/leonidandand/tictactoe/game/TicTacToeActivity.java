package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.game.player.Player;

public class TicTacToeActivity extends Activity {

    private static TicTacToeControllerNewArchitecture controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TicTacToeContext.setGameBoardDimension(3);
        TicTacToeContext.setFirstPlayerType(Player.Type.HUMAN);
        TicTacToeContext.setSecondPlayerType(Player.Type.HUMAN);

        boolean restartingOfActivity = (savedInstanceState != null);
        if (restartingOfActivity) {
            controller.onRestoreState(this);
        } else {
            controller = new TicTacToeControllerNewArchitecture(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        controller.onSaveState(this);
    }
}
