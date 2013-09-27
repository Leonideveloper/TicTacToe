package com.gmail.leonidandand.tictactoe.game.model.game_board;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 27.09.13.
 */
public interface GameBoard extends ReadOnlyGameBoard {
    void clear();
    void set(Position pos, Player.Id id);
}
