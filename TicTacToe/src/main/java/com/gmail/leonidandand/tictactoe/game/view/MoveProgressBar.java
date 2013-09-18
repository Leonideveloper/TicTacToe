package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.tictactoe.game.model.player.Player;

/**
 * Created by Leonid on 04.08.13.
 */
public interface MoveProgressBar {
    void show(Player.Id nextMovePlayerId);
    void hide();
}
