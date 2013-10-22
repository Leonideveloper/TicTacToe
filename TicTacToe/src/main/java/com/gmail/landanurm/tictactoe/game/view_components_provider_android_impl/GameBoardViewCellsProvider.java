package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.CurrentThemeProvider;
import com.gmail.landanurm.tictactoe.R;
import com.gmail.landanurm.tictactoe.game.model_view.model.SquareMatrix;
import com.gmail.landanurm.tictactoe.theme.TicTacToeTheme;

/**
 * Created by Leonid on 19.07.13.
 */

class GameBoardViewCellsProvider {
    private final Activity activity;
    private final Integer distanceBetweenCells;

    static Matrix<ImageView> prepareCells(Activity activity, int gameBoardDimension) {
        return new GameBoardViewCellsProvider(activity).prepareCells(gameBoardDimension);
    }

    GameBoardViewCellsProvider(Activity activity) {
        this.activity = activity;
        this.distanceBetweenCells = getDistanceBetweenCells();
    }

    private static int getDistanceBetweenCells() {
        TicTacToeTheme theme = CurrentThemeProvider.getCurrentTheme();
        return theme.getGameTheme().getGameBoardTheme().getDistanceBetweenCells();
    }

    Matrix<ImageView> prepareCells(final int gameBoardDimension) {
        Matrix<ImageView> cells = new SquareMatrix<ImageView>(gameBoardDimension);
        LinearLayout rowsContainerLayout = prepareRowsContainerLayout(gameBoardDimension);
        for (int row = 0; row < gameBoardDimension; ++row) {
            LinearLayout rowLayout = prepareRowLayout(gameBoardDimension);
            for (int column = 0; column < gameBoardDimension; ++column) {
                ImageView cell = prepareCell(row, column);
                rowLayout.addView(cell);
                cells.set(new Position(row, column), cell);
            }
            rowsContainerLayout.addView(rowLayout);
        }
        FrameLayout gameBoardFrameLayout =
                (FrameLayout) activity.findViewById(R.id.gameBoardFrameLayout);
        gameBoardFrameLayout.addView(rowsContainerLayout);
        return cells;
    }

    private LinearLayout prepareRowsContainerLayout(int numberOfRows) {
        return prepareLinearLayout(LinearLayout.VERTICAL, numberOfRows);
    }

    private LinearLayout prepareRowLayout(int numberOfColumns) {
        return prepareLinearLayout(LinearLayout.HORIZONTAL, numberOfColumns);
    }

    private LinearLayout prepareLinearLayout(int orientation, int weightSum) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setWeightSum(weightSum);
        layout.setOrientation(orientation);
        layout.setLayoutParams(prepareParams());
        return layout;
    }

    private LinearLayout.LayoutParams prepareParams() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
        );
    }

    private ImageView prepareCell(int row, int column) {
        LayoutInflater inflater = activity.getLayoutInflater();
        ImageView cell = (ImageView) inflater.inflate(R.layout.cell_image_view, null);
        cell.setLayoutParams(prepareCellParams(row, column));
        return cell;
    }

    private LinearLayout.LayoutParams prepareCellParams(int row, int column) {
        int left = (column == 0) ? distanceBetweenCells : 0;
        int top = (row == 0) ? distanceBetweenCells : 0;
        int right = distanceBetweenCells;
        int bottom = distanceBetweenCells;
        LinearLayout.LayoutParams params = prepareParams();
        params.setMargins(left, top, right, bottom);
        return params;
    }
}
