package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;

/**
 * Created by Leonid on 06.09.13.
 */
class AIPlayer implements Player, Serializable {
    private final AIMoveCalculator aiMoveCalculator;
    private final Id id;
    private final TicTacToeModel model;

    AIPlayer(Id id, TicTacToeModel model, AIMoveCalculator aiMoveCalculator) {
        this.aiMoveCalculator = aiMoveCalculator;
        this.id = id;
        this.model = model;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void enableMoves() {
        Position pos = aiMoveCalculator.calculatePositionToMove();
        model.onMove(pos, this);
    }

    @Override
    public void disableMoves() {
        // do nothing
    }
}
