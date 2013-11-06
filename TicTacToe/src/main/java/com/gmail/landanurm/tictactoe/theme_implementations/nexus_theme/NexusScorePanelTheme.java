package com.gmail.landanurm.tictactoe.theme_implementations.nexus_theme;

import android.graphics.Color;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.game_theme.ScorePanelTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class NexusScorePanelTheme implements ScorePanelTheme {
    @Override
    public int getBackgroundIconId() {
        return R.drawable.nexus_score_panel_background;
    }

    @Override
    public int getFirstPlayerFaceIconId() {
        return R.drawable.nexus_cross_1;
    }

    @Override
    public int getSecondPlayerFaceIconId() {
        return R.drawable.nexus_nought_1;
    }

    @Override
    public int getVersusIconId() {
        return R.drawable.nexus_versus;
    }

    @Override
    public int getFirstPlayerScoreColor() {
        return Color.WHITE;
    }

    @Override
    public int getSecondPlayerScoreColor() {
        return Color.WHITE;
    }
}
