package com.gmail.landanurm.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModelImpl;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayerFactory;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeView;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeViewImpl;
import com.gmail.landanurm.tictactoe.game.players.PlayerFactoryImpl;
import com.gmail.landanurm.tictactoe.game.players.PlayerTypes;
import com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl.ViewComponentsProviderAndroidImpl;

import java.io.Serializable;

/**
 * Created by Leonid on 15.10.13.
 */
class TicTacToeController {
    private final Activity activity;
    private final Integer gameBoardDimension;
    private final PlayerFactory playerFactory;
    private final String firstPlayerType;
    private final String secondPlayerType;

    private TicTacToeModel model;
    private TicTacToeView view;
    private ViewComponentsProviderAndroidImpl viewComponentsProvider;


    TicTacToeController(Activity activity) {
        this.activity = activity;
        this.gameBoardDimension = 13;
        this.playerFactory = new PlayerFactoryImpl();
        this.firstPlayerType = PlayerTypes.HUMAN;
        this.secondPlayerType = PlayerTypes.AI.NORMAL;
    }


    void startGame() {
        model = new TicTacToeModelImpl(gameBoardDimension,
                                       playerFactory,
                                       firstPlayerType,
                                       secondPlayerType);

        viewComponentsProvider = new ViewComponentsProviderAndroidImpl(gameBoardDimension, activity);

        view = new TicTacToeViewImpl(viewComponentsProvider, model);

        model.startGame();
    }


    private static class BundleKeys {
        final static String model = "Controller.model";
        final static String viewComponentsState = "Controller.viewComponentsState";
    }

    void saveState(Bundle outState) {
        outState.putSerializable(BundleKeys.model, (Serializable) model);
        outState.putSerializable(BundleKeys.viewComponentsState, viewComponentsProvider.getState());
    }

    void restoreState(Bundle savedState) {
        model = (TicTacToeModel) savedState.getSerializable(BundleKeys.model);

        viewComponentsProvider = new ViewComponentsProviderAndroidImpl(
                                        gameBoardDimension,
                                        activity,
                                        savedState.getSerializable(BundleKeys.viewComponentsState)
                                 );

        view = new TicTacToeViewImpl(viewComponentsProvider, model);
    }
}
