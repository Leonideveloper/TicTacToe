package com.gmail.landanurm.tictactoe.game.model_view.model.game_board;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 27.09.13.
 */
public interface GameBoard extends ReadOnlyGameBoard {
    void clear();
    void set(Position pos, Player.Id id);
}
