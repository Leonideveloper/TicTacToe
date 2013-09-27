package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.matrix.ReadOnlyMatrix;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 12.09.13.
 */
abstract class BasePlayer implements Player {
    protected final Player.Id id;
    protected final TicTacToeModel model;
    protected final ReadOnlyMatrix<Player.Id> gameBoard;

    protected BasePlayer(Player.Id id, TicTacToeModel model) {
        this.id = id;
        this.model = model;
        this.gameBoard = model.getGameBoard();
    }

    protected boolean cellIsEmpty(Position pos) {
        return gameBoard.get(pos) == null;
    }

    public Id getId() {
        return id;
    }
}
