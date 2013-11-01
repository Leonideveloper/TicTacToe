package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.OnEachHandler;
import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.matrix.ReadOnlyMatrix;
import com.gmail.landanurm.tictactoe.CurrentThemeProvider;
import com.gmail.landanurm.tictactoe.game.model_view.model.SquareMatrix;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;
import com.gmail.landanurm.tictactoe.theme.CellsTheme;
import com.gmail.landanurm.tictactoe.theme.TicTacToeTheme;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

class GameBoardViewImpl implements GameBoardView {

    private static class MapKeys {
        final static String backgroundIconsIds = "GameBoardView.backgroundIconsIds";
        final static String moveIconsIds = "GameBoardView.moveIconsIds";
        final static String fireIconsIds = "GameBoardView.fireIconsIds";
        final static String movesAreBlocked = "GameBoardView.movesAreBlocked";
    }

    private static CellsTheme getCurrentCellsTheme() {
        TicTacToeTheme currentTheme = CurrentThemeProvider.getCurrentTheme();
        return currentTheme.getGameTheme().getGameBoardTheme().getCellsTheme();
    }

    private final CellsTheme cellsTheme = getCurrentCellsTheme();

    private final CombinedDrawablesProvider combinedDrawablesProvider;
    private final Matrix<Integer> backgroundIconsIds;
    private final Matrix<Integer> moveIconsIds;
    private final Matrix<Integer> fireIconsIds;
    private final ReadOnlyMatrix<ImageView> cells;

    private Boolean movesAreBlocked;


    GameBoardViewImpl(Activity activity, int gameBoardDimension) {
        combinedDrawablesProvider = new CombinedDrawablesProvider(activity);
        cells = GameBoardViewCellsProvider.prepareCells(activity, gameBoardDimension);
        backgroundIconsIds = prepareCellsBackgroundIconsIds(gameBoardDimension);
        moveIconsIds = prepareTransparentIconsIds(gameBoardDimension);
        fireIconsIds = prepareTransparentIconsIds(gameBoardDimension);
        updateCells();
        movesAreBlocked = false;
    }

    private Matrix<Integer> prepareCellsBackgroundIconsIds(int gameBoardDimension) {
        final Matrix<Integer> ids = new SquareMatrix<Integer>(gameBoardDimension);
        ids.forEach(new OnEachHandler<Integer>() {
            @Override
            public void handle(Position pos, Integer elem) {
                ids.set(pos, cellsTheme.getCellBackgroundIconId());
            }
        });
        return ids;
    }

    private Matrix<Integer> prepareTransparentIconsIds(int gameBoardDimension) {
        Matrix<Integer> ids = new SquareMatrix<Integer>(gameBoardDimension);
        ids.fill(android.R.color.transparent);
        return ids;
    }

    private void updateCells() {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position pos, ImageView cell) {
                updateCellBackground(pos);
                updateCellImage(pos);
            }
        });
    }

    private void updateCellBackground(Position pos) {
        ImageView cell = cells.get(pos);
        cell.setBackgroundResource(backgroundIconsIds.get(pos));
    }

    private void updateCellImage(Position pos) {
        ImageView cell = cells.get(pos);
        int moveIconId = moveIconsIds.get(pos);
        int fireIconId = fireIconsIds.get(pos);
        Drawable cellDrawable = combinedDrawablesProvider.getCombinedDrawable(moveIconId, fireIconId);
        cell.setImageDrawable(cellDrawable);
    }

    GameBoardViewImpl(Activity activity, int gameBoardDimension, Map<String, Serializable> savedState) {
        combinedDrawablesProvider = new CombinedDrawablesProvider(activity);
        cells = GameBoardViewCellsProvider.prepareCells(activity, gameBoardDimension);
        backgroundIconsIds = (Matrix<Integer>) savedState.get(MapKeys.backgroundIconsIds);
        moveIconsIds = (Matrix<Integer>) savedState.get(MapKeys.moveIconsIds);
        fireIconsIds = (Matrix<Integer>) savedState.get(MapKeys.fireIconsIds);
        updateCells();
        movesAreBlocked = (Boolean) savedState.get(MapKeys.movesAreBlocked);
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.backgroundIconsIds, (Serializable) backgroundIconsIds);
        outState.put(MapKeys.moveIconsIds, (Serializable) moveIconsIds);
        outState.put(MapKeys.fireIconsIds, (Serializable) fireIconsIds);
        outState.put(MapKeys.movesAreBlocked, movesAreBlocked);
    }

    @Override
    public void setOnCellClickListener(final OnCellClickListener listener) {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView cell) {
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!movesAreBlocked()) {
                            listener.onCellClick(pos);
                        }
                    }
                });
            }
        });
    }

    private boolean movesAreBlocked() {
        return movesAreBlocked;
    }

    @Override
    public void blockMoves() {
        movesAreBlocked = true;
    }

    @Override
    public void unblockMoves() {
        movesAreBlocked = false;
    }

    @Override
    public void clear() {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position pos, ImageView cell) {
                moveIconsIds.set(pos, android.R.color.transparent);
                fireIconsIds.set(pos, android.R.color.transparent);
                updateCellImage(pos);
            }
        });
    }

    @Override
    public void showMove(Position pos, Player.Id playerId) {
        if (playerId == Player.Id.FIRST_PLAYER) {
            moveIconsIds.set(pos, cellsTheme.getFirstPlayerMoveIconId());
        } else {
            moveIconsIds.set(pos, cellsTheme.getSecondPlayerMoveIconId());
        }
        updateCellImage(pos);
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
            fireIconsIds.set(pos, cellsTheme.getFireIconId(fireLineType));
            updateCellImage(pos);
        }
    }
}
