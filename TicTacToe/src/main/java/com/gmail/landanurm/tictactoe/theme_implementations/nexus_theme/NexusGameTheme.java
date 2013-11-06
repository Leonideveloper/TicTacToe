package com.gmail.landanurm.tictactoe.theme_implementations.nexus_theme;


import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.game_theme.GameBoardTheme;
import com.gmail.landanurm.tictactoe.theme.game_theme.GameTheme;
import com.gmail.landanurm.tictactoe.theme.game_theme.ScorePanelTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class NexusGameTheme implements GameTheme {
    private static final GameBoardTheme gameBoardTheme = new NexusGameBoardTheme();
    private static final ScorePanelTheme scorePanelTheme = new NexusScorePanelTheme();

    @Override
    public int getScreenBackgroundIconId() {
        return R.drawable.nexus_game_screen_background;
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
