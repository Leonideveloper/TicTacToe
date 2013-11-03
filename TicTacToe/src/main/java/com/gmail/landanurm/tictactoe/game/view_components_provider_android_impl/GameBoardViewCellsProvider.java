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
    private int gameBoardDimension;

    GameBoardViewCellsProvider(Activity activity) {
        this.activity = activity;
        this.distanceBetweenCells = getDistanceBetweenCells();
    }

    private static int getDistanceBetweenCells() {
        TicTacToeTheme currentTheme = CurrentThemeProvider.getCurrentTheme();
        return currentTheme.getGameTheme().getGameBoardTheme().getDistanceBetweenCells();
    }

    Matrix<ImageView> prepareCells(Matrix<Integer> backgroundIconsIds) {
        gameBoardDimension = backgroundIconsIds.getDimension().rows;
        LinearLayout rowsContainerLayout = prepareRowsContainerLayout();
        Matrix<ImageView> cells = new SquareMatrix<ImageView>(gameBoardDimension);
        for (int row = 0; row < gameBoardDimension; ++row) {
            LinearLayout rowLayout = prepareRowLayout();
            for (int column = 0; column < gameBoardDimension; ++column) {
                Position pos = new Position(row, column);
                int cellBackgroundIconId = backgroundIconsIds.get(pos);
                ImageView cell = prepareCell(pos);
                cell.setBackgroundResource(cellBackgroundIconId);
                rowLayout.addView(cell);
                cells.set(pos, cell);
            }
            rowsContainerLayout.addView(rowLayout);
        }
        FrameLayout gameBoardLayout = (FrameLayout) activity.findViewById(R.id.gameBoardLayout);
        gameBoardLayout.addView(rowsContainerLayout);
        return cells;
    }

    private LinearLayout prepareRowsContainerLayout() {
        return prepareLinearLayout(LinearLayout.VERTICAL);
    }

    private LinearLayout prepareRowLayout() {
        return prepareLinearLayout(LinearLayout.HORIZONTAL);
    }

    private LinearLayout prepareLinearLayout(int orientation) {
        LinearLayout layout = new LinearLayout(activity);
        layout.setWeightSum(gameBoardDimension);
        layout.setOrientation(orientation);
        layout.setLayoutParams(createParams());
        return layout;
    }

    private LinearLayout.LayoutParams createParams() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
        );
    }

    private ImageView prepareCell(Position pos) {
        LayoutInflater inflater = activity.getLayoutInflater();
        ImageView cell = (ImageView) inflater.inflate(R.layout.cell_image_view, null);
        cell.setLayoutParams(prepareCellParams(pos));
        return cell;
    }

    private LinearLayout.LayoutParams prepareCellParams(Position pos) {
        LinearLayout.LayoutParams params = createParams();
        int left = (pos.column == 0) ? distanceBetweenCells : 0;
        int top = (pos.row == 0) ? distanceBetweenCells : 0;
        int right = distanceBetweenCells;
        int bottom = distanceBetweenCells;
        params.setMargins(left, top, right, bottom);
        return params;
    }
}
