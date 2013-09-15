package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.R;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameBoardCreator {

    private final Activity activity;

    public GameBoardCreator(Activity activity) {
        this.activity = activity;
    }

    public GameBoardAndroidImpl createGameBoardByDimension(int dimension) {
        return new GameBoardAndroidImpl(prepareCells(dimension));
    }

    private Matrix<ImageView> prepareCells(final int gameBoardDimension) {
        Matrix<ImageView> cells = new ArrayMatrix<ImageView>(gameBoardDimension, gameBoardDimension);
        LinearLayout verticalLayout = prepareVerticalLinearLayout(gameBoardDimension);
        for (int row = 0; row < gameBoardDimension; ++row) {
            LinearLayout rowLayout = prepareHorizontalLinearLayout(gameBoardDimension);
            for (int column = 0; column < gameBoardDimension; ++column) {
                ImageView cell = prepareCell();
                rowLayout.addView(cell);
                cells.set(new Position(row, column), cell);
            }
            verticalLayout.addView(rowLayout);
        }
        FrameLayout gameBoardFrameLayout =
                (FrameLayout) activity.findViewById(R.id.gameBoardFrameLayout);
        gameBoardFrameLayout.addView(verticalLayout);
        return cells;
    }

    private LinearLayout prepareVerticalLinearLayout(int numberOfChildElements) {
        return prepareLinearLayout(LinearLayout.VERTICAL, numberOfChildElements);
    }

    private LinearLayout prepareHorizontalLinearLayout(int numberOfChildElements) {
        return prepareLinearLayout(LinearLayout.HORIZONTAL, numberOfChildElements);
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
