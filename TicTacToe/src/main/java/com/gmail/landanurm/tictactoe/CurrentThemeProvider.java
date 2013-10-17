package com.gmail.landanurm.tictactoe;

import com.gmail.landanurm.tictactoe.theme.game_theme.GameTheme;
import com.gmail.landanurm.tictactoe.theme_implementations.plain_theme.game_theme.PlainGameTheme;

/**
 * Created by Leonid on 17.10.13.
 */
public class CurrentThemeProvider {

    private static GameTheme gameTheme = new PlainGameTheme();

    public static GameTheme getGameTheme() {
        return gameTheme;
    }
}
