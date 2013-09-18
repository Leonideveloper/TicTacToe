package com.gmail.leonidandand.tictactoe.game;

/**
 * Created by Leonid on 07.09.13.
 */
public class TicTacToeContext {
    private static int gameBoardDimension;
    private static String firstPlayerType;
    private static String secondPlayerType;

    public static String getFirstPlayerType() {
        return firstPlayerType;
    }

    public static String getSecondPlayerType() {
        return secondPlayerType;
    }

    public static int getGameBoardDimension() {
        return gameBoardDimension;
    }

    public static void setGameBoardDimension(int gameBoardDimension) {
        TicTacToeContext.gameBoardDimension = gameBoardDimension;
    }

    public static void setFirstPlayerType(String firstPlayerType) {
        TicTacToeContext.firstPlayerType = firstPlayerType;
    }

    public static void setSecondPlayerType(String secondPlayerType) {
        TicTacToeContext.secondPlayerType = secondPlayerType;
    }
}
