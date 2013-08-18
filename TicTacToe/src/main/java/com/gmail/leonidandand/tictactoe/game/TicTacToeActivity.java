package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.game.controller.GameControllerAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.GameModelImpl;
import com.gmail.leonidandand.tictactoe.game.model.opponent.Opponent;
import com.gmail.leonidandand.tictactoe.game.model.opponent.OpponentFactory;
import com.gmail.leonidandand.tictactoe.game.view.android.GameViewAndroidImpl;

import java.util.Map;

public class TicTacToeActivity extends Activity implements CapableSaveRestoreState {

    public static final String DIFFICULTY_LEVEL_KEY = "TicTacToeActivity.DIFFICULTY_LEVEL";

    private static final String MODEL_KEY = "TicTacToeActivity.MVC.model";

    private static final int GAME_BOARD_DIMENSION = 11;

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
        Opponent opponent = OpponentFactory.createByDifficultyLevel(extractDifficultyLevel());
        model.setOpponent(opponent);
        controller = new GameControllerAndroidImpl(model, this);
        view = controller.getView();
    }

    private DifficultyLevel extractDifficultyLevel() {
        String name = getIntent().getStringExtra(DIFFICULTY_LEVEL_KEY);
        return DifficultyLevel.valueOf(name);
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
        bundle.put(MODEL_KEY, model);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.plugModel(model);
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.unplugModel();
    }
}
