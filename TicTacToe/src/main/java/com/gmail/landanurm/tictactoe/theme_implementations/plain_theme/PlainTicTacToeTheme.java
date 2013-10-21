package com.gmail.landanurm.tictactoe.theme_implementations.plain_theme;

import com.gmail.landanurm.tictactoe.theme.GameTheme;
import com.gmail.landanurm.tictactoe.theme.TicTacToeTheme;

/**
 * Created by Leonid on 18.10.13.
 */
public class PlainTicTacToeTheme implements TicTacToeTheme {
    private static final GameTheme gameTheme = new PlainGameTheme();

    @Override
    public String getName() {
        return "Plain Theme";
    }

    @Override
    public GameTheme getGameTheme() {
        return gameTheme;
    }
}
