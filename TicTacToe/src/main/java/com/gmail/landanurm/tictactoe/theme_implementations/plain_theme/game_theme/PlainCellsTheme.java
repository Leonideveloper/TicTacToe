package com.gmail.landanurm.tictactoe.theme_implementations.plain_theme.game_theme;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.landanurm.tictactoe.theme.game_theme.CellsTheme;

import java.util.Random;

/**
 * Created by Leonid on 17.10.13.
 */
class PlainCellsTheme implements CellsTheme {

    private static final int[] FIRST_PLAYER_MOVE_ICONS_IDS = {
            R.drawable.first_player_move_1,
            R.drawable.first_player_move_2,
            R.drawable.first_player_move_3
    };

    private static final int[] SECOND_PLAYER_MOVE_ICONS_IDS = {
            R.drawable.second_player_move_1,
            R.drawable.second_player_move_2,
            R.drawable.second_player_move_3
    };

    private static final int[] FIRE_ICONS_IDS = {
            R.drawable.fire_1, R.drawable.fire_2, R.drawable.fire_3,
            R.drawable.fire_4, R.drawable.fire_5, R.drawable.fire_6
    };

    @Override
    public int getEmptyCellIconId() {
        return R.drawable.empty_cell;
    }

    @Override
    public int getFirstPlayerMoveIconId() {
        return randomElementFrom(FIRST_PLAYER_MOVE_ICONS_IDS);
    }

    @Override
    public int getSecondPlayerMoveIconId() {
        return randomElementFrom(SECOND_PLAYER_MOVE_ICONS_IDS);
    }

    @Override
    public int getFireIconId(FireLine.Type fireLineType) {
        return randomElementFrom(FIRE_ICONS_IDS);
    }

    private static int randomElementFrom(int[] array) {
        int randomIndex = randomPositiveInt() % array.length;
        return array[randomIndex];
    }

    private static int randomPositiveInt() {
        Random random = new Random();
        random.setSeed(System.nanoTime() + System.currentTimeMillis() * random.nextLong());
        int randomInt = Math.abs(random.nextInt());
        return Math.max(1, randomInt);
    }
}
