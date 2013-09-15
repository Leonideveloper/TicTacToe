package com.gmail.leonidandand.tictactoe.game.view;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.FireLine;
import com.gmail.leonidandand.tictactoe.game.player.Player;

import java.util.Collection;

/**
 * Created by Leonid on 26.07.13.
 */
public interface GameBoard {
    void clear();
    void showMove(Position pos, Player.Id playerId);
    void showFireLines(Collection<FireLine> fireLines);
    void setOnCellClickListener(OnCellClickListener onCellClickListener);
}
