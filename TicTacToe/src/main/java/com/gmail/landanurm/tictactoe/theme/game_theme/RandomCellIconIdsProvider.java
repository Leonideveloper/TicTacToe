package com.gmail.landanurm.tictactoe.theme.game_theme;

import java.util.Random;

/**
 * Created by Leonid on 05.11.13.
 */
public class RandomCellIconIdsProvider implements CellIconIdsProvider {

    private final CellsTheme cellsTheme;

    public RandomCellIconIdsProvider(CellsTheme cellsTheme) {
        this.cellsTheme = cellsTheme;
    }

    @Override
    public int getCellBackgroundIconId() {
        return randomElementFrom(cellsTheme.getCellBackgroundIconIds());
    }

    @Override
    public int getFirstPlayerMoveIconId() {
        return randomElementFrom(cellsTheme.getFirstPlayerMoveIconIds());
    }

    @Override
    public int getSecondPlayerMoveIconId() {
        return randomElementFrom(cellsTheme.getSecondPlayerMoveIconIds());
    }

    @Override
    public int getFireIconId() {
        return randomElementFrom(cellsTheme.getFireIconIds());
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
