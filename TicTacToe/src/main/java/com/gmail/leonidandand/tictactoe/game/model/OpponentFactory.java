package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 29.07.13.
 */
public class OpponentFactory {
    public static Opponent createDefault() {
        return new StupidAIOpponent();
    }
}
