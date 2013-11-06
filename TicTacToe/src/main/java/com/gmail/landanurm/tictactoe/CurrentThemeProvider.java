package com.gmail.landanurm.tictactoe;

import com.gmail.landanurm.tictactoe.theme.TicTacToeTheme;
import com.gmail.landanurm.tictactoe.theme_implementations.nexus_theme.NexusTicTacToeTheme;

/**
 * Created by Leonid on 17.10.13.
 */
public class CurrentThemeProvider {
    private static TicTacToeTheme currentTheme = new NexusTicTacToeTheme();

    public static TicTacToeTheme getCurrentTheme() {
        return currentTheme;
    }
}
