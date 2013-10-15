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

    private TicTacToeModel model;
    private TicTacToeView view;
    private TicTacToeViewComponentsProviderAndroidImpl viewComponentsProvider;


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


    private static class BundleKeys {
        final static String model = "Controller.model";
        final static String viewComponentsState = "Controller.viewComponentsState";
    }

    void saveState(Bundle outState) {
        outState.putSerializable(BundleKeys.model, (Serializable) model);

        Serializable viewComponentsState = viewComponentsProvider.getState();
        outState.putSerializable(BundleKeys.viewComponentsState, viewComponentsState);
    }

    void restoreState(Bundle savedState) {
        model = (TicTacToeModel) savedState.getSerializable(BundleKeys.model);

        Serializable viewComponentsState = savedState.getSerializable(BundleKeys.viewComponentsState);
        viewComponentsProvider = new TicTacToeViewComponentsProviderAndroidImpl(
                                            gameBoardDimension, activity, viewComponentsState);

        view = new TicTacToeViewImpl(viewComponentsProvider, model);
    }
}
