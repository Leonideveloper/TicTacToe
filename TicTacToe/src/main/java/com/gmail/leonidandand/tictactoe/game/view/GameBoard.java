package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.List;

/**
 * Created by Leonid on 26.07.13.
 */
public interface GameBoard {
    void setOnCellClickListener(OnCellClickListener onCellClickListener);
    void showMove(Matrix.Position pos);
    void showFireLine(List<Matrix.Position> positions);
    void clear();
}
