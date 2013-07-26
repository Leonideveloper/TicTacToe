package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.GameBoardCreator;
import com.gmail.leonidandand.tictactoe.game.view.GameResultDisplay;
import com.gmail.leonidandand.tictactoe.game.view.GameViewImpl;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameViewAndroidImpl extends GameViewImpl {

    private final GameBoard gameBoard;
    private final GameResultDisplay gameResultDisplay;

    public GameViewAndroidImpl(GameController controller, GameModel model, Activity activity) {
        super(controller, model);
        gameResultDisplay = new GameResultDisplayAndroidToastImpl(activity);
        GameBoardCreator gameBoardCreator = new GameBoardCreatorAndroidImpl(activity);
        gameBoard = gameBoardCreator.createGameBoard(model.getDimension());
        gameBoard.setOnCellClickListener(super.getOnCellClickListener());
    }

    @Override
    protected GameBoard gameBoard() {
        return gameBoard;
    }

    @Override
    protected GameResultDisplay gameResultDisplay() {
        return gameResultDisplay;
    }
}
