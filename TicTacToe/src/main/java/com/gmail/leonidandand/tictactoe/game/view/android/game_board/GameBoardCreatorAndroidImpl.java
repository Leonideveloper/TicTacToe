package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Dimension;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.GameBoardCreator;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameBoardCreatorAndroidImpl implements GameBoardCreator {

    private final Activity activity;

    public GameBoardCreatorAndroidImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public GameBoard createGameBoard(Dimension dim) {
        return new GameBoardAndroidImpl(prepareCells(dim));
    }

    private Matrix<ImageView> prepareCells(Dimension dim) {
        FrameLayout gameBoardFrameLayout =
                (FrameLayout) activity.findViewById(R.id.gameBoardFrameLayout);
        Matrix<ImageView> cells = new ArrayMatrix<ImageView>(dim);
        LinearLayout verticalLayout = prepareVerticalLinearLayout(dim);
        for (int row = 0; row < dim.rows; ++row) {
            LinearLayout rowLayout = prepareHorizontalLinearLayout(dim);
            for (int column = 0; column < dim.columns; ++column) {
                ImageView cell = prepareCell();
                rowLayout.addView(cell);
                cells.set(new Position(row, column), cell);
            }
            verticalLayout.addView(rowLayout);
        }
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
        layout.setLayoutParams(createParams());
        return layout;
    }

    private ImageView prepareCell() {
        LayoutInflater inflater = activity.getLayoutInflater();
        ImageView cell = (ImageView) inflater.inflate(R.layout.cell_image_view, null);
        cell.setLayoutParams(createParams());
        return cell;
    }

    private LinearLayout.LayoutParams createParams() {
        return new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
        );
    }
}
