package com.gmail.landanurm.tictactoe.game.android_view_components_provider;

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
import com.gmail.landanurm.tictactoe.theme.TicTacToeTheme;
import com.gmail.landanurm.tictactoe.theme.game_theme.CellsTheme;

import java.io.Serializable;
import java.util.ArrayList;
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

    private final Collection<OnCellClickListener> onCellClickListeners;
    private final DrawablesCombiner drawablesCombiner;
    private final Matrix<Integer> backgroundIconsIds;
    private final Matrix<Integer> moveIconsIds;
    private final Matrix<Integer> fireIconsIds;
    private final ReadOnlyMatrix<ImageView> cells;
    private boolean movesAreBlocked;


    GameBoardViewImpl(Activity activity, int gameBoardDimension) {
        drawablesCombiner = new DrawablesCombiner(activity);
        onCellClickListeners = new ArrayList<OnCellClickListener>();
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
        Matrix<ImageView> preparedCells = cellsProvider.prepareCells(backgroundIconsIds);
        preparedCells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView cell) {
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!movesAreBlocked()) {
                            blockMoves();
                            notifyOnCellClickListener(pos);
                            unblockMoves();
                        }
                    }
                });
            }
        });
        return preparedCells;
    }

    private boolean movesAreBlocked() {
        return movesAreBlocked;
    }

    private void blockMoves() {
        movesAreBlocked = true;
    }

    private void unblockMoves() {
        movesAreBlocked = false;
    }

    private void notifyOnCellClickListener(Position pos) {
        for (OnCellClickListener each : onCellClickListeners) {
            each.onCellClick(pos);
        }
    }

    GameBoardViewImpl(Activity activity, Map<String, Serializable> savedState) {
        drawablesCombiner = new DrawablesCombiner(activity);
        onCellClickListeners = new ArrayList<OnCellClickListener>();
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
        Drawable cellDrawable = drawablesCombiner.getCombinedDrawable(moveIconId, fireIconId);
        cell.setImageDrawable(cellDrawable);
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.backgroundIconsIds, (Serializable) backgroundIconsIds);
        outState.put(MapKeys.moveIconsIds, (Serializable) moveIconsIds);
        outState.put(MapKeys.fireIconsIds, (Serializable) fireIconsIds);
        outState.put(MapKeys.movesAreBlocked, movesAreBlocked);
    }

    @Override
    public void addOnCellClickListener(OnCellClickListener listener) {
        onCellClickListeners.add(listener);
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
