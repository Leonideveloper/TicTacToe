package com.gmail.leonidandand.tictactoe.game.model;


import com.gmail.leonidandand.tictactoe.game.model.game_judge.TicTacToeResult;

/**
 * Created by Leonid on 18.07.13.
 */
public interface OnGameFinishedListener {
    void onGameFinished(TicTacToeResult result);
}
