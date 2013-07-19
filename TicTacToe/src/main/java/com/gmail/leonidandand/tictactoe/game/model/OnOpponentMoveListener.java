package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 18.07.13.
 */
public interface OnOpponentMoveListener {

    void onOpponentMove(Matrix.Position movePos);

}
