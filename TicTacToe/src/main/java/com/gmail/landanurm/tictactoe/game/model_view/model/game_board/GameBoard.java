package com.gmail.landanurm.tictactoe.game.model_view.model.game_board;

import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 27.09.13.
 */
public interface GameBoard extends ReadOnlyGameBoard {
    void clear();
    void set(com.gmail.landanurm.matrix.Position pos, Player.Id id);
}
