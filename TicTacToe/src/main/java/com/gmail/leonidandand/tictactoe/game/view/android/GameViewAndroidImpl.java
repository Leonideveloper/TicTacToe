package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.view.View;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.GameBoardCreator;
import com.gmail.leonidandand.tictactoe.game.view.GameResultDisplay;
import com.gmail.leonidandand.tictactoe.game.view.GameScoreDisplay;
import com.gmail.leonidandand.tictactoe.game.view.GameViewImpl;
import com.gmail.leonidandand.tictactoe.game.view.OpponentMoveProgressBar;
import com.gmail.leonidandand.tictactoe.game.view.android.game_board.GameBoardCreatorAndroidImpl;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameViewAndroidImpl extends GameViewImpl {

    private final GameBoard gameBoard;
    private final GameResultDisplay gameResultDisplay;
    private final GameScoreDisplay gameScoreDisplay;
    private final OpponentMoveProgressBarAndroidImpl opponentMoveProgressBar;

    public GameViewAndroidImpl(GameController controller, GameModel model, Activity activity) {
        super(controller, model);

        activity.setContentView(R.layout.tic_tac_toe_activity);

        opponentMoveProgressBar = new OpponentMoveProgressBarAndroidImpl(activity);
        gameScoreDisplay = new GameScoreDisplayAndroidImpl(activity);
        gameScoreDisplay.showScore(model.getScore());
        gameResultDisplay = new GameResultDisplayAndroidToastImpl(activity);

        GameBoardCreator gameBoardCreator = new GameBoardCreatorAndroidImpl(activity);
        gameBoard = gameBoardCreator.createGameBoard(model.getDimension());
        gameBoard.setOnCellClickListener(this);
    }

    @Override
    protected OpponentMoveProgressBar opponentMoveProgressBar() {
        return opponentMoveProgressBar;
    }

    @Override
    protected GameBoard gameBoard() {
        return gameBoard;
    }

    @Override
    protected GameResultDisplay gameResultDisplay() {
        return gameResultDisplay;
    }

    @Override
    protected GameScoreDisplay gameScoreDisplay() {
        return gameScoreDisplay;
    }
}
