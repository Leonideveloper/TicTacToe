package com.gmail.landanurm.tictactoe.game.model_view.view;

import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;

import java.util.Collection;

/**
 * Created by Leonid on 26.07.13.
 */
public interface GameBoardView {
    void blockMoves();
    void unblockMoves();
    void clear();
    void showMove(Position pos, Player.Id playerId);
    void showFireLines(Collection<FireLine> fireLines);
    void setOnCellClickListener(OnCellClickListener onCellClickListener);
}
