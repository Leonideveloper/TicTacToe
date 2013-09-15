package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.ResultDisplay;
import com.gmail.leonidandand.tictactoe.game.view.ScoreDisplay;
import com.gmail.leonidandand.tictactoe.game.view.TicTacToeViewImpl;
import com.gmail.leonidandand.tictactoe.game.view.MoveProgressBar;
import com.gmail.leonidandand.tictactoe.game.view.android.game_board.GameBoardAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.view.android.game_board.GameBoardCreator;

import java.util.Map;

/**
 * Created by Leonid on 18.07.13.
 */
public class TicTacToeViewAndroidImpl extends TicTacToeViewImpl implements CapableSaveRestoreState {

    private static final String GAME_FINISHED_KEY = "TicTacToeView.gameFinished";

    private final GameBoardAndroidImpl gameBoard;
    private final ResultDisplayAndroidToastImpl gameResultDisplay;
    private final ScoreDisplayAndroidImpl gameScoreDisplay;
    private final MoveProgressBarAndroidImpl opponentMoveProgressBar;

    public TicTacToeViewAndroidImpl(TicTacToeModel model, Activity activity) {
        super(model);
        activity.setContentView(R.layout.tic_tac_toe_activity);

        opponentMoveProgressBar = new MoveProgressBarAndroidImpl(activity);

        gameScoreDisplay = new ScoreDisplayAndroidImpl(activity);
        gameScoreDisplay.showScore(model.getScore());

        gameResultDisplay = new ResultDisplayAndroidToastImpl(activity);

        GameBoardCreator gameBoardCreator = new GameBoardCreator(activity);
        int gameBoardDimension = model.getGameBoardDimension();
        gameBoard = gameBoardCreator.createGameBoardByDimension(gameBoardDimension);
        gameBoard.setOnCellClickListener(this);

        prepareNewGame();
    }

    @Override
    protected MoveProgressBar getMoveProgressBar() {
        return opponentMoveProgressBar;
    }

    @Override
    protected GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    protected ResultDisplay getGameResultDisplay() {
        return gameResultDisplay;
    }

    @Override
    protected ScoreDisplay getGameScoreDisplay() {
        return gameScoreDisplay;
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(GAME_FINISHED_KEY, gameFinished);
        gameBoard.saveState(bundle);
        gameResultDisplay.saveState(bundle);
        gameScoreDisplay.saveState(bundle);
        opponentMoveProgressBar.saveState(bundle);
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        gameFinished = (Boolean) bundle.get(GAME_FINISHED_KEY);
        gameBoard.restoreState(bundle);
        gameResultDisplay.restoreState(bundle);
        gameScoreDisplay.restoreState(bundle);
        opponentMoveProgressBar.restoreState(bundle);
    }
}
