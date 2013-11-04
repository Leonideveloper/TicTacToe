package com.gmail.landanurm.tictactoe.game.model_view.view;

import com.gmail.landanurm.tictactoe.game.model_view.model.judge.GameState;

public interface ResultDisplay {
    void show(GameState gameState);
    void hide();
}
