package com.gmail.leonidandand.tictactoe.game.controller;


import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.view.GameView;

/**
 * Created by Leonid on 18.07.13.
 */
public interface GameController {
    GameView getView();
    void onViewIsReadyToStartGame();
    void onPlayerMove(Position movePos);
}
