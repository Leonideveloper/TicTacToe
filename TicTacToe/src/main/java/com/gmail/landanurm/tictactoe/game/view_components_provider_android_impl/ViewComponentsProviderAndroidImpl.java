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

    public ViewComponentsProviderAndroidImpl(int gameBoardDimension, Activity activity,
                                             Serializable savedState) {
        Map<String, Serializable> viewComponentsState = (Map<String, Serializable>) savedState;
        GameBoardViewCreator gameBoardViewCreator = new GameBoardViewCreator(activity);
        gameBoardView = gameBoardViewCreator.create(gameBoardDimension, viewComponentsState);
        moveProgressBar = new MoveProgressBarAndroidImpl(activity, viewComponentsState);
        resultDisplay = new ResultDisplayAndroidImpl(activity, viewComponentsState);
        scoreDisplay = new ScoreDisplayAndroidImpl(activity, viewComponentsState);
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
        moveProgressBar.saveStateInto(viewComponentsState);
        resultDisplay.saveStateInto(viewComponentsState);
        scoreDisplay.saveStateInto(viewComponentsState);
        return viewComponentsState;
    }
}
