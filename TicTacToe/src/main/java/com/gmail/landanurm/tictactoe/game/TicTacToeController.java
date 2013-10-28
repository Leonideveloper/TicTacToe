package com.gmail.landanurm.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModelImpl;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeView;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeViewImpl;
import com.gmail.landanurm.tictactoe.game.players.PlayerTypes;
import com.gmail.landanurm.tictactoe.game.players.PlayersFactoryImpl;
import com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl.ViewComponentsProviderAndroidImpl;

import java.io.Serializable;

/**
 * Created by Leonid on 15.10.13.
 */
class TicTacToeController {
    private static final Integer gameBoardDimension = 5;
    private static final String firstPlayerType = PlayerTypes.HUMAN;
    private static final String secondPlayerType = PlayerTypes.AI.NORMAL;

    private final Activity activity;
    private TicTacToeModel model;
    private TicTacToeView view;
    private ViewComponentsProviderAndroidImpl viewComponentsProvider;


    TicTacToeController(Activity activity) {
        this.activity = activity;
    }

    void startGame() {
        model = createModel();
        viewComponentsProvider = createViewComponentsProvider();
        view = createView();
        model.startGame();
    }

    private TicTacToeModel createModel() {
        return new TicTacToeModelImpl(gameBoardDimension, new PlayersFactoryImpl(),
                                      firstPlayerType, secondPlayerType);
    }

    private ViewComponentsProviderAndroidImpl createViewComponentsProvider() {
        return new ViewComponentsProviderAndroidImpl(activity, gameBoardDimension);
    }

    private TicTacToeView createView() {
        TicTacToeView view = new TicTacToeViewImpl(viewComponentsProvider, model);
        connectHumanPlayersToView(view);
        return view;
    }

    private void connectHumanPlayersToView(TicTacToeView view) {
        if (firstPlayerType == PlayerTypes.HUMAN) {
            view.addOnCellClickListener((OnCellClickListener) model.getFirstPlayer());
        }
        if (secondPlayerType == PlayerTypes.HUMAN) {
            view.addOnCellClickListener((OnCellClickListener) model.getSecondPlayer());
        }
    }

    private static class BundleKeys {
        final static String model = "Controller.model";
        final static String viewComponentsState = "Controller.viewComponentsState";
    }

    void saveStateInto(Bundle outState) {
        outState.putSerializable(BundleKeys.model, (Serializable) model);
        outState.putSerializable(BundleKeys.viewComponentsState, viewComponentsProvider.getState());
    }

    void restoreStateFrom(Bundle savedState) {
        model = getModelRestoredFrom(savedState);
        viewComponentsProvider = createViewComponentsProviderFrom(savedState);
        view = createView();
    }

    private TicTacToeModel getModelRestoredFrom(Bundle savedState) {
        return (TicTacToeModel) savedState.getSerializable(BundleKeys.model);
    }

    private ViewComponentsProviderAndroidImpl createViewComponentsProviderFrom(Bundle savedState) {
        Serializable viewComponentsState = savedState.getSerializable(BundleKeys.viewComponentsState);
        return new ViewComponentsProviderAndroidImpl(activity, gameBoardDimension, viewComponentsState);
    }
}
