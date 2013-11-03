package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
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

    private final DrawablesCombiner drawablesCombiner;
    private final Matrix<Integer> backgroundIconsIds;
    private final Matrix<Integer> moveIconsIds;
    private final Matrix<Integer> fireIconsIds;
    private final ReadOnlyMatrix<ImageView> cells;

    private Boolean movesAreBlocked;


    GameBoardViewImpl(Activity activity, int gameBoardDimension) {
        drawablesCombiner = new DrawablesCombiner(activity);
        backgroundIconsIds = prepareCellsBackgroundIconsIds(gameBoardDimension);
        moveIconsIds = prepareTransparentIconsIds(gameBoardDimension);
        fireIconsIds = prepareTransparentIconsIds(gameBoardDimension);
        cells = prepareCells(activity);
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

    private ReadOnlyMatrix<ImageView> prepareCells(Activity activity) {
        GameBoardViewCellsProvider cellsProvider = new GameBoardViewCellsProvider(activity);
        return cellsProvider.prepareCells(backgroundIconsIds);
    }

    GameBoardViewImpl(Activity activity, Map<String, Serializable> savedState) {
        drawablesCombiner = new DrawablesCombiner(activity);
        backgroundIconsIds = (Matrix<Integer>) savedState.get(MapKeys.backgroundIconsIds);
        moveIconsIds = (Matrix<Integer>) savedState.get(MapKeys.moveIconsIds);
        fireIconsIds = (Matrix<Integer>) savedState.get(MapKeys.fireIconsIds);
        cells = prepareCells(activity);
        movesAreBlocked = (Boolean) savedState.get(MapKeys.movesAreBlocked);
        updateCells();
    }

    private void updateCells() {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position pos, ImageView cell) {
                updateCell(pos);
            }
        });
    }

    private void updateCell(Position pos) {
        ImageView cell = cells.get(pos);
        int moveIconId = moveIconsIds.get(pos);
        int fireIconId = fireIconsIds.get(pos);
        cell.setImageDrawable(drawablesCombiner.getCombinedDrawable(moveIconId, fireIconId));
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
                updateCell(pos);
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
        updateCell(pos);
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
            updateCell(pos);
        }
    }
}
