package com.gmail.landanurm.tictactoe.game.android_view_components_provider;

import android.app.Activity;

import com.gmail.landanurm.tictactoe.game.model_view.view.NeedToRestartGameRequestor;
import com.gmail.landanurm.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.landanurm.tictactoe.game.model_view.view.NextMoveProgressBar;
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
    private final NeedToRestartGameRequestorImpl needToRestartGameRequestor;
    private final NextMoveProgressBarImpl nextMoveProgressBar;
    private final ResultDisplayImpl resultDisplay;
    private final ScoreDisplayImpl scoreDisplay;


    public ViewComponentsProviderAndroidImpl(Activity activity, int gameBoardDimension) {
        gameBoardView = new GameBoardViewImpl(activity, gameBoardDimension);
        needToRestartGameRequestor = new NeedToRestartGameRequestorImpl(activity);
        nextMoveProgressBar = new NextMoveProgressBarImpl(activity);
        resultDisplay = new ResultDisplayImpl(activity);
        scoreDisplay = new ScoreDisplayImpl(activity);
    }

    public ViewComponentsProviderAndroidImpl(Activity activity, Serializable viewComponentsState) {
        Map<String, Serializable> savedState = (Map<String, Serializable>) viewComponentsState;
        gameBoardView = new GameBoardViewImpl(activity, savedState);
        needToRestartGameRequestor = new NeedToRestartGameRequestorImpl(activity, savedState);
        nextMoveProgressBar = new NextMoveProgressBarImpl(activity, savedState);
        resultDisplay = new ResultDisplayImpl(activity, savedState);
        scoreDisplay = new ScoreDisplayImpl(activity, savedState);
    }

    @Override
    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }

    @Override
    public NeedToRestartGameRequestor getNeedToRestartGameRequestor() {
        return needToRestartGameRequestor;
    }

    @Override
    public NextMoveProgressBar getNextMoveProgressBar() {
        return nextMoveProgressBar;
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
        needToRestartGameRequestor.saveStateInto(viewComponentsState);
        nextMoveProgressBar.saveStateInto(viewComponentsState);
        resultDisplay.saveStateInto(viewComponentsState);
        scoreDisplay.saveStateInto(viewComponentsState);
        return viewComponentsState;
    }
}
