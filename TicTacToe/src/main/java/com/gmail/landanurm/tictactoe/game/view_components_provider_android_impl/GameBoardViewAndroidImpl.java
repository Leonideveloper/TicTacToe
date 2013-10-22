package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.view.View;
import android.widget.ImageView;

import com.gmail.landanurm.matrix.ArrayMatrix;
import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.OnEachHandler;
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

    private final CellsTheme cellsTheme = getCurrentCellsTheme();

    private static CellsTheme getCurrentCellsTheme() {
        return CurrentThemeProvider.getCurrentTheme()
                   .getGameTheme().getGameBoardTheme().getCellsTheme();
    }

    private final ReadOnlyMatrix<ImageView> cells;
    private final Matrix<Integer> firstLayerCellsIconsIds;
    private final Matrix<Integer> secondLayerCellsIconsIds;
    private boolean movesBlocked;

    GameBoardViewAndroidImpl(ReadOnlyMatrix<ImageView> cells) {
        this.cells = cells;
        firstLayerCellsIconsIds = new ArrayMatrix<Integer>(cells.getDimension());
        secondLayerCellsIconsIds = new ArrayMatrix<Integer>(cells.getDimension());
        movesBlocked = false;
        clear();
    }

    GameBoardViewAndroidImpl(ReadOnlyMatrix<ImageView> cells, Map<String,Serializable> savedState) {
        this.cells = cells;
        firstLayerCellsIconsIds = (Matrix<Integer>) savedState.get(MapKeys.firstLayer);
        secondLayerCellsIconsIds = (Matrix<Integer>) savedState.get(MapKeys.secondLayer);
        movesBlocked = (Boolean) savedState.get(MapKeys.movesBlocked);
        updateAllCellViews();
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.firstLayer, (Serializable) firstLayerCellsIconsIds);
        outState.put(MapKeys.secondLayer, (Serializable) secondLayerCellsIconsIds);
        outState.put(MapKeys.movesBlocked, movesBlocked);
    }

    @Override
    public void setOnCellClickListener(final OnCellClickListener onCellClickListener) {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final com.gmail.landanurm.matrix.Position pos, ImageView elem) {
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

    private boolean movesBlocked() {
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
            public void handle(com.gmail.landanurm.matrix.Position pos, ImageView elem) {
                clearCell(pos);
            }
        });
    }

    private void clearCell(com.gmail.landanurm.matrix.Position cellPos) {
        setSecondLayerCellIconId(cellPos, android.R.color.transparent);
        setFirstLayerCellIconId(cellPos, cellsTheme.getEmptyCellIconId());
    }

    private void setFirstLayerCellIconId(com.gmail.landanurm.matrix.Position cellPos, int resId) {
        cells.get(cellPos).setBackgroundResource(resId);
        firstLayerCellsIconsIds.set(cellPos, resId);
    }

    private void setSecondLayerCellIconId(com.gmail.landanurm.matrix.Position cellPos, int resId) {
        cells.get(cellPos).setImageResource(resId);
        secondLayerCellsIconsIds.set(cellPos, resId);
    }

    private void updateAllCellViews() {
        secondLayerCellsIconsIds.forEach(new OnEachHandler<Integer>() {
            @Override
            public void handle(com.gmail.landanurm.matrix.Position pos, Integer each) {
                updateCell(pos);
            }
        });
    }

    private void updateCell(com.gmail.landanurm.matrix.Position pos) {
        setSecondLayerCellIconId(pos, secondLayerCellsIconsIds.get(pos));
        setFirstLayerCellIconId(pos, firstLayerCellsIconsIds.get(pos));
    }

    @Override
    public void showMove(com.gmail.landanurm.matrix.Position pos, Player.Id playerId) {
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
        Collection<com.gmail.landanurm.matrix.Position> cellsPositions = fireLine.getCellsPositions();
        for (com.gmail.landanurm.matrix.Position pos : cellsPositions) {
            setSecondLayerCellIconId(pos, cellsTheme.getFireIconId(fireLineType));
        }
    }
}
