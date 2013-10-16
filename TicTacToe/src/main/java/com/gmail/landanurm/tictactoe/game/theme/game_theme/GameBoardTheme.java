package com.gmail.landanurm.tictactoe.game.theme.game_theme;

/**
 * Created by Leonid on 16.10.13.
 */
public interface GameBoardTheme {

    public static interface CellsTheme {
        int getEmptyCellIconId();
        int getFirstPlayerMoveIconId();
        int getSecondPlayerMoveIconId();
        int getFireIconId();
    }

    int getBackgroundIconId();
    int getDistanceBetweenCells();
    CellsTheme getCellsTheme();
}
