package com.gmail.leonidandand.tictactoe.game.player;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;

/**
 * Created by Leonid on 06.09.13.
 */
class HumanPlayer extends BasePlayer implements OnCellClickListener {

    private boolean movesEnabled;

    public HumanPlayer(Player.Id id) {
        super(id);
        movesEnabled = false;
    }

    @Override
    public void onCellClick(Position pos) {
        if (movesEnabled && model.isEmptyCell(pos)) {
            model.onMove(pos, this);
        }
    }

    @Override
    public void enableMoves() {
        movesEnabled = true;
    }

    @Override
    public void disableMoves() {
        movesEnabled = false;
    }
}
