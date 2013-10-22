package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.tictactoe.game.model_view.model.game_board.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.OnMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;

import java.io.Serializable;

/**
 * Created by Leonid on 06.09.13.
 */
class HumanPlayer implements Player, OnCellClickListener, Serializable {
    private final OnMoveListener onMoveListener;
    private final ReadOnlyGameBoard gameBoard;
    private final Position position;
    private boolean movesEnabled;

    HumanPlayer(Position position, ReadOnlyGameBoard gameBoard, OnMoveListener onMoveListener) {
        this.position = position;
        this.gameBoard = gameBoard;
        this.onMoveListener = onMoveListener;
        this.movesEnabled = false;
    }

    @Override
    public Position getPosition() {
        return position;
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
    public void onCellClick(com.gmail.landanurm.matrix.Position pos) {
        if (movesEnabled && gameBoard.cellIsEmpty(pos)) {
            onMoveListener.onMove(pos, this);
        }
    }
}
