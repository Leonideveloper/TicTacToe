package com.gmail.leonidandand.tictactoe.game.model.game_judge;

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
        return new TicTacToeResult(GameState.UNKNOWN, new ArrayList<FireLine>());
    }

    public static TicTacToeResult drawResult() {
        return new TicTacToeResult(GameState.DRAW, new ArrayList<FireLine>());
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
