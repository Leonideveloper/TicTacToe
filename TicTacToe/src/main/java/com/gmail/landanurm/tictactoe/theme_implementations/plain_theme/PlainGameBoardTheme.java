package com.gmail.landanurm.tictactoe.theme_implementations.plain_theme;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.CellsTheme;
import com.gmail.landanurm.tictactoe.theme.GameBoardTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class PlainGameBoardTheme implements GameBoardTheme {
    private static final CellsTheme cellsTheme = new PlainCellsTheme();

    @Override
    public int getBackgroundIconId() {
        return R.color.light_green;
    }

    @Override
    public int getDistanceBetweenCells() {
        return 7;
    }

    @Override
    public CellsTheme getCellsTheme() {
        return cellsTheme;
    }
}
