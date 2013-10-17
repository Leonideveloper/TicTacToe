package com.gmail.landanurm.tictactoe.theme_implementations.plain_theme.game_theme;

import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.theme.game_theme.ScorePanelTheme;

/**
 * Created by Leonid on 17.10.13.
 */
class PlainScorePanelTheme implements ScorePanelTheme {
    @Override
    public int getBackgroundIconId() {
        return android.R.color.transparent;
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
        return R.color.green_transparent;
    }

    @Override
    public int getSecondPlayerScoreColor() {
        return R.color.red_transparent;
    }
}
