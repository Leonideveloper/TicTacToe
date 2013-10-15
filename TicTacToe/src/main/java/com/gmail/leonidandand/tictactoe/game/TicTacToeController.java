package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModelImpl;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.PlayerFactory;
import com.gmail.leonidandand.tictactoe.game.model_view.view.TicTacToeView;
import com.gmail.leonidandand.tictactoe.game.model_view.view.TicTacToeViewImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerFactoryImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerTypes;
import com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl.TicTacToeViewComponentsProviderAndroidImpl;

import java.io.Serializable;

/**
 * Created by Leonid on 15.10.13.
 */
class TicTacToeController {
    private final Activity activity;
    private final int gameBoardDimension;
    private final PlayerFactory playerFactory;
    private final String firstPlayerType;
    private final String secondPlayerType;

    private static TicTacToeViewComponentsProviderAndroidImpl viewComponentsProvider;
    private TicTacToeView view;
    private TicTacToeModel model;
    

    TicTacToeController(Activity activity) {
        this.activity = activity;
        this.gameBoardDimension = 13;
        this.playerFactory = new PlayerFactoryImpl();
        this.firstPlayerType = PlayerTypes.HUMAN;
        this.secondPlayerType = PlayerTypes.AI.NORMAL;
    }

    void startGame() {
        model = new TicTacToeModelImpl(gameBoardDimension, playerFactory, firstPlayerType, secondPlayerType);
        viewComponentsProvider = new TicTacToeViewComponentsProviderAndroidImpl(gameBoardDimension, activity);
        view = new TicTacToeViewImpl(viewComponentsProvider, model);

        model.startGame();
    }

    void saveState(Bundle outState) {
        outState.putSerializable("model", (Serializable) model);
    }

    void restoreState(Bundle savedInstanceState) {
        model = (TicTacToeModel) savedInstanceState.getSerializable("model");
        viewComponentsProvider = new TicTacToeViewComponentsProviderAndroidImpl(
                                gameBoardDimension, activity, viewComponentsProvider);
        view = new TicTacToeViewImpl(viewComponentsProvider, model);
    }
}
