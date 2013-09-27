package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.game.players.PlayerTypes;

public class TicTacToeActivity extends Activity {

    private static TicTacToeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContext();

        boolean restartingOfActivity = (savedInstanceState != null);
        if (restartingOfActivity) {
            controller.onRestoreState(this, savedInstanceState);
        } else {
            controller = createController();
        }
    }

    private void initContext() {
        TicTacToeContext.setGameBoardDimension(13);
        TicTacToeContext.setFirstPlayerType(PlayerTypes.HUMAN);
        TicTacToeContext.setSecondPlayerType(PlayerTypes.AI.NORMAL);
    }

    private TicTacToeController createController() {
        return new TicTacToeController(
                this,
                TicTacToeContext.getGameBoardDimension(),
                TicTacToeContext.getFirstPlayerType(),
                TicTacToeContext.getSecondPlayerType()
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        controller.onSaveState(this, outState);
    }
}
