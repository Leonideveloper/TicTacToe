package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;

import java.io.Serializable;

/**
 * Created by Leonid on 06.09.13.
 */
class HumanPlayer implements Player, OnCellClickListener, Serializable {
    private final Id id;
    private final ReadOnlyGameBoard gameBoard;
    private final TicTacToeModel model;
    private Boolean movesAreEnabled;

    HumanPlayer(Id id, ReadOnlyGameBoard gameBoard, TicTacToeModel model) {
        this.id = id;
        this.gameBoard = gameBoard;
        this.model = model;
        this.movesAreEnabled = false;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void enableMoves() {
        movesAreEnabled = true;
    }

    @Override
    public void disableMoves() {
        movesAreEnabled = false;
    }

    @Override
    public void onCellClick(Position pos) {
        if (movesAreEnabled && gameBoard.cellIsEmpty(pos)) {
            model.onMove(pos, this);
        }
    }
}
