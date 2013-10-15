package com.gmail.landanurm.tictactoe.game.model_view.model.listeners;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface OnNeedToShowMoveListener {
    void onNeedToShowMove(Position pos, Player.Id playerId);
}
