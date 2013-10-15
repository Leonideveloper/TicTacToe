package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.ReadOnlyGameBoard;

/**
 * Created by Leonid on 28.09.13.
 */
interface AIMoveCalculator {
    Position calculatePositionToMove(ReadOnlyGameBoard gameBoard);
}
