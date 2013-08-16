package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model.tic_tac_toe_result.FireLine;

import java.util.Random;

/**
 * Created by Leonid on 19.07.13.
 */

public class PlainRandomIconsProvider implements RandomIconsProvider {

    private static final int[] CROSS_ICONS_IDS = {
            R.drawable.cross_1, R.drawable.cross_2, R.drawable.cross_3
    };

    private static final int[] ZERO_ICONS_IDS = {
            R.drawable.zero_1, R.drawable.zero_2, R.drawable.zero_3
    };

    private static final int[] FIRE_ICONS_IDS = {
            R.drawable.fire_1, R.drawable.fire_2, R.drawable.fire_3,
            R.drawable.fire_4, R.drawable.fire_5, R.drawable.fire_6
    };

    private static final int[] ROW_FIRE_ICONS_IDS = {
            R.drawable.row_fire_line_1
    };
    private static final int[] COLUMN_FIRE_ICONS_IDS = {
            R.drawable.column_fire_line_1
    };
    private static final int[] LEFT_UPPER_DIAGONAL_FIRE_ICONS_IDS = {
            R.drawable.left_upper_diagonal_fire_line_1
    };
    private static final int[] RIGHT_UPPER_DIAGONAL_FIRE_ICONS_IDS = {
            R.drawable.right_upper_diagonal_fire_line_1
    };

    @Override
    public int getRandomEmptyIconId() {
        return R.drawable.empty;
    }

    @Override
    public int getRandomCrossIconId() {
        return randomElement(CROSS_ICONS_IDS);
    }

    private static int randomElement(int[] array) {
        int randomIndex = randomPositiveInt() % array.length;
        return array[randomIndex];
    }

    private static int randomPositiveInt() {
        Random random = new Random();
        random.setSeed(System.nanoTime() + System.currentTimeMillis() * random.nextLong());
        int randomInt = Math.abs(random.nextInt());
        return Math.max(1, randomInt);
    }

    @Override
    public int getRandomZeroIconId() {
        return randomElement(ZERO_ICONS_IDS);
    }

    @Override
    public int getRandomFireIconId(FireLine.Type fireLineType) {
        return randomElement(FIRE_ICONS_IDS);
    }
}
