package com.gmail.landanurm.tictactoe.game.model_view.view;

import com.gmail.landanurm.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.util.Collection;

/**
 * Created by Leonid on 26.07.13.
 */
public interface GameBoardView {
    void setOnCellClickListener(OnCellClickListener onCellClickListener);
    void blockMoves();
    void unblockMoves();
    void clear();
    void showMove(com.gmail.landanurm.matrix.Position pos, Player.Position playerPosition);
    void showFireLines(Collection<FireLine> fireLines);
}
