package com.gmail.leonidandand.tictactoe.game.model_view.model.judge;

import java.util.ArrayList;

/**
 * Created by Leonid on 17.09.13.
 */
class TicTacToeResultCreator {

    public static TicTacToeResult createUnknownResult() {
        return createResultWithoutFireLines(TicTacToeResult.GameState.UNKNOWN);
    }

    public static TicTacToeResult createDrawResult() {
        return createResultWithoutFireLines(TicTacToeResult.GameState.DRAW);
    }

    public static TicTacToeResult createResultWithoutFireLines(TicTacToeResult.GameState state) {
        return new TicTacToeResult(state, new ArrayList<FireLine>());
    }
}
