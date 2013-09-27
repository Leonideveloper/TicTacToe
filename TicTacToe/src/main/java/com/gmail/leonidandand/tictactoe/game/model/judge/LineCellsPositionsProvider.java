package com.gmail.leonidandand.tictactoe.game.model.judge;


import com.gmail.landanurm.matrix.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonid on 15.09.13.
 */
class LineCellsPositionsProvider {
    private final int gameBoardDimension;

    LineCellsPositionsProvider(int gameBoardDimension) {
        this.gameBoardDimension = gameBoardDimension;
    }

    List<Position> onRow(final int row) {
        return positions(new PositionProvider() {
            @Override
            public Position byIndex(int index) {
                return new Position(row, index);
            }
        });
    }

    List<Position> onColumn(final int column) {
        return positions(new PositionProvider() {
            @Override
            public Position byIndex(int index) {
                return new Position(index, column);
            }
        });
    }

    List<Position> onLeftUpperDiagonal() {
        return positions(new PositionProvider() {
            @Override
            public Position byIndex(int index) {
                return new Position(index, index);
            }
        });
    }

    List<Position> onRightUpperDiagonal() {
        return positions(new PositionProvider() {
            @Override
            public Position byIndex(int index) {
                return new Position(index, (gameBoardDimension - 1) - index);
            }
        });
    }

    private List<Position> positions(PositionProvider positionProvider) {
        List<Position> positions = new ArrayList<Position>();
        for (int index = 0; index < gameBoardDimension; ++index) {
            positions.add(positionProvider.byIndex(index));
        }
        return positions;
    }

    private static interface PositionProvider {
        Position byIndex(int index);
    }
}
