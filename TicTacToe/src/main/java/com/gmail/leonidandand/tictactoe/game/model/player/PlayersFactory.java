package com.gmail.leonidandand.tictactoe.game.model.player;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;

/**
 * Created by Leonid on 18.09.13.
 */
public interface PlayersFactory {
    Player createFirstPlayer(String playerType, TicTacToeModel model);
    Player createSecondPlayer(String playerType, TicTacToeModel model);
}
