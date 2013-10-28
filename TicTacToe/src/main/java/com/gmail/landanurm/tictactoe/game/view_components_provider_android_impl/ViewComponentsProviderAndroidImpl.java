package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;

import com.gmail.landanurm.tictactoe.game.model_view.view.StartNewGameRequestor;
import com.gmail.landanurm.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.landanurm.tictactoe.game.model_view.view.MoveProgressBar;
import com.gmail.landanurm.tictactoe.game.model_view.view.ResultDisplay;
import com.gmail.landanurm.tictactoe.game.model_view.view.ScoreDisplay;
import com.gmail.landanurm.tictactoe.game.model_view.view.TicTacToeView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonid on 28.09.13.
 */
public class ViewComponentsProviderAndroidImpl implements TicTacToeView.ComponentsProvider {
    private final GameBoardViewImpl gameBoardView;
    private final MoveProgressBarImpl moveProgressBar;
    private final ResultDisplayImpl resultDisplay;
    private final ScoreDisplayImpl scoreDisplay;
    private final StartNewGameRequestorImpl startNewGameRequestor;

    public ViewComponentsProviderAndroidImpl(Activity activity, int gameBoardDimension) {
        gameBoardView = new GameBoardViewImpl(activity, gameBoardDimension);
        moveProgressBar = new MoveProgressBarImpl(activity);
        resultDisplay = new ResultDisplayImpl(activity);
        scoreDisplay = new ScoreDisplayImpl(activity);
        startNewGameRequestor = new StartNewGameRequestorImpl(activity);
    }

    public ViewComponentsProviderAndroidImpl(Activity activity, int gameBoardDimension,
                                             Serializable viewComponentsState) {
        Map<String, Serializable> savedState = (Map<String, Serializable>) viewComponentsState;
        gameBoardView = new GameBoardViewImpl(activity, gameBoardDimension, savedState);
        moveProgressBar = new MoveProgressBarImpl(activity, savedState);
        resultDisplay = new ResultDisplayImpl(activity, savedState);
        scoreDisplay = new ScoreDisplayImpl(activity, savedState);
        startNewGameRequestor = new StartNewGameRequestorImpl(activity, savedState);
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

    @Override
    public StartNewGameRequestor getStartNewGameRequestor() {
        return startNewGameRequestor;
    }

    public Serializable getState() {
        HashMap<String, Serializable> viewComponentsState = new HashMap<String, Serializable>();
        gameBoardView.saveStateInto(viewComponentsState);
        moveProgressBar.saveStateInto(viewComponentsState);
        resultDisplay.saveStateInto(viewComponentsState);
        scoreDisplay.saveStateInto(viewComponentsState);
        startNewGameRequestor.saveStateInto(viewComponentsState);
        return viewComponentsState;
    }
}
