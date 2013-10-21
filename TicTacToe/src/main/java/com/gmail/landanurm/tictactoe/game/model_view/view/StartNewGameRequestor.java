package com.gmail.landanurm.tictactoe.game.model_view.view;

/**
 * Created by Leonid on 21.10.13.
 */
public interface StartNewGameRequestor {
    void setOnNeedToStartNewGameListener(OnNeedToStartNewGameListener onNeedToStartNewGameListener);
    void requestToStartNewGame();
    void hide();
}