package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.GameResultDisplay;
import com.gmail.leonidandand.tictactoe.game.view.GameScoreDisplay;
import com.gmail.leonidandand.tictactoe.game.view.GameViewImpl;
import com.gmail.leonidandand.tictactoe.game.view.OpponentMoveProgressBar;
import com.gmail.leonidandand.tictactoe.game.view.android.game_board.GameBoardAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.view.android.game_board.GameBoardCreator;

import java.util.Map;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameViewAndroidImpl extends GameViewImpl implements CapableSaveRestoreState {

    private static final String GAME_FINISHED_KEY = "GameView.gameFinished";

    private final GameBoardAndroidImpl gameBoard;
    private final GameResultDisplayAndroidToastImpl gameResultDisplay;
    private final GameScoreDisplayAndroidImpl gameScoreDisplay;
    private final OpponentMoveProgressBarAndroidImpl opponentMoveProgressBar;

    public GameViewAndroidImpl(GameController controller, GameModel model, Activity activity) {
        super(controller, model);
        activity.setContentView(R.layout.tic_tac_toe_activity);

        opponentMoveProgressBar = new OpponentMoveProgressBarAndroidImpl(activity);

        gameScoreDisplay = new GameScoreDisplayAndroidImpl(activity);
        gameScoreDisplay.showScore(model.getScore());

        gameResultDisplay = new GameResultDisplayAndroidToastImpl(activity);

        GameBoardCreator gameBoardCreator = new GameBoardCreator(activity);
        int gameBoardDimension = model.getGameBoardDimension();
        gameBoard = gameBoardCreator.createGameBoard(gameBoardDimension);
        gameBoard.setOnCellClickListener(this);
    }

    @Override
    protected OpponentMoveProgressBar getOpponentMoveProgressBar() {
        return opponentMoveProgressBar;
    }

    @Override
    protected GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    protected GameResultDisplay getGameResultDisplay() {
        return gameResultDisplay;
    }

    @Override
    protected GameScoreDisplay getGameScoreDisplay() {
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
