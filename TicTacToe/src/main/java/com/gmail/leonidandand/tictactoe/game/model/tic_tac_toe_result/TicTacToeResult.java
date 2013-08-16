package com.gmail.leonidandand.tictactoe.game.model.tic_tac_toe_result;

import com.gmail.leonidandand.tictactoe.game.model.GameState;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Leonid on 18.07.13.
 */
public class TicTacToeResult {
    private final GameState gameState;
    private final Collection<FireLine> fireLines;

    public static TicTacToeResult unknownResult() {
        return withoutFireLines(GameState.UNKNOWN);
    }

    public static TicTacToeResult drawResult() {
        return withoutFireLines(GameState.DRAW);
    }

    public static TicTacToeResult withoutFireLines(GameState opponentWins) {
        return new TicTacToeResult(opponentWins, new ArrayList<FireLine>());
    }

    public TicTacToeResult(GameState gameState, Collection<FireLine> fireLines) {
        this.gameState = gameState;
        this.fireLines = fireLines;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Collection<FireLine> getFireLines() {
        return fireLines;
    }

    public boolean isKnown() {
        return gameState != GameState.UNKNOWN;
    }
}
