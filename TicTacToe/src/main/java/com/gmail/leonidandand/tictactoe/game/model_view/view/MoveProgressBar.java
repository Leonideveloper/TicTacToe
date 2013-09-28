package com.gmail.leonidandand.tictactoe.game.model_view.view;

import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 04.08.13.
 */
public interface MoveProgressBar {
    void show(Player.Id nextMovePlayerId);
    void hide();
}
