package com.gmail.landanurm.tictactoe.game.model_view.model.judge;

import java.util.ArrayList;

/**
 * Created by Leonid on 17.09.13.
 */
class TicTacToeResultCreator {

    static TicTacToeResult createUnknownResult() {
        return createResultWithoutFireLines(GameState.UNKNOWN);
    }

    static TicTacToeResult createDrawResult() {
        return createResultWithoutFireLines(GameState.DRAW);
    }

    static TicTacToeResult createResultWithoutFireLines(GameState state) {
        return new TicTacToeResult(state, new ArrayList<FireLine>());
    }
}
