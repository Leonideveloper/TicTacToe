package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.Score;
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
    private final View progressBar;
    private final TextView playerScore;
    private final TextView opponentScore;

    public GameViewAndroidImpl(GameController controller, GameModel model, Activity activity) {
        super(controller, model);
        activity.setContentView(R.layout.tic_tac_toe_activity);
        progressBar = activity.findViewById(R.id.progressBar);
        playerScore = (TextView) activity.findViewById(R.id.playerScore);
        opponentScore = (TextView) activity.findViewById(R.id.opponentScore);
        showScore(model.getScore());

        gameResultDisplay = new GameResultDisplayAndroidToastImpl(activity);
        GameBoardCreator gameBoardCreator = new GameBoardCreatorAndroidImpl(activity);
        gameBoard = gameBoardCreator.createGameBoard(model.getDimension());
        gameBoard.setOnCellClickListener(this);
    }

    @Override
    protected void showScore(Score score) {
        playerScore.setText(String.valueOf(score.getPlayerScore()));
        opponentScore.setText(String.valueOf(score.getOpponentScore()));
    }

    @Override
    public void blockMoves() {
        super.blockMoves();
        showProgressBar();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void unblockMoves() {
        super.unblockMoves();
        hideProgressBar();
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
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
