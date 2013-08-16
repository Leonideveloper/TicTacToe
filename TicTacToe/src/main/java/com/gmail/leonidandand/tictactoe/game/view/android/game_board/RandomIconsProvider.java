package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import com.gmail.leonidandand.tictactoe.game.model.tic_tac_toe_result.FireLine;

/**
 * Created by Leonid on 11.08.13.
 */
public interface RandomIconsProvider {
    int getRandomEmptyIconId();
    int getRandomCrossIconId();
    int getRandomZeroIconId();
    int getRandomFireIconId(FireLine.Type fireLineType);
}
