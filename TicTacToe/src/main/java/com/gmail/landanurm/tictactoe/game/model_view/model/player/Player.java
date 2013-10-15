package com.gmail.landanurm.tictactoe.game.model_view.model.player;


/**
 * Created by Leonid on 06.09.13.
 */
public interface Player {
    public static enum Id {
        PLAYER_1, PLAYER_2
    }

    Id getId();
    void enableMoves();
    void disableMoves();
}
