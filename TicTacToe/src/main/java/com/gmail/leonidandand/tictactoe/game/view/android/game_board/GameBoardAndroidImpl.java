package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import android.view.View;
import android.widget.ImageView;

import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.OnEachHandler;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.view.CellIcon;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;
import com.gmail.leonidandand.tictactoe.game.view.android.IconRandomizer;

import java.util.Collection;

public class GameBoardAndroidImpl implements GameBoard {

    private final Matrix<ImageView> cells;
    private CellIcon currentIcon;

    GameBoardAndroidImpl(Matrix<ImageView> cells) {
        this.cells = cells;
        clear();
    }

    @Override
    public void clear() {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position pos, ImageView elem) {
                clearCell(pos);
            }
        });
        currentIcon = CellIcon.X;
    }

    private void clearCell(Position cellPos) {
        setCellImageResource(cellPos, android.R.color.transparent);
        setCellBackgroundResource(cellPos, R.drawable.empty);
    }

    @Override
    public void setOnCellClickListener(final OnCellClickListener onCellClickListener) {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView elem) {
                ImageView cell = cells.get(pos);
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCellClickListener.onCellClick(pos);
                    }
                });
            }
        });
    }

    @Override
    public void showMove(Position pos) {
        int iconId;
        if (currentIcon == CellIcon.X) {
            iconId = IconRandomizer.randomCrossIconId();
            currentIcon = CellIcon.O;
        } else {
            iconId = IconRandomizer.randomZeroIconId();
            currentIcon = CellIcon.X;
        }
        setCellBackgroundResource(pos, iconId);
    }

    @Override
    public void showFireLine(Collection<Position> positions) {
        for (Position pos : positions) {
            setCellImageResource(pos, IconRandomizer.randomFireIconId());
        }
    }

    private void setCellBackgroundResource(Position cellPos, int resId) {
        cells.get(cellPos).setBackgroundResource(resId);
    }

    private void setCellImageResource(Position cellPos, int resId) {
        cells.get(cellPos).setImageResource(resId);
    }
}
