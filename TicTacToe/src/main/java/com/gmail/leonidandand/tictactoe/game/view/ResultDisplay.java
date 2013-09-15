package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameState;

/**
 * Created by Leonid on 19.07.13.
 */
public interface ResultDisplay {
    void show(GameState gameState);
    void hide();
}
