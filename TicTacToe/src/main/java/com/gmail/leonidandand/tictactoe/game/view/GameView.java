package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.tictactoe.game.model.GameModel;

/**
 * Created by Leonid on 18.07.13.
 */
public interface GameView {
    boolean movesBlocked();
    void blockMoves();
    void unblockMoves();
    void plugModel(GameModel model);
    void unplugModel();
}
