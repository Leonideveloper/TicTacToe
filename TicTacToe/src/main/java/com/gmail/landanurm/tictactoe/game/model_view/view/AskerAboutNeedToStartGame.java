package com.gmail.landanurm.tictactoe.game.model_view.view;

/**
 * Created by Leonid on 21.10.13.
 */
public interface AskerAboutNeedToStartGame {
    void askAboutNeedToStartGame();
    void hide();
    void setOnNeedToStartGameListener(OnNeedToStartGameListener onNeedToStartGameListener);
}
