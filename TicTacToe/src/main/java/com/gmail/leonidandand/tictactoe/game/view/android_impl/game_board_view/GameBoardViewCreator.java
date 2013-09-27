package com.gmail.leonidandand.tictactoe.game.view.android_impl.game_board_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Dimension;
import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.Position;
import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model.ReadOnlyGameBoard;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameBoardViewCreator {
    private final Activity activity;

    public GameBoardViewCreator(Activity activity) {
        this.activity = activity;
    }

    public GameBoardViewAndroidImpl create(ReadOnlyGameBoard gameBoard) {
        return new GameBoardViewAndroidImpl(prepareCells(gameBoard));
    }

    public GameBoardViewAndroidImpl create(ReadOnlyGameBoard gameBoard,
                                           GameBoardViewAndroidImpl toRestore) {
        return new GameBoardViewAndroidImpl(prepareCells(gameBoard), toRestore);
    }

    private Matrix<ImageView> prepareCells(ReadOnlyGameBoard gameBoard) {
        return prepareCells(gameBoard.getDimension());
    }

    private Matrix<ImageView> prepareCells(final int gameBoardDimension) {
        Matrix<ImageView> cells = new ArrayMatrix<ImageView>(
                new Dimension(gameBoardDimension, gameBoardDimension)
        );
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
