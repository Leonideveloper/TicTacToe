package com.gmail.landanurm.tictactoe.theme;

import com.gmail.landanurm.tictactoe.game.model_view.model.judge.FireLine;

/**
 * Created by Leonid on 17.10.13.
 */
public interface CellsTheme {
    int getEmptyCellIconId();
    int getFirstPlayerMoveIconId();
    int getSecondPlayerMoveIconId();
    int getFireIconId(FireLine.Type fireLineType);
}
