package com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.leonidandand.tictactoe.game.model_view.view.MoveProgressBar;
import com.gmail.leonidandand.tictactoe.game.model_view.view.ResultDisplay;
import com.gmail.leonidandand.tictactoe.game.model_view.view.ScoreDisplay;
import com.gmail.leonidandand.tictactoe.game.model_view.view.TicTacToeViewComponentsProvider;
import com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl.game_board_view.GameBoardViewAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl.game_board_view.GameBoardViewCreator;

/**
 * Created by Leonid on 28.09.13.
 */
public class TicTacToeViewComponentsProviderAndroidImpl implements TicTacToeViewComponentsProvider {
    private final GameBoardViewAndroidImpl gameBoardView;
    private final MoveProgressBarAndroidImpl moveProgressBar;
    private final ResultDisplayAndroidToastImpl resultDisplay;
    private final ScoreDisplayAndroidImpl scoreDisplay;

    public TicTacToeViewComponentsProviderAndroidImpl(int gameBoardDimension, Activity activity) {
        GameBoardViewCreator gameBoardViewCreator = new GameBoardViewCreator(activity);
        gameBoardView = gameBoardViewCreator.create(gameBoardDimension);
        moveProgressBar = new MoveProgressBarAndroidImpl(activity);
        resultDisplay = new ResultDisplayAndroidToastImpl(activity);
        scoreDisplay = new ScoreDisplayAndroidImpl(activity);
    }

    public TicTacToeViewComponentsProviderAndroidImpl(int gameBoardDimension, Activity activity,
                                        TicTacToeViewComponentsProviderAndroidImpl toRestore) {
        GameBoardViewCreator gameBoardViewCreator = new GameBoardViewCreator(activity);
        gameBoardView = gameBoardViewCreator.create(gameBoardDimension, toRestore.gameBoardView);
        moveProgressBar = new MoveProgressBarAndroidImpl(activity, toRestore.moveProgressBar);
        resultDisplay = new ResultDisplayAndroidToastImpl(activity, toRestore.resultDisplay);
        scoreDisplay = new ScoreDisplayAndroidImpl(activity);
    }

    @Override
    public GameBoardView getGameBoardView() {
        return gameBoardView;
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
    public MoveProgressBar getMoveProgressBar() {
        return moveProgressBar;
    }
}
