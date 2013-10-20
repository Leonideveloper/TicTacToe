package com.gmail.landanurm.tictactoe.theme_implementations.plain_theme;

import android.graphics.Color;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.ScorePanelTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class PlainScorePanelTheme implements ScorePanelTheme {
    @Override
    public int getBackgroundIconId() {
        return R.drawable.score_panel_background;
    }

    @Override
    public int getFirstPlayerFaceIconId() {
        return R.drawable.first_player_face;
    }

    @Override
    public int getSecondPlayerFaceIconId() {
        return R.drawable.second_player_face;
    }

    @Override
    public int getVersusIconId() {
        return R.drawable.versus;
    }

    @Override
    public int getFirstPlayerScoreColor() {
        return Color.GREEN;
    }

    @Override
    public int getSecondPlayerScoreColor() {
        return Color.RED;
    }
}
