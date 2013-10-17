package com.gmail.landanurm.tictactoe.theme.game_theme;

/**
 * Created by Leonid on 16.10.13.
 */
public interface GameTheme {
    int getScreenBackgroundIconId();
    int getSeparatorIconId();
    GameBoardTheme getGameBoardTheme();
    ScorePanelTheme getScorePanelTheme();
}
