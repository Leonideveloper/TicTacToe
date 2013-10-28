package com.gmail.landanurm.tictactoe.game.model_view.view;

/**
 * Created by Leonid on 21.10.13.
 */
public interface StartNewGameRequestor {
    void addOnUserWantsToStartNewGameListener(OnUserWantsToStartNewGameListener listener);
    void requestToStartNewGame();
    void hideRequest();
}
