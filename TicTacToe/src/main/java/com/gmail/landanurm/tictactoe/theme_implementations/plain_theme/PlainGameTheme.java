package com.gmail.landanurm.tictactoe.theme_implementations.plain_theme;


import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.GameBoardTheme;
import com.gmail.landanurm.tictactoe.theme.GameTheme;
import com.gmail.landanurm.tictactoe.theme.ScorePanelTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class PlainGameTheme implements GameTheme {
    private static final GameBoardTheme gameBoardTheme = new PlainGameBoardTheme();
    private static final ScorePanelTheme scorePanelTheme = new PlainScorePanelTheme();

    @Override
    public int getScreenBackgroundIconId() {
        return R.drawable.game_screen_background;
    }

    @Override
    public GameBoardTheme getGameBoardTheme() {
        return gameBoardTheme;
    }

    @Override
    public ScorePanelTheme getScorePanelTheme() {
        return scorePanelTheme;
    }
}
