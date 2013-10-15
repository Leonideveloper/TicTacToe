package com.gmail.leonidandand.tictactoe.game.model_view.view;


/**
 * Created by Leonid on 12.09.13.
 */
public interface TicTacToeView {
    void addOnCellClickListener(OnCellClickListener listener);

    public static interface ComponentsProvider {
        GameBoardView getGameBoardView();
        ResultDisplay getResultDisplay();
        ScoreDisplay getScoreDisplay();
        MoveProgressBar getMoveProgressBar();
    }
}
