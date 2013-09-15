package com.gmail.leonidandand.tictactoe.game.player;


import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;

/**
 * Created by Leonid on 06.09.13.
 */
public interface Player {
    public static enum Id {
        PLAYER_1, PLAYER_2
    }

    public static enum Type {
        HUMAN, AI_NORMAL, AI_HARD, REMOTE
    }

    Id getId();
    void setModel(TicTacToeModel model);
    void enableMoves();
    void disableMoves();
}
