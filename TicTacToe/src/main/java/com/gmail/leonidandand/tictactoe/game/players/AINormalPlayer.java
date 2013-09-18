package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 06.09.13.
 */
class AINormalPlayer extends BasePlayer {

    AINormalPlayer(Player.Id id, TicTacToeModel model) {
        super(id, model);
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
                if (model.getCell(pos) == null) {
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
