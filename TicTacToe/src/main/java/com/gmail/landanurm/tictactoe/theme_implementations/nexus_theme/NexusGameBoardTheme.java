package com.gmail.landanurm.tictactoe.theme_implementations.nexus_theme;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.game_theme.CellsTheme;
import com.gmail.landanurm.tictactoe.theme.game_theme.GameBoardTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class NexusGameBoardTheme implements GameBoardTheme {
    private static final CellsTheme cellsTheme = new NexusCellsTheme();

    @Override
    public int getBackgroundIconId() {
        return R.drawable.nexus_game_board_background;
    }

    @Override
    public int getDistanceBetweenCells() {
        return 11;
    }

    @Override
    public CellsTheme getCellsTheme() {
        return cellsTheme;
    }
}
