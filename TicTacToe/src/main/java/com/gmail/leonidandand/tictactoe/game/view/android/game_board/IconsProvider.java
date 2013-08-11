package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import com.gmail.leonidandand.tictactoe.game.model.game_judge.FireLine;

/**
 * Created by Leonid on 11.08.13.
 */
public interface IconsProvider {
    int getEmptyIconId();
    int getCrossIconId();
    int getZeroIconId();
    int getFireIconId(FireLine.Type fireLineType);
}
