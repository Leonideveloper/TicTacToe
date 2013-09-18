package com.gmail.leonidandand.tictactoe.game.model.result;

import java.util.ArrayList;

/**
 * Created by Leonid on 17.09.13.
 */
public class TicTacToeResultCreator {

    public static TicTacToeResult createUnknownResult() {
        return createWithoutFireLines(TicTacToeResult.GameState.UNKNOWN);
    }

    public static TicTacToeResult createDrawResult() {
        return createWithoutFireLines(TicTacToeResult.GameState.DRAW);
    }

    public static TicTacToeResult createWithoutFireLines(TicTacToeResult.GameState gameState) {
        return new TicTacToeResult(gameState, new ArrayList<FireLine>());
    }
}
