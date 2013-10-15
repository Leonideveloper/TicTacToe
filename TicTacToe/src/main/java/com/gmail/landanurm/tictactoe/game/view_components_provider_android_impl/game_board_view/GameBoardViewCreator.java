package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl.game_board_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Dimension;
import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.tictactoe.R;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameBoardViewCreator {
    private final Activity activity;

    public GameBoardViewCreator(Activity activity) {
        this.activity = activity;
    }

    public GameBoardViewAndroidImpl create(int gameBoardDimension) {
        return new GameBoardViewAndroidImpl(prepareCells(gameBoardDimension));
    }

    public GameBoardViewAndroidImpl create(int gameBoardDimension,
                                           Map<String, Serializable> savedState) {
        return new GameBoardViewAndroidImpl(prepareCells(gameBoardDimension), savedState);
    }

    private Matrix<ImageView> prepareCells(final int gameBoardDimension) {
        Matrix<ImageView> cells =
                new ArrayMatrix<ImageView>( new Dimension(gameBoardDimension, gameBoardDimension) );

        LinearLayout rowsContainerLayout = prepareRowsContainerLayout(gameBoardDimension);
        for (int row = 0; row < gameBoardDimension; ++row) {
            LinearLayout rowLayout = prepareRowLayout(gameBoardDimension);
            for (int column = 0; column < gameBoardDimension; ++column) {
                ImageView cell = prepareCell();
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

    private ImageView prepareCell() {
        LayoutInflater inflater = activity.getLayoutInflater();
        ImageView cell = (ImageView) inflater.inflate(R.layout.cell_image_view, null);
        cell.setLayoutParams(prepareParams());
        return cell;
    }

    private LinearLayout.LayoutParams prepareParams() {
        return new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
        );
    }
}
