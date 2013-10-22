package com.gmail.landanurm.tictactoe.game.model_view.model.game_board;

import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 27.09.13.
 */
public interface ReadOnlyGameBoard {
    boolean cellIsEmpty(com.gmail.landanurm.matrix.Position pos);
    boolean containsEmptyCell();
    int getDimension();
    Player.Id get(com.gmail.landanurm.matrix.Position pos);
}
