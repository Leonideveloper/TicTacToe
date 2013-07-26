package com.gmail.leonidandand.tictactoe.game.view.android;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.utils.Randomizer;

/**
 * Created by Leonid on 19.07.13.
 */
public class IconRandomizer {

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

    public static int randomCrossIconId() {
        return randomElement(CROSS_ICONS_IDS);
    }

    public static int randomZeroIconId() {
        return randomElement(ZERO_ICONS_IDS);
    }

    public static int randomFireIconId() {
        return randomElement(FIRE_ICONS_IDS);
    }

    private static int randomElement(int[] array) {
        int randomIndex = Randomizer.randomPositiveInt() % array.length;
        return array[randomIndex];
    }
}
