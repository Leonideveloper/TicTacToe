package com.gmail.leonidandand.tictactoe.game.view.android_impl;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.view.GameBoardView;
import com.gmail.leonidandand.tictactoe.game.view.MoveProgressBar;
import com.gmail.leonidandand.tictactoe.game.view.ResultDisplay;
import com.gmail.leonidandand.tictactoe.game.view.ScoreDisplay;
import com.gmail.leonidandand.tictactoe.game.view.TicTacToeViewAbstract;
import com.gmail.leonidandand.tictactoe.game.view.android_impl.game_board.GameBoardViewAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.view.android_impl.game_board.GameBoardCreator;

/**
 * Created by Leonid on 18.07.13.
 */
public class TicTacToeViewAndroidImpl extends TicTacToeViewAbstract {
    private final GameBoardViewAndroidImpl gameBoard;
    private final MoveProgressBarAndroidImpl moveProgressBar;
    private final ResultDisplayAndroidToastImpl resultDisplay;
    private final ScoreDisplayAndroidImpl scoreDisplay;

    public TicTacToeViewAndroidImpl(TicTacToeModel model, Activity activity) {
        super(model);
        activity.setContentView(R.layout.tic_tac_toe_activity);

        moveProgressBar = new MoveProgressBarAndroidImpl(activity);
        resultDisplay = new ResultDisplayAndroidToastImpl(activity);
        scoreDisplay = new ScoreDisplayAndroidImpl(activity, model.getScore());

        GameBoardCreator gameBoardCreator = new GameBoardCreator(activity);
        gameBoard = gameBoardCreator.create(model.getGameBoardDimension());
        gameBoard.setOnCellClickListener(this);

        prepareNewGame();
    }

    public TicTacToeViewAndroidImpl(TicTacToeModel model, Activity activity,
                                    TicTacToeViewAndroidImpl toRestore) {
        super(toRestore);
        activity.setContentView(R.layout.tic_tac_toe_activity);

        moveProgressBar = new MoveProgressBarAndroidImpl(activity, toRestore.moveProgressBar);
        resultDisplay = new ResultDisplayAndroidToastImpl(activity, toRestore.resultDisplay);
        scoreDisplay = new ScoreDisplayAndroidImpl(activity, model.getScore());

        GameBoardCreator gameBoardCreator = new GameBoardCreator(activity);
        gameBoard = gameBoardCreator.create(model.getGameBoardDimension(), toRestore.gameBoard);
        gameBoard.setOnCellClickListener(this);
    }

    @Override
    protected MoveProgressBar getMoveProgressBar() {
        return moveProgressBar;
    }

    @Override
    protected GameBoardView getGameBoardView() {
        return gameBoard;
    }

    @Override
    protected ResultDisplay getResultDisplay() {
        return resultDisplay;
    }

    @Override
    protected ScoreDisplay getScoreDisplay() {
        return scoreDisplay;
    }
}
