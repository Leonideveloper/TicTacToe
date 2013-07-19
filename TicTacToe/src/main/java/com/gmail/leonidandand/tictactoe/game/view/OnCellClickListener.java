package com.gmail.leonidandand.tictactoe.game.view;

import android.view.View;
import android.widget.ImageView;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

/**
 * Created by Leonid on 19.07.13.
 */
public interface OnCellClickListener {

    void onCellClick(Matrix.Position pos);
}
