package com.gmail.landanurm.tictactoe.game.model_view.view;


/**
 * Created by Leonid on 12.09.13.
 */
public interface TicTacToeView {
    void addOnCellClickListener(OnCellClickListener listener);

    public static interface ComponentsProvider {
        GameBoardView getGameBoardView();
        MoveProgressBar getMoveProgressBar();
        ResultDisplay getResultDisplay();
        ScoreDisplay getScoreDisplay();
        StartNewGameRequestor getStartNewGameRequestor();
    }
}
