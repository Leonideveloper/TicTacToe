package com.gmail.landanurm.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModelImpl;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeView;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeViewImpl;
import com.gmail.landanurm.tictactoe.game.players.PlayersFactoryImpl;
import com.gmail.landanurm.tictactoe.game.players.PlayerTypes;
import com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl.ViewComponentsProviderAndroidImpl;

import java.io.Serializable;

/**
 * Created by Leonid on 15.10.13.
 */
class TicTacToeController {
    private final Activity activity;
    private final Integer gameBoardDimension;
    private final PlayersFactory playersFactory;
    private final String firstPlayerType;
    private final String secondPlayerType;

    private ViewComponentsProviderAndroidImpl viewComponentsProvider;
    private TicTacToeModel model;
    private TicTacToeView view;


    TicTacToeController(Activity activity) {
        this.activity = activity;
        this.gameBoardDimension = 13;
        this.playersFactory = new PlayersFactoryImpl();
        this.firstPlayerType = PlayerTypes.HUMAN;
        this.secondPlayerType = PlayerTypes.HUMAN;
    }

    void startGame() {
        viewComponentsProvider = createViewComponentsProvider();
        model = createModel();
        view = new TicTacToeViewImpl(viewComponentsProvider, model);
        model.startGame();
    }

    private ViewComponentsProviderAndroidImpl createViewComponentsProvider() {
        return new ViewComponentsProviderAndroidImpl(gameBoardDimension, activity);
    }

    private TicTacToeModel createModel() {
        return new TicTacToeModelImpl(gameBoardDimension,
                playersFactory, firstPlayerType, secondPlayerType);
    }

    private static class BundleKeys {
        final static String model = "Controller.model";
        final static String viewComponentsState = "Controller.viewComponentsState";
    }

    void saveStateInto(Bundle outState) {
        outState.putSerializable(BundleKeys.model, (Serializable) model);
        outState.putSerializable(BundleKeys.viewComponentsState, viewComponentsProvider.getState());
    }

    void restoreStateByUsing(Bundle savedState) {
        Serializable viewComponentsState = savedState.getSerializable(BundleKeys.viewComponentsState);
        viewComponentsProvider = createViewComponentsProvider(viewComponentsState);
        model = (TicTacToeModel) savedState.getSerializable(BundleKeys.model);
        view = new TicTacToeViewImpl(viewComponentsProvider, model);
    }

    private ViewComponentsProviderAndroidImpl createViewComponentsProvider(Serializable state) {
        return new ViewComponentsProviderAndroidImpl(gameBoardDimension, activity, state);
    }
}
