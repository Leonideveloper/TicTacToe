package com.gmail.landanurm.tictactoe.theme_implementations.xo_theme;

import com.gmail.landanurm.tictactoe.theme.game_theme.GameTheme;
import com.gmail.landanurm.tictactoe.theme.TicTacToeTheme;

/**
 * Created by Leonid on 18.10.13.
 */
public class XOTicTacToeTheme implements TicTacToeTheme {
    private static final GameTheme gameTheme = new XOGameTheme();

    @Override
    public String getName() {
        return "XO Theme";
    }

    @Override
    public GameTheme getGameTheme() {
        return gameTheme;
    }
}
