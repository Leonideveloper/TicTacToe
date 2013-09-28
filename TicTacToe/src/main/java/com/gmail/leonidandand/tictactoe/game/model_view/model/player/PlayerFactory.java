package com.gmail.leonidandand.tictactoe.game.model_view.model.player;

import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;

/**
 * Created by Leonid on 18.09.13.
 */
public interface PlayerFactory {
    Player createFirstPlayer(String playerType, TicTacToeModel model);
    Player createSecondPlayer(String playerType, TicTacToeModel model);
}
