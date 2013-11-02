package com.gmail.landanurm.tictactoe.game.model_view.model.player;

import com.gmail.landanurm.tictactoe.game.model_view.model.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;

/**
 * Created by Leonid on 18.09.13.
 */
public interface PlayersFactory {
    Player createFirstPlayer(ReadOnlyGameBoard gameBoard, TicTacToeModel model);
    Player createSecondPlayer(ReadOnlyGameBoard gameBoard, TicTacToeModel model);
}
