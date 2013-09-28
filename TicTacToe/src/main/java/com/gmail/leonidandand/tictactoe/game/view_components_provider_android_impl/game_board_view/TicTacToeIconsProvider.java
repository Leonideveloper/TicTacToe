package com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl.game_board_view;

import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 11.08.13.
 */
interface TicTacToeIconsProvider {
    int getEmptyIconId();
    int getPlayerIconId(Player.Id playerId);
    int getFireIconId(FireLine.Type fireLineType);
}
