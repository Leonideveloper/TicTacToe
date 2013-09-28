package com.gmail.leonidandand.tictactoe.game.model_view.model.listeners;

import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public interface OnMovePlayerChangedListener {
    void onMovePlayerChanged(Player.Id movePlayerId);
}
