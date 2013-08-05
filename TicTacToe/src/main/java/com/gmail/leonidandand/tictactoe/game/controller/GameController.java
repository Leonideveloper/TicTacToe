package com.gmail.leonidandand.tictactoe.game.controller;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public interface GameController {
    void onViewIsReadyToStartGame();
    void onPlayerMove(Matrix.Position movePos);
}
