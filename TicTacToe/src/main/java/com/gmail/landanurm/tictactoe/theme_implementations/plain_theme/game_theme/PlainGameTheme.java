package com.gmail.landanurm.tictactoe.theme_implementations.plain_theme.game_theme;


import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.game_theme.GameBoardTheme;
import com.gmail.landanurm.tictactoe.theme.game_theme.GameTheme;
import com.gmail.landanurm.tictactoe.theme.game_theme.ScorePanelTheme;

/**
 * Created by Leonid on 17.10.13.
 */
public class PlainGameTheme implements GameTheme {
    private static final GameBoardTheme gameBoardTheme = new PlainGameBoardTheme();
    private static final ScorePanelTheme scorePanelTheme = new PlainScorePanelTheme();

    @Override
    public int getScreenBackgroundIconId() {
        return android.R.color.transparent;
    }

    @Override
    public int getSeparatorIconId() {
        return R.drawable.separator;
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
