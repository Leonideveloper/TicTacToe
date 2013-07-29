package com.gmail.leonidandand.tictactoe.game.view.android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.GameBoardCreator;
import com.gmail.leonidandand.tictactoe.utils.Dimension;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameBoardCreatorAndroidImpl implements GameBoardCreator {

    private static final int SPACE_BETWEEN_CELLS = 2;

    private final Activity activity;

    public GameBoardCreatorAndroidImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public GameBoard createGameBoard(Dimension dim) {
        return new GameBoardAndroidImpl(prepareCells(dim));
    }

    private Matrix<ImageView> prepareCells(Dimension dim) {
        Matrix<ImageView> cells = new Matrix<ImageView>(dim);
        LinearLayout verticalLayout = prepareVerticalLinearLayout(dim);
        for (int row = 0; row < dim.rows; ++row) {
            LinearLayout rowLayout = prepareHorizontalLinearLayout(dim);
            for (int column = 0; column < dim.columns; ++column) {
                ImageView cell = prepareCell();
                setHorizontalMargins(cell, column, dim.columns);
                rowLayout.addView(cell);
                cells.set(row, column, cell);
            }
            setVerticalMargins(rowLayout, row, dim.rows);
            verticalLayout.addView(rowLayout);
        }
        FrameLayout gameBoardFrameLayout =
                (FrameLayout) activity.findViewById(R.id.gameBoardFrameLayout);
        gameBoardFrameLayout.addView(verticalLayout);
        return cells;
    }

    private LinearLayout prepareVerticalLinearLayout(Dimension dim) {
        return prepareLinearLayout(LinearLayout.VERTICAL, dim.rows);
    }

    private LinearLayout prepareHorizontalLinearLayout(Dimension dim) {
        return prepareLinearLayout(LinearLayout.HORIZONTAL, dim.columns);
    }

    private LinearLayout prepareLinearLayout(int orientation, int weightSum) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setWeightSum(weightSum);
        layout.setOrientation(orientation);
        return layout;
    }

    private ImageView prepareCell() {
        LayoutInflater inflater = activity.getLayoutInflater();
        return (ImageView) inflater.inflate(R.layout.cell_image_view, null);
    }

    private void setHorizontalMargins(ImageView cell, int column, int columns) {
        int leftMargin = (column == 0) ? 0 : SPACE_BETWEEN_CELLS;
        int rightMargin = (column == columns - 1) ? 0 : SPACE_BETWEEN_CELLS;
        setMargins(cell, leftMargin, 0, rightMargin, 0);
    }

    private void setVerticalMargins(LinearLayout rowLayout, int row, int rows) {
        int topMargin = (row == 0) ? 0 : SPACE_BETWEEN_CELLS;
        int bottomMargin = (row == rows - 1) ? 0 : SPACE_BETWEEN_CELLS;
        setMargins(rowLayout, 0, topMargin, 0, bottomMargin);
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams params = createLinearLayoutParams();
        params.setMargins(left, top, right, bottom);
        view.setLayoutParams(params);
    }

    private LinearLayout.LayoutParams createLinearLayoutParams() {
        return new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
        );
    }
}
