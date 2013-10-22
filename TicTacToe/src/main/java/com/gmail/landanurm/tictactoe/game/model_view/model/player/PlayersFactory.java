package com.gmail.landanurm.tictactoe.game.model_view.model.player;

import com.gmail.landanurm.tictactoe.game.model_view.model.game_board.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.OnMoveListener;

/**
 * Created by Leonid on 18.09.13.
 */
public interface PlayersFactory {
    Player createFirstPlayer(String playerType, ReadOnlyGameBoard gameBoard, OnMoveListener onMoveListener);
    Player createSecondPlayer(String playerType, ReadOnlyGameBoard gameBoard, OnMoveListener onMoveListener);
}
