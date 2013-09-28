package com.gmail.leonidandand.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 27.09.13.
 */
public interface ReadOnlyGameBoard {
    boolean cellIsEmpty(Position pos);
    boolean containsEmptyCell();
    int getDimension();
    Player.Id get(Position pos);
}
