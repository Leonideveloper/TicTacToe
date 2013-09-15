package com.gmail.leonidandand.tictactoe.game.player;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;

/**
 * Created by Leonid on 12.09.13.
 */
abstract class BasePlayer implements Player {
    protected final Id id;
    protected TicTacToeModel model;

    protected BasePlayer(Player.Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    public void setModel(TicTacToeModel model) {
        this.model = model;
    }
}
