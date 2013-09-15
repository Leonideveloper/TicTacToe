package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import com.gmail.leonidandand.tictactoe.game.model.game_judge.FireLine;
import com.gmail.leonidandand.tictactoe.game.player.Player;

/**
 * Created by Leonid on 11.08.13.
 */
public interface TicTacToeIconsProvider {
    int getEmptyIconId();
    int getPlayerIconId(Player.Id playerId);
    int getFireIconId(FireLine.Type fireLineType);
}
