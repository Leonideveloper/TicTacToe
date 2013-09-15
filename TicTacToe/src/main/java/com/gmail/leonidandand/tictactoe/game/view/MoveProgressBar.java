package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.tictactoe.game.player.Player;

/**
 * Created by Leonid on 04.08.13.
 */
public interface MoveProgressBar {
    void show(Player.Id nextMovePlayerId);
    void hide();
}
