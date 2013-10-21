package com.gmail.landanurm.tictactoe.game.model_view.view;

import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 21.10.13.
 */
class ConnectorPlayersToView {
    private final TicTacToeView view;
    private final TicTacToeModel model;

    ConnectorPlayersToView(TicTacToeView view, TicTacToeModel model) {
        this.view = view;
        this.model = model;
    }

    void connect() {
        connectIfPlayerIsOnCellClickListener(model.getFirstPlayer());
        connectIfPlayerIsOnCellClickListener(model.getSecondPlayer());
    }

    private void connectIfPlayerIsOnCellClickListener(Player player) {
        if (player instanceof OnCellClickListener) {
            view.addOnCellClickListener((OnCellClickListener) player);
        }
    }
}
