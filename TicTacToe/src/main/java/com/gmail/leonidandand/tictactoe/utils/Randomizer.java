package com.gmail.leonidandand.tictactoe.utils;

import java.util.Random;

/**
 * Created by Leonid on 12.07.13.
 */
public class Randomizer {

    public static int randomPositiveInt() {
        Random random = new Random(System.currentTimeMillis());
        random.setSeed(System.nanoTime());
        return Math.abs(random.nextInt());
    }
}
