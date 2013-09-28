package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.ReadOnlyGameBoard;

/**
 * Created by Leonid on 28.09.13.
 */
interface AIMoveCalculator {
    Position positionToMove(ReadOnlyGameBoard gameBoard);
}
