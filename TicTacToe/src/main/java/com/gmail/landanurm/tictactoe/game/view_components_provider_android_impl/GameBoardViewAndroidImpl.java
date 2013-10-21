package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.view.View;
import android.widget.ImageView;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.OnEachHandler;
import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.matrix.ReadOnlyMatrix;
import com.gmail.landanurm.tictactoe.CurrentThemeProvider;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;
import com.gmail.landanurm.tictactoe.theme.CellsTheme;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

class GameBoardViewAndroidImpl implements GameBoardView {

    private static class MapKeys {
        final static String firstLayer = "GameBoardView.firstLayer";
        final static String secondLayer = "GameBoardView.secondLayer";
        final static String movesBlocked = "GameBoardView.movesBlocked";
    }

    private final CellsTheme cellsTheme;
    private final ReadOnlyMatrix<ImageView> cells;
    private final Matrix<Integer> firstLayerCellsIconsIds;
    private final Matrix<Integer> secondLayerCellsIconsIds;

    private boolean movesBlocked;

    GameBoardViewAndroidImpl(ReadOnlyMatrix<ImageView> cells) {
        this(
            cells,
            new ArrayMatrix<Integer>(cells.getDimension()),
            new ArrayMatrix<Integer>(cells.getDimension()),
            false
        );
        this.clear();
    }

    private GameBoardViewAndroidImpl(ReadOnlyMatrix<ImageView> cells,
                                     Matrix<Integer> firstLayerCellsIconsIds,
                                     Matrix<Integer> secondLayerCellsIconsIds,
                                     boolean movesBlocked) {
        this.cells = cells;
        this.firstLayerCellsIconsIds = firstLayerCellsIconsIds;
        this.secondLayerCellsIconsIds = secondLayerCellsIconsIds;
        this.movesBlocked = movesBlocked;
        this.cellsTheme = getCurrentCellsTheme();
    }

    private static CellsTheme getCurrentCellsTheme() {
        return CurrentThemeProvider.getCurrentTheme()
                                   .getGameTheme()
                                   .getGameBoardTheme()
                                   .getCellsTheme();
    }

    GameBoardViewAndroidImpl(ReadOnlyMatrix<ImageView> cells, Map<String,Serializable> savedState) {
        this(
            cells,
            (Matrix<Integer>) savedState.get(MapKeys.firstLayer),
            (Matrix<Integer>) savedState.get(MapKeys.secondLayer),
            (Boolean) savedState.get(MapKeys.movesBlocked)
        );
        this.updateAllCellViews();
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.firstLayer, (Serializable) firstLayerCellsIconsIds);
        outState.put(MapKeys.secondLayer, (Serializable) secondLayerCellsIconsIds);
        outState.put(MapKeys.movesBlocked, movesBlocked);
    }

    @Override
    public boolean movesBlocked() {
        return movesBlocked;
    }

    @Override
    public void blockMoves() {
        movesBlocked = true;
    }

    @Override
    public void unblockMoves() {
        movesBlocked = false;
    }

    @Override
    public void clear() {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position pos, ImageView elem) {
                clearCell(pos);
            }
        });
    }

    private void clearCell(Position cellPos) {
        setSecondLayerCellIconId(cellPos, android.R.color.transparent);
        setFirstLayerCellIconId(cellPos, cellsTheme.getEmptyCellIconId());
    }

    private void setFirstLayerCellIconId(Position cellPos, int resId) {
        cells.get(cellPos).setBackgroundResource(resId);
        firstLayerCellsIconsIds.set(cellPos, resId);
    }

    private void setSecondLayerCellIconId(Position cellPos, int resId) {
        cells.get(cellPos).setImageResource(resId);
        secondLayerCellsIconsIds.set(cellPos, resId);
    }

    private void updateAllCellViews() {
        secondLayerCellsIconsIds.forEach(new OnEachHandler<Integer>() {
            @Override
            public void handle(Position pos, Integer each) {
                updateCell(pos);
            }
        });
    }

    private void updateCell(Position pos) {
        setSecondLayerCellIconId(pos, secondLayerCellsIconsIds.get(pos));
        setFirstLayerCellIconId(pos, firstLayerCellsIconsIds.get(pos));
    }

    @Override
    public void showMove(Position pos, Player.Id playerId) {
        if (playerId == Player.Id.FIRST_PLAYER) {
            setFirstLayerCellIconId(pos, cellsTheme.getFirstPlayerMoveIconId());
        } else {
            setFirstLayerCellIconId(pos, cellsTheme.getSecondPlayerMoveIconId());
        }
    }

    @Override
    public void showFireLines(Collection<FireLine> fireLines) {
        for (FireLine each : fireLines) {
            showFireLine(each);
        }
    }

    private void showFireLine(FireLine fireLine) {
        FireLine.Type fireLineType = fireLine.getFireLineType();
        Collection<Position> cellsPositions = fireLine.getCellsPositions();
        for (Position pos : cellsPositions) {
            setSecondLayerCellIconId(pos, cellsTheme.getFireIconId(fireLineType));
        }
    }

    @Override
    public void setOnCellClickListener(final OnCellClickListener onCellClickListener) {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView elem) {
                ImageView cell = cells.get(pos);
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!movesBlocked()) {
                            onCellClickListener.onCellClick(pos);
                        }
                    }
                });
            }
        });
    }
}
