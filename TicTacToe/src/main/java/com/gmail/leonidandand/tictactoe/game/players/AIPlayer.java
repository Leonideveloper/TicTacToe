package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 06.09.13.
 */
class AIPlayer extends BasePlayer {
    private final AIMoveCalculator aiMoveCalculator;

    AIPlayer(Player.Id id, TicTacToeModel model, AIMoveCalculator aiMoveCalculator) {
        super(id, model);
        this.aiMoveCalculator = aiMoveCalculator;
    }

    @Override
    public void enableMoves() {
        Position pos = aiMoveCalculator.calculatePositionToMove(gameBoard);
        model.onMove(pos, this);
    }

    @Override
    public void disableMoves() {
        // do nothing
    }
}
