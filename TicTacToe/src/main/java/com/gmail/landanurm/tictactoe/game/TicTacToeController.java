package com.gmail.landanurm.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModelImpl;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeView;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeViewImpl;
import com.gmail.landanurm.tictactoe.game.players.PlayerTypes;
import com.gmail.landanurm.tictactoe.game.players.PlayersFactoryImpl;
import com.gmail.landanurm.tictactoe.game.android_view_components_provider.ViewComponentsProviderAndroidImpl;

import java.io.Serializable;

class TicTacToeController {

    private static class BundleKeys {
        final static String model = "Controller.model";
        final static String viewComponentsState = "Controller.viewComponentsState";
    }

    private static int gameBoardDimension = 5;
    private static String firstPlayerType = PlayerTypes.HUMAN;
    private static String secondPlayerType = PlayerTypes.AI.NORMAL;

    private final Activity activity;
    private final PlayersFactory playersFactory;

    private TicTacToeModel model;
    private ViewComponentsProviderAndroidImpl viewComponentsProvider;

    @SuppressWarnings("unused")
    private TicTacToeView view;


    public TicTacToeController(Activity activity) {
        this.activity = activity;
        this.playersFactory = new PlayersFactoryImpl(firstPlayerType, secondPlayerType);
    }

    public void startGame() {
        model = createModel();
        viewComponentsProvider = createViewComponentsProvider();
        view = createView();
        model.startGame();
    }

    private TicTacToeModel createModel() {
        return new TicTacToeModelImpl(gameBoardDimension, playersFactory);
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
        if (firstPlayerType.equals(PlayerTypes.HUMAN)) {
            view.addOnCellClickListener((OnCellClickListener) model.getFirstPlayer());
        }
        if (secondPlayerType.equals(PlayerTypes.HUMAN)) {
            view.addOnCellClickListener((OnCellClickListener) model.getSecondPlayer());
        }
    }

    public void saveStateInto(Bundle outState) {
        outState.putSerializable(BundleKeys.model, (Serializable) model);
        outState.putSerializable(BundleKeys.viewComponentsState, viewComponentsProvider.getState());
    }

    public void restoreStateFrom(Bundle savedState) {
        model = getModelRestoredFrom(savedState);
        viewComponentsProvider = createViewComponentsProviderFrom(savedState);
        view = createView();
    }

    private TicTacToeModel getModelRestoredFrom(Bundle savedState) {
        return (TicTacToeModel) savedState.getSerializable(BundleKeys.model);
    }

    private ViewComponentsProviderAndroidImpl createViewComponentsProviderFrom(Bundle savedState) {
        Serializable viewComponentsState = savedState.getSerializable(BundleKeys.viewComponentsState);
        return new ViewComponentsProviderAndroidImpl(activity, viewComponentsState);
    }
}
