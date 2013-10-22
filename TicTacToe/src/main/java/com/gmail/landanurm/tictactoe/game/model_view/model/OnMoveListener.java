package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 22.10.13.
 */
public interface OnMoveListener {
    void onMove(com.gmail.landanurm.matrix.Position movePos, Player player);
}
