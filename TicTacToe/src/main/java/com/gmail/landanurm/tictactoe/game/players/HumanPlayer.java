package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.OnMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.game_board.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;

import java.io.Serializable;

/**
 * Created by Leonid on 06.09.13.
 */
class HumanPlayer implements Player, OnCellClickListener, Serializable {
    private final OnMoveListener onMoveListener;
    private final ReadOnlyGameBoard gameBoard;
    private final Id id;
    private boolean movesEnabled;

    HumanPlayer(Id id, ReadOnlyGameBoard gameBoard, OnMoveListener onMoveListener) {
        this.id = id;
        this.gameBoard = gameBoard;
        this.onMoveListener = onMoveListener;
        this.movesEnabled = false;
    }

    @Override
    public Id getId() {
        return id;
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
            onMoveListener.onMove(pos, this);
        }
    }
}
