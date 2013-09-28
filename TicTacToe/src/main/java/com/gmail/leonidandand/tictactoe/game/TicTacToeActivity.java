package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModelImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerFactoryImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerTypes;
import com.gmail.leonidandand.tictactoe.game.view.android_impl.TicTacToeViewAndroidImpl;

public class TicTacToeActivity extends Activity {

    private static TicTacToeModel model;
    private static TicTacToeViewAndroidImpl view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContext();

        boolean restartingOfActivity = (savedInstanceState != null);
        if (restartingOfActivity) {
            restoreState(savedInstanceState);
        } else {
            create();
        }
    }

    private void initContext() {
        TicTacToeContext.setGameBoardDimension(13);
        TicTacToeContext.setFirstPlayerType(PlayerTypes.HUMAN);
        TicTacToeContext.setSecondPlayerType(PlayerTypes.AI.NORMAL);
    }

    private void create() {
        model = new TicTacToeModelImpl(
            TicTacToeContext.getGameBoardDimension(),
            new PlayerFactoryImpl(),
            TicTacToeContext.getFirstPlayerType(),
            TicTacToeContext.getSecondPlayerType()
        );

        view = new TicTacToeViewAndroidImpl(model, this);
    }

    private void restoreState(Bundle savedInstanceState) {
        view = new TicTacToeViewAndroidImpl(model, this, view);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    private void saveState(Bundle outState) {
        // Do nothing
    }
}
