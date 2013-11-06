package com.gmail.landanurm.tictactoe.theme_implementations.nexus_theme;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.game_theme.CellsTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class NexusCellsTheme implements CellsTheme {

    private static final int[] CELL_BACKGROUND_ICONS_IDS = {
            R.drawable.nexus_cell_background
    };

    private static final int[] FIRST_PLAYER_MOVE_ICONS_IDS = {
            R.drawable.nexus_cross_1,
            R.drawable.nexus_cross_2,
            R.drawable.nexus_cross_3,
            R.drawable.nexus_cross_4,
            R.drawable.nexus_cross_5
    };

    private static final int[] SECOND_PLAYER_MOVE_ICONS_IDS = {
            R.drawable.nexus_nought_1,
            R.drawable.nexus_nought_2,
            R.drawable.nexus_nought_3,
            R.drawable.nexus_nought_4,
            R.drawable.nexus_nought_5
    };

    private static final int[] FIRE_ICONS_IDS = {
            R.drawable.nexus_fire_1,
            R.drawable.nexus_fire_2,
            R.drawable.nexus_fire_3,
            R.drawable.nexus_fire_4,
            R.drawable.nexus_fire_5,
            R.drawable.nexus_fire_6
    };

    @Override
    public int[] getCellBackgroundIconIds() {
        return CELL_BACKGROUND_ICONS_IDS;
    }

    @Override
    public int[] getFirstPlayerMoveIconIds() {
        return FIRST_PLAYER_MOVE_ICONS_IDS;
    }

    @Override
    public int[] getSecondPlayerMoveIconIds() {
        return SECOND_PLAYER_MOVE_ICONS_IDS;
    }

    @Override
    public int[] getFireIconIds() {
        return FIRE_ICONS_IDS;
    }
}
