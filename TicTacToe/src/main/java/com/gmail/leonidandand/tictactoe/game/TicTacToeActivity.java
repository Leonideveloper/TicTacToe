package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.game.controller.GameControllerAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.GameModelImpl;
import com.gmail.leonidandand.tictactoe.game.model.opponent.Opponent;
import com.gmail.leonidandand.tictactoe.game.model.opponent.StupidAIOpponent;
import com.gmail.leonidandand.tictactoe.game.view.android.GameViewAndroidImpl;

import java.util.Map;

public class TicTacToeActivity extends Activity implements CapableSaveRestoreState {

    private static final String MODEL_KEY = "TicTacToeActivity.MVC.model";

    private static final int GAME_BOARD_DIMENSION = 11;
    private static final Opponent OPPONENT = new StupidAIOpponent();

    private GameModel model;
    private GameViewAndroidImpl view;
    private GameControllerAndroidImpl controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean restartingOfActivity = (savedInstanceState != null);
        if (restartingOfActivity) {
            restoreState(BundleProvider.getBundleToRestoreState());
        } else {
            initGame();
        }
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        model = (GameModel) bundle.get(MODEL_KEY);
        controller = new GameControllerAndroidImpl(model, this);
        view = controller.getView();
        view.restoreState(bundle);
    }

    private void initGame() {
        model = new GameModelImpl(GAME_BOARD_DIMENSION);
        model.setOpponent(OPPONENT);
        controller = new GameControllerAndroidImpl(model, this);
        view = controller.getView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(BundleProvider.getBundleToSaveState());
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        GameViewAndroidImpl view = controller.getView();
        view.saveState(bundle);
        view.unplugModel();
        bundle.put(MODEL_KEY, model);
    }
}
