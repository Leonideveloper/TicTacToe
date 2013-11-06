package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

public interface ReadOnlyGameBoard {
    boolean cellIsEmpty(Position pos);
    boolean containsEmptyCell();
    int getDimension();
    Player.Id get(Position pos);
}
