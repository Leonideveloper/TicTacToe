package com.gmail.leonidandand.tictactoe.game.view;


import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.tic_tac_toe_result.FireLine;

import java.util.Collection;

/**
 * Created by Leonid on 26.07.13.
 */
public interface GameBoard {
    void setOnCellClickListener(OnCellClickListener onCellClickListener);
    void showMove(Position pos);
    void showFireLines(Collection<FireLine> fireLines);
    void clear();
}
