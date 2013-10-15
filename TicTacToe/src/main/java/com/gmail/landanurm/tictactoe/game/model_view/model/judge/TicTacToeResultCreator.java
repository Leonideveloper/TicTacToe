package com.gmail.landanurm.tictactoe.game.model_view.model.judge;

import java.util.ArrayList;

/**
 * Created by Leonid on 17.09.13.
 */
class TicTacToeResultCreator {

    static TicTacToeResult createUnknownResult() {
        return createResultWithoutFireLines(TicTacToeResult.GameState.UNKNOWN);
    }

    static TicTacToeResult createDrawResult() {
        return createResultWithoutFireLines(TicTacToeResult.GameState.DRAW);
    }

    static TicTacToeResult createResultWithoutFireLines(TicTacToeResult.GameState state) {
        return new TicTacToeResult(state, new ArrayList<FireLine>());
    }
}
