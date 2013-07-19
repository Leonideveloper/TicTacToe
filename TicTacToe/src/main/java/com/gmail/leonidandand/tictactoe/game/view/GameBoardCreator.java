package com.gmail.leonidandand.tictactoe.game.view;

import android.view.View;
import android.widget.ImageView;

import com.gmail.leonidandand.tictactoe.utils.Dimension;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 19.07.13.
 */
public interface GameBoardCreator {
    Matrix<ImageView> prepareCells(Dimension dim);
}
