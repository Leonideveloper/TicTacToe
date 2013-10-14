package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model_view.view.OnCellClickListener;

import java.io.Serializable;

/**
 * Created by Leonid on 06.09.13.
 */
class HumanPlayer extends BasePlayer implements OnCellClickListener, Serializable {
    private boolean movesEnabled;

    HumanPlayer(Player.Id id, TicTacToeModel model) {
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
        if (movesEnabled && gameBoard.cellIsEmpty(pos)) {
            model.onMove(pos, this);
        }
    }
}
