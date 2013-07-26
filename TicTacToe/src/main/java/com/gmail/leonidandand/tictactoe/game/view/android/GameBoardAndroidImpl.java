package com.gmail.leonidandand.tictactoe.game.view.android;

import android.view.View;
import android.widget.ImageView;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.view.CellIcon;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.List;

public class GameBoardAndroidImpl implements GameBoard {

    private final Matrix<ImageView> cells;
    private CellIcon currentIcon;

    public GameBoardAndroidImpl(Matrix<ImageView> cells) {
        this.cells = cells;
        clear();
    }

    @Override
    public void clear() {
        cells.forEach(new Matrix.OnEachHandler<ImageView>() {
            @Override
            public void handle(Matrix<ImageView> matrix, Matrix.Position pos) {
                clearCell(pos);
            }
        });
        currentIcon = CellIcon.X;
    }

    private void clearCell(Matrix.Position cellPos) {
        setCellImageResource(cellPos, android.R.color.transparent);
        setCellBackgroundResource(cellPos, R.drawable.empty);
    }

    @Override
    public void setOnCellClickListener(final OnCellClickListener onCellClickListener) {
        cells.forEach(new Matrix.OnEachHandler<ImageView>() {
            @Override
            public void handle(Matrix<ImageView> matrix, final Matrix.Position pos) {
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
    public void showMove(Matrix.Position pos) {
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
    public void showFireLine(List<Matrix.Position> positions) {
        for (Matrix.Position pos : positions) {
            setCellImageResource(pos, IconRandomizer.randomFireIconId());
        }
    }

    private void setCellBackgroundResource(Matrix.Position cellPos, int resId) {
        cells.get(cellPos).setBackgroundResource(resId);
    }

    private void setCellImageResource(Matrix.Position cellPos, int resId) {
        cells.get(cellPos).setImageResource(resId);
    }
}
