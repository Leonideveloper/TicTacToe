package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.OnMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;

/**
 * Created by Leonid on 06.09.13.
 */
class AIPlayer implements Player, Serializable {
    private final Id id;
    private final OnMoveListener onMoveListener;
    private final AIMoveCalculator aiMoveCalculator;

    AIPlayer(Id id, OnMoveListener onMoveListener, AIMoveCalculator aiMoveCalculator) {
        this.id = id;
        this.onMoveListener = onMoveListener;
        this.aiMoveCalculator = aiMoveCalculator;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void enableMoves() {
        Position pos = aiMoveCalculator.calculatePositionToMove();
        onMoveListener.onMove(pos, this);
    }

    @Override
    public void disableMoves() {
        // do nothing
    }
}
