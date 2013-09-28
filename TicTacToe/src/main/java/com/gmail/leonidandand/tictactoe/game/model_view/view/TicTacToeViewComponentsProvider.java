package com.gmail.leonidandand.tictactoe.game.model_view.view;

/**
 * Created by Leonid on 28.09.13.
 */
public interface TicTacToeViewComponentsProvider {
    GameBoardView getGameBoardView();
    ResultDisplay getResultDisplay();
    ScoreDisplay getScoreDisplay();
    MoveProgressBar getMoveProgressBar();
}
