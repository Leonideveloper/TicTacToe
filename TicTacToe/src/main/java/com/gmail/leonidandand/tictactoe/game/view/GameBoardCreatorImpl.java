package com.gmail.leonidandand.tictactoe.game.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.utils.Dimension;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 19.07.13.
 */
public class GameBoardCreatorImpl implements GameBoardCreator, View.OnClickListener {

    private static final int[][] IDS = {
            { R.id.cell_1_1, R.id.cell_1_2, R.id.cell_1_3 },
            { R.id.cell_2_1, R.id.cell_2_2, R.id.cell_2_3 },
            { R.id.cell_3_1, R.id.cell_3_2, R.id.cell_3_3 }
    };

    private final OnCellClickListener onCellClickListener;
    private final Activity activity;

    public GameBoardCreatorImpl(OnCellClickListener onCellClickListener, Activity activity) {
        this.onCellClickListener = onCellClickListener;
        this.activity = activity;
    }

    @Override
    public Matrix<ImageView> prepareCells(Dimension dim) {
        activity.setContentView(R.layout.tic_tac_toe_activity);
        final Matrix<ImageView> cells = new Matrix<ImageView>(dim);
        cells.forEach(new Matrix.OnEachHandler<ImageView>() {
            @Override
            public void handle(Matrix<ImageView> matrix, Matrix.Position pos) {
                int id = IDS[pos.row][pos.column];
                ImageView cell = (ImageView) activity.findViewById(id);
                cell.setOnClickListener(GameBoardCreatorImpl.this);
                cells.set(pos, cell);
            }
        });
        return cells;
    }

    @Override
    public void onClick(View cell) {
        Matrix.Position pos = posByCellId(cell.getId());
        onCellClickListener.onCellClick(pos);
    }

    private static Matrix.Position posByCellId(int id) {
        for (int row = 0; row < IDS.length; ++row) {
            for (int column = 0; column < IDS[row].length; ++column) {
                if (id == IDS[row][column]) {
                    return new Matrix.Position(row, column);
                }
            }
        }
        throw new RuntimeException("Cannot find id!");
    }
}
