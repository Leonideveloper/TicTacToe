package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.ReadOnlyGameBoard;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 12.09.13.
 */
abstract class BasePlayer implements Player {
    private final Player.Id id;
    protected final TicTacToeModel model;
    protected final ReadOnlyGameBoard gameBoard;

    protected BasePlayer(Player.Id id, TicTacToeModel model) {
        this.id = id;
        this.model = model;
        this.gameBoard = model.getGameBoard();
    }

    @Override
    public Id getId() {
        return id;
    }
}
