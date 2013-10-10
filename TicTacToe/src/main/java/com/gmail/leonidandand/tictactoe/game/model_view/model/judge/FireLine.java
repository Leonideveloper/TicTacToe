package com.gmail.leonidandand.tictactoe.game.model_view.model.judge;

import com.gmail.landanurm.matrix.Position;
import java.util.Collection;

/**
 * Created by Leonid on 10.08.13.
 */
public class FireLine {
    public static enum Type {
        ROW,
        COLUMN,
        LEFT_UPPER_DIAGONAL,
        RIGHT_UPPER_DIAGONAL
    }

    private final Collection<Position> cellsPositions;
    private final FireLine.Type fireLineType;

    FireLine(Collection<Position> cellsPositions, FireLine.Type fireLineType) {
        this.cellsPositions = cellsPositions;
        this.fireLineType = fireLineType;
    }

    public Collection<Position> getCellsPositions() {
        return cellsPositions;
    }

    public Type getFireLineType() {
        return fireLineType;
    }
}
