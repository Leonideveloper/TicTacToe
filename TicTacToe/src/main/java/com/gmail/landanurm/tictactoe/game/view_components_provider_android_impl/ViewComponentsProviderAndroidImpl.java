package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;

import com.gmail.landanurm.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.landanurm.tictactoe.game.model_view.view.MoveProgressBar;
import com.gmail.landanurm.tictactoe.game.model_view.view.ResultDisplay;
import com.gmail.landanurm.tictactoe.game.model_view.view.ScoreDisplay;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeView;
import com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl.game_board_view.GameBoardViewAndroidImpl;
import com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl.game_board_view.GameBoardViewCreator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonid on 28.09.13.
 */
public class ViewComponentsProviderAndroidImpl implements TicTacToeView.ComponentsProvider {
    private final GameBoardViewAndroidImpl gameBoardView;
    private final MoveProgressBarAndroidImpl moveProgressBar;
    private final ResultDisplayAndroidImpl resultDisplay;
    private final ScoreDisplayAndroidImpl scoreDisplay;

    public ViewComponentsProviderAndroidImpl(int gameBoardDimension, Activity activity) {
        GameBoardViewCreator gameBoardViewCreator = new GameBoardViewCreator(activity);
        gameBoardView = gameBoardViewCreator.create(gameBoardDimension);
        moveProgressBar = new MoveProgressBarAndroidImpl(activity);
        resultDisplay = new ResultDisplayAndroidImpl(activity);
        scoreDisplay = new ScoreDisplayAndroidImpl(activity);
    }

    public ViewComponentsProviderAndroidImpl(int gameBoardDimension,
                                             Activity activity,
                                             Serializable viewComponentsState) {

        Map<String, Serializable> savedState = (Map<String, Serializable>) viewComponentsState;
        GameBoardViewCreator gameBoardViewCreator = new GameBoardViewCreator(activity);
        gameBoardView = gameBoardViewCreator.create(gameBoardDimension, savedState);
        moveProgressBar = new MoveProgressBarAndroidImpl(activity, savedState);
        resultDisplay = new ResultDisplayAndroidImpl(activity, savedState);
        scoreDisplay = new ScoreDisplayAndroidImpl(activity, savedState);
    }

    @Override
    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }

    @Override
    public MoveProgressBar getMoveProgressBar() {
        return moveProgressBar;
    }

    @Override
    public ResultDisplay getResultDisplay() {
        return resultDisplay;
    }

    @Override
    public ScoreDisplay getScoreDisplay() {
        return scoreDisplay;
    }

    public Serializable getState() {
        HashMap<String, Serializable> viewComponentsState = new HashMap<String, Serializable>();
        gameBoardView.saveStateInto(viewComponentsState);
        resultDisplay.saveStateInto(viewComponentsState);
        scoreDisplay.saveStateInto(viewComponentsState);
        moveProgressBar.saveStateInto(viewComponentsState);
        return viewComponentsState;
    }
}
