package com.gmail.leonidandand.tictactoe.game.view;

/**
 * Created by Leonid on 18.07.13.
 */
public interface GameView {

    boolean movesBlocked();

    void blockMoves();

    void unblockMoves();
}
