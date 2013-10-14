package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.ReadOnlyGameBoard;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

import java.io.Serializable;

/**
 * Created by Leonid on 12.09.13.
 */
abstract class BasePlayer implements Player, Serializable {
    protected final ReadOnlyGameBoard gameBoard;
    protected final TicTacToeModel model;
    private final Player.Id id;

    protected BasePlayer(Player.Id id, TicTacToeModel model) {
        this.gameBoard = model.getGameBoard();
        this.model = model;
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }
}
