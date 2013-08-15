package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.game.controller.GameControllerAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.GameModelImpl;
import com.gmail.leonidandand.tictactoe.game.model.opponent.Opponent;
import com.gmail.leonidandand.tictactoe.game.model.opponent.StupidAIOpponent;
import com.gmail.leonidandand.tictactoe.game.view.android.GameViewAndroidImpl;

import java.util.HashMap;
import java.util.Map;

public class TicTacToeActivity extends Activity {

    private static final int GAME_BOARD_DIMENSION = 11;
    private static final Opponent OPPONENT = new StupidAIOpponent();

    private static final Map<String, Object> BUNDLE = new HashMap<String, Object>();
    private static final String MODEL_KEY = "TicTacToeActivity.model";

    private GameModel model;
    private GameViewAndroidImpl view;
    private GameControllerAndroidImpl controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean recreationOfActivityDueToChangesInRuntime = (savedInstanceState != null);
        if (recreationOfActivityDueToChangesInRuntime) {
            restoreState();
        } else {
            initGame();
        }
    }

    private void restoreState() {
        model = (GameModel) BUNDLE.get(MODEL_KEY);
        controller = new GameControllerAndroidImpl(model, this);
        view = controller.getView();
        view.restoreState(BUNDLE);
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
        BUNDLE.clear();
        saveState();
    }

    private void saveState() {
        GameViewAndroidImpl view = controller.getView();
        view.saveState(BUNDLE);
        view.unplugModel();
        BUNDLE.put(MODEL_KEY, model);
    }
}
