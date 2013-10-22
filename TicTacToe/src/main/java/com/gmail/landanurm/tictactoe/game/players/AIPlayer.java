package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.tictactoe.game.model_view.model.OnMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;

/**
 * Created by Leonid on 06.09.13.
 */
class AIPlayer implements Player, Serializable {
    private final Position position;
    private final OnMoveListener onMoveListener;
    private final AIMoveCalculator aiMoveCalculator;

    AIPlayer(Position position, OnMoveListener onMoveListener, AIMoveCalculator aiMoveCalculator) {
        this.position = position;
        this.onMoveListener = onMoveListener;
        this.aiMoveCalculator = aiMoveCalculator;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void enableMoves() {
        com.gmail.landanurm.matrix.Position pos = aiMoveCalculator.calculatePositionToMove();
        onMoveListener.onMove(pos, this);
    }

    @Override
    public void disableMoves() {
        // do nothing
    }
}
