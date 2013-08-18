package com.gmail.leonidandand.tictactoe.game.model.opponent;

import com.gmail.leonidandand.tictactoe.game.DifficultyLevel;

/**
 * Created by Leonid on 29.07.13.
 */
public class OpponentFactory {

    public static Opponent createByDifficultyLevel(DifficultyLevel level) {
        switch (level) {
        case EASY:
            return new EasyOpponent();

        case MEDIUM:
            return new MediumOpponent();

        case HARD:
            return new HardOpponent();
        }

        throw new IllegalArgumentException("Unknown Difficulty Level");
    }
}
