package com.gmail.landanurm.tictactoe.game.model_view.view;

public interface NeedToRestartGameRequestor {
    void addOnUserWantsToRestartGameListener(OnUserWantsToRestartGameListener listener);
    void requestNeedToRestartGame();
    void hideRequest();
}
