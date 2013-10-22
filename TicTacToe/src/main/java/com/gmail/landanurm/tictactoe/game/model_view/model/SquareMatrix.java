package com.gmail.landanurm.tictactoe.game.model_view.model;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Dimension;

import java.io.Serializable;

/**
 * Created by Leonid on 22.10.13.
 */
public class SquareMatrix<T> extends ArrayMatrix<T> implements Serializable {
    public SquareMatrix(int dimension) {
        super(new Dimension(dimension, dimension));
    }
}
