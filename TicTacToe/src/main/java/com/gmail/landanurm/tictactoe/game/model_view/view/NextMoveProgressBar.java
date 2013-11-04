package com.gmail.landanurm.tictactoe.game.model_view.view;

import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

public interface NextMoveProgressBar {
    void show(Player.Id playerId);
    void hide();
}
