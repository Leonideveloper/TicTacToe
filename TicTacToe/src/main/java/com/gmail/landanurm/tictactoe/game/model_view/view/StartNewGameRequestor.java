package com.gmail.landanurm.tictactoe.game.model_view.view;

public interface StartNewGameRequestor {
    void addOnUserWantsToStartNewGameListener(OnUserWantsToStartNewGameListener listener);
    void requestToStartNewGame();
    void hideRequest();
}
