package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModelImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayersFactoryImpl;
import com.gmail.leonidandand.tictactoe.game.view.android_impl.TicTacToeViewAndroidImpl;

/**
 * Created by Leonid on 06.09.13.
 */
public class TicTacToeController {
    private final TicTacToeModel model;
    private TicTacToeViewAndroidImpl view;

    public TicTacToeController(Activity activity,
                               int gameBoardDimension,
                               String firstPlayerType,
                               String secondPlayerType) {

        model = new TicTacToeModelImpl(gameBoardDimension, new PlayersFactoryImpl(),
                                       firstPlayerType, secondPlayerType);

        view = new TicTacToeViewAndroidImpl(model, activity);
    }

    public void onSaveState(Activity activity) {
        // Do nothing
    }

    public void onRestoreState(Activity activity) {
        view = new TicTacToeViewAndroidImpl(model, activity, view);
    }
}
