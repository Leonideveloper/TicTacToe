package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 27.09.13.
 */
interface GameBoard extends ReadOnlyGameBoard {
    void clear();
    void set(Position pos, Player.Id id);
}
