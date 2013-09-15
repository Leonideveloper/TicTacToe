package com.gmail.leonidandand.tictactoe.game;

import com.gmail.leonidandand.tictactoe.game.player.Player;

/**
 * Created by Leonid on 07.09.13.
 */
public class TicTacToeContext {
    private static int gameBoardDimension;
    private static Player.Type firstPlayerType;
    private static Player.Type secondPlayerType;

    public static Player.Type getFirstPlayerType() {
        return firstPlayerType;
    }

    public static Player.Type getSecondPlayerType() {
        return secondPlayerType;
    }

    public static int getGameBoardDimension() {
        return gameBoardDimension;
    }

    public static void setGameBoardDimension(int gameBoardDimension) {
        TicTacToeContext.gameBoardDimension = gameBoardDimension;
    }

    public static void setFirstPlayerType(Player.Type firstPlayerType) {
        TicTacToeContext.firstPlayerType = firstPlayerType;
    }

    public static void setSecondPlayerType(Player.Type secondPlayerType) {
        TicTacToeContext.secondPlayerType = secondPlayerType;
    }
}
