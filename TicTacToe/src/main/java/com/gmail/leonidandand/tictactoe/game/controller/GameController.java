package com.gmail.leonidandand.tictactoe.game.controller;


import com.gmail.leonidandand.matrix.Position;

/**
 * Created by Leonid on 18.07.13.
 */
public interface GameController {
    void onViewIsReadyToStartGame();
    void onPlayerMove(Position movePos);
    void onPlayerGivesUp();
}
