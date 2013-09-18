package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;

/**
 * Created by Leonid on 06.09.13.
 */
class HumanPlayer extends BasePlayer implements OnCellClickListener {
    private boolean movesEnabled;

    HumanPlayer(Id id, TicTacToeModel model) {
        super(id, model);
        movesEnabled = false;
    }

    @Override
    public void enableMoves() {
        movesEnabled = true;
    }

    @Override
    public void disableMoves() {
        movesEnabled = false;
    }

    @Override
    public void onCellClick(Position pos) {
        if (movesEnabled && model.cellIsEmpty(pos)) {
            model.onMove(pos, this);
        }
    }
}
