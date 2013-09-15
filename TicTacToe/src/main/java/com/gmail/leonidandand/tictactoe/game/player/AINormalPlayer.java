package com.gmail.leonidandand.tictactoe.game.player;

import com.gmail.leonidandand.matrix.Position;

/**
 * Created by Leonid on 06.09.13.
 */
class AINormalPlayer extends BasePlayer {

    public AINormalPlayer(Player.Id id) {
        super(id);
    }

    @Override
    public void enableMoves() {
        Position pos = positionToMove_tempDummyAI();
        model.onMove(pos, this);
    }

    private Position positionToMove_tempDummyAI() {
        int dimension = model.getGameBoardDimension();
        for (int row = 0; row < dimension; ++row) {
            for (int column = 0; column < dimension; ++column) {
                Position pos = new Position(row, column);
                if (model.getCellValueAtPos(pos) == null) {
                    return pos;
                }
            }
        }
        throw new RuntimeException("AINormalPlayer: There is not empty cells!");
    }

    @Override
    public void disableMoves() {
        // do nothing
    }
}
