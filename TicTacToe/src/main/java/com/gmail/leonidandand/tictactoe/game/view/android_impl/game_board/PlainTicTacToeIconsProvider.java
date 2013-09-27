package com.gmail.leonidandand.tictactoe.game.view.android_impl.game_board;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model.result.FireLine;

import java.util.Random;

/**
 * Created by Leonid on 19.07.13.
 */

class PlainTicTacToeIconsProvider implements TicTacToeIconsProvider {

    private static final int[] CROSS_ICONS_IDS = {
            R.drawable.cross_1, R.drawable.cross_2, R.drawable.cross_3
    };

    private static final int[] NOUGHT_ICONS_IDS = {
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
    public int getEmptyIconId() {
        return R.drawable.empty;
    }

    @Override
    public int getPlayerIconId(Player.Id playerId) {
        return randomElement(
            playerId == Player.Id.PLAYER_1
                ? CROSS_ICONS_IDS
                : NOUGHT_ICONS_IDS
        );
    }

    @Override
    public int getFireIconId(FireLine.Type fireLineType) {
        return randomElement(FIRE_ICONS_IDS);
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
}