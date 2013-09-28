package com.gmail.leonidandand.tictactoe.game.model_view.view;

import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.TicTacToeResult;

/**
 * Created by Leonid on 19.07.13.
 */
public interface ResultDisplay {
    void show(TicTacToeResult.GameState gameState);
    void hide();
}
